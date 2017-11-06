package com.epicodus.recipro;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Response;




public class RecipeListActivity extends AppCompatActivity implements OnRecipeSelectedListener{
    String time = "";
    String[] allowedIngredients;
    String[] excludedIngredients;
    String course = "";
    String cuisine = "";
    private Integer mPosition;
    ArrayList<Recipe> mRecipes;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        ButterKnife.bind(this);

        if (savedInstanceState != null) {

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                mPosition = savedInstanceState.getInt(Constants.EXTRA_KEY_POSITION);
                mRecipes = Parcels.unwrap(savedInstanceState.getParcelable(Constants.EXTRA_KEY_RECIPES));

                if (mPosition != null && mRecipes != null) {
                    Intent intent = new Intent(this, RecipeDetailActivity.class);
                    intent.putExtra(Constants.EXTRA_KEY_POSITION, mPosition);
                    intent.putExtra(Constants.EXTRA_KEY_RECIPES, Parcels.wrap(mRecipes));
                    startActivity(intent);
                }

            }

        }

        Intent intent = getIntent();
        time = intent.getStringExtra("time");
        allowedIngredients = intent.getStringArrayExtra("allowedIngredients");
        excludedIngredients = intent.getStringArrayExtra("excludedIngredients");
        course = intent.getStringExtra("course");
        cuisine = intent.getStringExtra("cuisine");

        Log.i("FindRecipeResults..","cuisine variable is == " + cuisine);



//        getRecipes(time,allowedIngredients,excludedIngredients,course,cuisine);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mPosition != null && mRecipes != null) {
            outState.putInt(Constants.EXTRA_KEY_POSITION, mPosition);
            outState.putParcelable(Constants.EXTRA_KEY_RECIPES, Parcels.wrap(mRecipes));
        }

    }

    @Override
    public void onRecipeSelected(Integer position, ArrayList<Recipe> recipes) {
        mPosition = position;
        mRecipes = recipes;
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