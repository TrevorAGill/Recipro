package com.epicodus.recipro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Response;




public class RecipeListActivity extends AppCompatActivity {
    String time = "";
    String[] allowedIngredients;
    String[] excludedIngredients;
    String course = "";
    String cuisine = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        ButterKnife.bind(this);


        Intent intent = getIntent();
        time = intent.getStringExtra("time");
        allowedIngredients = intent.getStringArrayExtra("allowedIngredients");
        excludedIngredients = intent.getStringArrayExtra("excludedIngredients");
        course = intent.getStringExtra("course");
        cuisine = intent.getStringExtra("cuisine");

        Log.i("FindRecipeResults..","cuisine variable is == " + cuisine);



//        getRecipes(time,allowedIngredients,excludedIngredients,course,cuisine);
    }

    public String getTime() {
        return this.time;
    }

    public String[] getAllowedIngredients() {
        return this.allowedIngredients;
    }

    public String[] getExcludedIngredients() {
        return this.excludedIngredients;
    }

    public String getCourse() {
        return this.course;
    }

    public String getCuisine() {
        return this.cuisine;
    }


}