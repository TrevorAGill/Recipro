package com.epicodus.recipro;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FindRecipesActivity extends FragmentActivity implements View.OnClickListener, SearchCriteriaFragment.RecipeSearcher {
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
            for (String ingredient : allowedIngredients) {
                ingredient = ingredient.replace(",", "");
                //this needs to be improved. Currently removes ALL spaces, not just leading and trailing spaces
                ingredient = ingredient.replace(" ", "");
                System.out.println(ingredient);
            }
            for (String ingredient : excludedIngredients) {
                ingredient = ingredient.replace(",", "");
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

    public void searchForRecipes(){
        SearchResultsFragment searchResultsFragment = new SearchResultsFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.your_placeholder, searchResultsFragment);
        ft.commit();
    }


//    // Activity implements the fragment listener to handle events
//    public class RssfeedActivity extends AppCompatActivity implements SearchCriteriaFragment.OnItemSelectedListener {
//        // Can be any fragment, `DetailFragment` is just an example
//        SearchResultsFragment fragment;
//
//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.activity_rssfeed);
//            // Get access to the detail view fragment by id
//            fragment = (SearchResultsFragment) getSupportFragmentManager()
//                    .findFragmentById(R.id.detailFragment);
//        }
//
//        // Now we can define the action to take in the activity when the fragment event fires
//        // This is implementing the `OnItemSelectedListener` interface methods
//        @Override
//        public void onRssItemSelected(String link) {
//            if (fragment != null && fragment.isInLayout()) {
//                fragment.setText(link);
//            }
//        }


        public void showSearchResults() {
            SearchResultsFragment searchResultsFragment = new SearchResultsFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.your_placeholder, searchResultsFragment);
            ft.commit();
        }
    }
}
