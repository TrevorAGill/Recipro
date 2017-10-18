package com.epicodus.recipro;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//        "attributes": {
//        "course": [
//        "Soups"
//        ],
//        "cuisine": [
//        "Italian"
//        ]
//        },
//        "flavors": {
//        "salty": 0.6666666666666666,
//        "sour": 0.8333333333333334,
//        "sweet": 0.6666666666666666,
//        "bitter": 0.5,
//        "meaty": 0.16666666666666666,
//        "piquant": 0.5
//        },
//        "rating": 4.6,
//        "id": "Vegetarian-Cabbage-Soup-Recipezaar",
//        "smallImageUrls": [],
//        "sourceDisplayName": "Food.com",
//        "totalTimeInSeconds": 4500,
//        "ingredients": [
//        "garlic cloves",
//        "ground pepper",
//        "diced tomatoes",
//        "celery",
//        "tomato juice",
//        "salt",
//        "cabbage",
//        "bell peppers",
//        "oregano",
//        "carrots",
//        "basil",
//        "vegetable broth",
//        "chili pepper flakes",
//        "green beans",
//        "onions",
//        "onion soup mix"
//        ],
//        "recipeName": "Vegetarian Cabbage Soup"
//        },
//        {
//        "attributes": {
//        "course": [
//        "Soups"
//        ],
//        "cuisine": [
//        "Moroccan",
//        "Asian"
//        ]
//        },
//        "flavors": {
//        "salty": 0.6666666666666666,
//        "sour": 0.6666666666666666,
//        "sweet": 0.5,
//        "bitter": 0.5,
//        "meaty": 0.3333333333333333,
//        "piquant": 0.6666666666666666
//        },
//        "rating": 5,
//        "id": "Oriental-Inspired-Vegetable-Soup-Recipezaar",
//        "smallImageUrls": [],
//        "sourceDisplayName": "Food.com",
//        "totalTimeInSeconds": 24300,
//        "ingredients": [
//        "tamari",
//        "rice vinegar",
//        "bamboo shoots",
//        "lime juice",
//        "pepper",
//        "vegetable bouillon",
//        "sesame oil",
//        ],
//        "recipeName": "Oriental Inspired Vegetable Soup"
//        },

public class YummlyService {

        public static void findRecipes(Integer time, String[] allowedIngredients, String[] excludedIngredients, String cuisine, String course, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();


        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.YUMMLY_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.YUMMLY_TIME_QUERY_PARAMETER, time.toString());
        urlBuilder.addQueryParameter(Constants.YUMMLY_CUISINE_QUERY_PARAMETER, cuisine);
        urlBuilder.addQueryParameter(Constants.YUMMLY_COURSE_QUERY_PARAMETER, course);
        for(String ingredient : allowedIngredients) {
            urlBuilder.addQueryParameter(Constants.YUMMLY_ALLOWED_INGREDIENTS_QUERY_PARAMETER, ingredient);
        }
        for(String ingredient : allowedIngredients) {
                urlBuilder.addQueryParameter(Constants.YUMMLY_EXCLUDED_INGREDIENTS_QUERY_PARAMETER, ingredient);
        }

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

        public ArrayList<Recipe> processResults(Response response) {
                ArrayList<Recipe> recipes = new ArrayList<>();

                try {
                        String jsonData = response.body().string();
                        JSONObject yummlyJSON = new JSONObject(jsonData);
                        JSONArray matchesJSON = yummlyJSON.getJSONArray("matches");
                        for (int i = 0; i < matchesJSON.length(); i++) {
                                JSONObject recipeJSON = matchesJSON.getJSONObject(i);
                                String name = recipeJSON.getString("recipeName");
                                String time = recipeJSON.getString("totalTimeInSeconds");
                                JSONArray ingredientsRaw = recipeJSON.getJSONArray("ingredients");
                                String[] ingredients = ingredientsRaw.toString().replace("},{", " ,").split(" ");
                                String course = recipeJSON.getString("course");
                                String cuisine = recipeJSON.getString("cuisine");
                                Recipe recipe = new Recipe(name, ingredients, time, course, cuisine);
                                recipes.add(recipe);
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

}
