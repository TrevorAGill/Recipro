package com.epicodus.recipro;

public class Constants {
    public static final String YUMMLY_APPLICATION_ID = BuildConfig.YUMMLY_APPLICATION_ID;
    public static final String YUMMLY_KEY = BuildConfig.YUMMLY_KEY;
    public static final String YUMMLY_BASE_URL = "http://api.yummly.com/v1/api/recipes?_app_id=" + YUMMLY_APPLICATION_ID + "&_app_key=" + YUMMLY_KEY + "&";
    public static final String YUMMLY_TIME_QUERY_PARAMETER = "time";
}
