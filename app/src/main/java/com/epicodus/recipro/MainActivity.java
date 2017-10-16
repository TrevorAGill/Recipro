package com.epicodus.recipro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.createRecipe) Button mCreateRecipe;
    @Bind(R.id.findRecipe) Button mFindRecipe;
    @Bind(R.id.groceryList) Button mGroceryList;
    @Bind(R.id.recipeBox) Button mRecipeBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mCreateRecipe.setOnClickListener(this);
        mFindRecipe.setOnClickListener(this);
        mGroceryList.setOnClickListener(this);
        mRecipeBox.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == mCreateRecipe) {
            Intent iCreateRecipe = new Intent(MainActivity.this, NewRecipeActivity.class);
            startActivity(iCreateRecipe);
        } else if(v == mRecipeBox) {
            Log.i("MainActivity","recipe box");
        } else if(v == mFindRecipe) {
            Log.i("MainActivity","find recipe");
            Intent iFindRecipes = new Intent(MainActivity.this, FindRecipesActivity.class);
            startActivity(iFindRecipes);
        } else if(v == mGroceryList) {
            Log.i("MainActivity","grocery list");
        }
    }
}
