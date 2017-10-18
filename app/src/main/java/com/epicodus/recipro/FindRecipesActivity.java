package com.epicodus.recipro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FindRecipesActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.timeEditText) EditText mTime;
    @Bind(R.id.includeEditText) EditText mAllowedIngredients;
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
            for(int i=0 ; i < allowedIngredients.length ; i++) {
                allowedIngredients[i] = allowedIngredients[i].replace(",","");
                //this needs to be improved. Currently removes ALL spaces, not just leading and trailing spaces
                allowedIngredients[i] = allowedIngredients[i].replace(" ", "");
                System.out.println(allowedIngredients[i]);
            }
            Log.i("FindRecipesActivity", "ingredient array = " + allowedIngredients);
            Intent intent = new Intent(FindRecipesActivity.this, FindRecipeResultsActivity.class);
            intent.putExtra("time", time);
            intent.putExtra("allowedIngredients", allowedIngredients);
            startActivity(intent);
        }
    }
}
