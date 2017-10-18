package com.epicodus.recipro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FindRecipesActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.timeEditText) EditText mTime;
    @Bind(R.id.includeEditText) EditText mAllowedIngredients;
    @Bind(R.id.excludeEditText) EditText mExcludedIngredients;
    @Bind(R.id.courseSpinner) Spinner mCourse;
    @Bind(R.id.cuisineSpinner) Spinner mCuisine;
    @Bind(R.id.submitCriteriaButton) Button mSubmitCriteriaButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_recipes);
        ButterKnife.bind(this);
        mSubmitCriteriaButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mSubmitCriteriaButton) {
            String time = mTime.getText().toString();
            String[] allowedIngredients = mAllowedIngredients.getText().toString().split(",");
            String[] excludedIngredients = mExcludedIngredients.getText().toString().split(",");
            String cuisine = mCuisine.getSelectedItem().toString();
            String course = mCourse.getSelectedItem().toString();
            for(String ingredient : allowedIngredients) {
                ingredient = ingredient.replace(",","");
                //this needs to be improved. Currently removes ALL spaces, not just leading and trailing spaces
                ingredient = ingredient.replace(" ", "");
                System.out.println(ingredient);
            }
            for(String ingredient : excludedIngredients) {
                ingredient = ingredient.replace(",","");
                //this needs to be improved. Currently removes ALL spaces, not just leading and trailing spaces
                ingredient = ingredient.replace(" ", "");
                System.out.println(ingredient);
            }
            Log.i("FindRecipesActivity", "ingredient array = " + allowedIngredients);
            Intent intent = new Intent(FindRecipesActivity.this, FindRecipeResultsActivity.class);
            intent.putExtra("time", time);
            intent.putExtra("allowedIngredients", allowedIngredients);
            intent.putExtra("excludedIngredients", excludedIngredients);
            intent.putExtra("cuisine", cuisine);
            intent.putExtra("course", course);
            startActivity(intent);
        }
    }
}
