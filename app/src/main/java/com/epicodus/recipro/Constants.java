package com.epicodus.recipro;

public class Constants {
    public static final String YUMMLY_APPLICATION_ID = BuildConfig.YUMMLY_APPLICATION_ID;
    public static final String YUMMLY_KEY = BuildConfig.YUMMLY_KEY;
    public static final String YUMMLY_RECIPE_ID = "recipeId";
    public static final String YUMMLY_BASE_URL = "http://api.yummly.com/v1/api/recipes?_app_id=" + YUMMLY_APPLICATION_ID + "&_app_key=" + YUMMLY_KEY + "&";
    public static final String YUMMLY_GET_BASE_URL = "http://api.yummly.com/v1/api/recipe/" + YUMMLY_RECIPE_ID + "?_app_id=" + YUMMLY_APPLICATION_ID + "&_app_key=" + YUMMLY_KEY;
    public static final String YUMMLY_TIME_QUERY_PARAMETER = "time";
    public static final String YUMMLY_ALLOWED_INGREDIENTS_QUERY_PARAMETER = "allowedIngredient";
    public static final String YUMMLY_EXCLUDED_INGREDIENTS_QUERY_PARAMETER = "excludedIngredient";
    public static final String YUMMLY_CUISINE_QUERY_PARAMETER = "&allowedCuisine[]=cuisine^cuisine-";
    public static final String YUMMLY_COURSE_QUERY_PARAMETER = "course";
    public static final String FIREBASE_SAVED_RECIPE = "savedRecipe";
    public static final String FIREBASE_CHILD_RECIPES = "recipes";
    public static final String FIREBASE_QUERY_INDEX = "index";
    public static final String EXTRA_KEY_POSITION = "position";
    public static final String EXTRA_KEY_RECIPES = "recipes";
    public static final String EXTRA_KEY_EMAIL = "email";
    public static final String KEY_SOURCE = "source";
    public static final String SOURCE_SAVED = "saved";
    public static final String SOURCE_FIND = "find";

}
