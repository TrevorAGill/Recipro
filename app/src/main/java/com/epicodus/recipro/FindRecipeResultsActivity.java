package com.epicodus.recipro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Response;




public class FindRecipeResultsActivity extends AppCompatActivity {
    public static final String TAG = FindRecipeResultsActivity.class.getSimpleName();
    public ArrayList<Recipe> recipes = new ArrayList<>();
    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
    private RecipeListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_recipe_results);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String time = intent.getStringExtra("time");
        String[] allowedIngredients = intent.getStringArrayExtra("allowedIngredients");
        String[] excludedIngredients = intent.getStringArrayExtra("excludedIngredients");
        String course = intent.getStringExtra("course");
        String cuisine = intent.getStringExtra("cuisine");

        Log.i("FindRecipeResults..","cuisine variable is == " + cuisine);



        getRecipes(time,allowedIngredients,excludedIngredients,course,cuisine);
    }

    private void getRecipes(String time, String[] allowedIngredients, String[] excludedIngredients, String course, String cuisine) {
        final YummlyService yummlyService = new YummlyService();
        yummlyService.findRecipes(time, allowedIngredients, excludedIngredients, course, cuisine, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("FindRecipeResults", "response is: " + response);
                recipes = yummlyService.processResults(response);
                Log.i("FindRecipeResults", "recipes are: " + recipes);

                FindRecipeResultsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new RecipeListAdapter(getApplicationContext(), recipes);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(FindRecipeResultsActivity.this);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);
                    }
                });

            }
        });
    }
}