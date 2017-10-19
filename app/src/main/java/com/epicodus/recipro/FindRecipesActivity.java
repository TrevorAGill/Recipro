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
            Intent intent = new Intent(FindRecipesActivity.this, FindRecipeResultsActivity.class);
            if(mTime.getText().toString() != null && mTime.getText().toString() != "") {String time = Integer.toString(Integer.parseInt(mTime.getText().toString()) * 60);
                intent.putExtra("time", time);}
            if(mAllowedIngredients.getText().toString() != null && mAllowedIngredients.getText().toString() != "") {String[] allowedIngredients = mAllowedIngredients.getText().toString().split(",");
                for(String ingredient : allowedIngredients) {
                    ingredient = ingredient.replace(",","");
                    //this needs to be improved. Currently removes ALL spaces, not just leading and trailing spaces
                    ingredient = ingredient.replace(" ", "");
                    System.out.println(ingredient);
                    intent.putExtra("allowedIngredients", allowedIngredients);
                }
            }
            if(mExcludedIngredients.getText().toString() != null && mExcludedIngredients.getText().toString() != "") {String[] excludedIngredients = mExcludedIngredients.getText().toString().split(",");
                for(String ingredient : excludedIngredients) {
                    ingredient = ingredient.replace(",","");
                    //this needs to be improved. Currently removes ALL spaces, not just leading and trailing spaces
                    ingredient = ingredient.replace(" ", "");
                    System.out.println(ingredient);
                    intent.putExtra("excludedIngredients", excludedIngredients);
                }
            }
            if(mCuisine.getSelectedItem().toString() != null && mCuisine.getSelectedItem().toString() != "") {String cuisine = mCuisine.getSelectedItem().toString();
                intent.putExtra("cuisine", cuisine);
            }
            if(mCourse.getSelectedItem().toString() != null && mCourse.getSelectedItem().toString() != "") {String course = mCourse.getSelectedItem().toString();
                intent.putExtra("course", course);
            }

            startActivity(intent);
        }
    }
}
