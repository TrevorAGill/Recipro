package com.epicodus.recipro;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class YummlyService {
        private DatabaseReference mSavedRecipeReference;

        public static void findRecipes(String time, String[] allowedIngredients, String[] excludedIngredients, String course, String cuisine, Callback callback) {

                OkHttpClient client = new OkHttpClient.Builder()
                        .build();

                HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.YUMMLY_BASE_URL).newBuilder();
                if((Arrays.toString(allowedIngredients)).length() != 2) {
                        for(String ingredient : allowedIngredients) {
                                ingredient.trim();
                                ingredient = ingredient.replace(" ", "");
                                ingredient = ingredient.replace("%20", "");
                                urlBuilder.addQueryParameter(Constants.YUMMLY_ALLOWED_INGREDIENTS_QUERY_PARAMETER, ingredient);
                        }
                }
                if((Arrays.toString(excludedIngredients)).length() != 2) {
                        System.out.println("exluded ingredients length = " + (Arrays.toString(excludedIngredients)).length() + (Arrays.toString(excludedIngredients)));
                        for(String ingredient : excludedIngredients) {
                                ingredient.trim();
                                ingredient = ingredient.replace(" ", "");
                                ingredient = ingredient.replace("%20", "");
                                urlBuilder.addQueryParameter(Constants.YUMMLY_EXCLUDED_INGREDIENTS_QUERY_PARAMETER, ingredient);
                        }
                }
                if(time.length() != 0) {urlBuilder.addQueryParameter(Constants.YUMMLY_TIME_QUERY_PARAMETER, time);}
                String url = urlBuilder.build().toString();
                if(cuisine.length() != 0) {url = url + "&allowedCuisine[]=cuisine^cuisine-" + cuisine.toLowerCase();}
                if(course.length() != 0) {url = url + "&allowedCourse[]=course^course-" + course;}

                Request request = new Request.Builder()
                        .url(url)
                        .build();

                Call call = client.newCall(request);
                call.enqueue(callback);
        }

        public static void getRecipeAPI(String id, Callback callback2) {

                OkHttpClient client = new OkHttpClient.Builder()
                        .build();

                HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.YUMMLY_GET_BASE_URL).newBuilder();
                        urlBuilder.addQueryParameter(Constants.YUMMLY_RECIPE_ID, id);

//                String url = urlBuilder.build().toString();
                String url = "http://api.yummly.com/v1/api/recipe/" + id + "?_app_id=" + Constants.YUMMLY_APPLICATION_ID + "&_app_key=" + Constants.YUMMLY_KEY;
                System.out.println(url);

                Request request = new Request.Builder()
                        .url(url)
                        .build();

                Call call = client.newCall(request);
                call.enqueue(callback2);
        }

        public ArrayList<Recipe> processResults(Response response) {
                ArrayList<Recipe> recipes = new ArrayList<>();
                mSavedRecipeReference = FirebaseDatabase
                        .getInstance()
                        .getReference()
                        .child(Constants.FIREBASE_SAVED_RECIPE);


                try {
                        String jsonData = response.body().string();
                        JSONObject yummlyJSON = new JSONObject(jsonData);
                        JSONArray matchesJSON = yummlyJSON.getJSONArray("matches");
                        for (int i = 0; i < matchesJSON.length(); i++) {
                                JSONObject recipeJSON = matchesJSON.getJSONObject(i);
                                String name = recipeJSON.getString("recipeName");
                                String time = recipeJSON.optString("totalTimeInSeconds");
                                JSONArray ingredientsRaw = recipeJSON.getJSONArray("ingredients");
                                ArrayList<String> ingredients = new ArrayList<>(Arrays.asList(ingredientsRaw.toString().replace("},{", " ,").split(" ")));
                                String course = recipeJSON.optString("course");
                                String cuisine = recipeJSON.optString("cuisine");
                                String id = recipeJSON.getString("id");
                                String smallImageURL = recipeJSON.getString("smallImageUrls").replace("[","").replace("]","").replace("\"", "");
                                Recipe recipe = new Recipe(id, name, ingredients, time, course, cuisine, smallImageURL);
                                recipes.add(recipe);
                                saveRecipeToFireBase(recipe);
                        }
                }
                catch (IOException e){
                        e.printStackTrace();
                }
                catch (JSONException e){
                        e.printStackTrace();
                }
                return recipes;
        }

        public String processResults2(Response response) {
                String test = "";
                try {
                        String jsonData = response.body().string();
                        JSONObject recipeJSON = new JSONObject(jsonData);
                                test = recipeJSON.getJSONObject("source").toString();
//                                String test2 = recipeJSON.getJSONObject("source").getJSONObject("sourceRecipeURL").toString();
                                System.out.println(test);
//                                source = recipeJSON.getJSONObject("source").getJSONArray("sourceRecipeURL").toString();
                }
                catch (IOException e){
                        e.printStackTrace();
                }
                catch (JSONException e){
                        e.printStackTrace();
                }
                System.out.println(test);
                return test;
        }

        public void saveRecipeToFireBase(Recipe newRecipe) {
                mSavedRecipeReference.setValue(newRecipe);
        }

}
