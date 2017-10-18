package com.epicodus.recipro;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.epicodus.myrestaurants.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchCriteriaFragment extends Fragment implements View.OnClickListener {
    @Bind(R.id.timeEditText) EditText mTime;
    @Bind(R.id.includeEditText) EditText mAllowedIngredients;
    @Bind(R.id.excludeEditText) EditText mExcludedIngredients;
    @Bind(R.id.courseSpinner) Spinner mCourse;
    @Bind(R.id.cuisineSpinner) Spinner mCuisine;
    @Bind(R.id.submitCriteriaButton) Button mSubmitCriteriaButton;


    public SearchCriteriaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSubmitCriteriaButton.setOnClickListener(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_criteria, container, false);
        ButterKnife.bind(this,view);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_criteria, container, false);

    }

    @Override
    public void onClick(View v) {
        if (v == mSubmitCriteriaButton) {
            FindRecipesActivity.showSearchResults();
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
