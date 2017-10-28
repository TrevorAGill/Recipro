package com.epicodus.recipro;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewRecipeActivity extends AppCompatActivity implements View.OnClickListener, InstructionsFragment.OnSubmitButtonListener {
    @Bind(R.id.addIngredientButton) Button mAddIngredientButton;
    @Bind(R.id.addInstructionsButton) Button mAddInstructionsButton;
    @Bind(R.id.saveRecipeButton) Button mSaveRecipeButton;
    @Bind(R.id.ingredientCount) EditText mIngredientCount;
    @Bind(R.id.ingredientMeasurement) Spinner mIngredientMeasurement;
    @Bind(R.id.ingredientName) EditText mIngredientName;
    @Bind(R.id.listView) ListView mListView;
    private String name;
    private String time;
    private String cuisine;
    private String course;
    private ArrayList<String> ingredientList;
    private ArrayAdapter<String> adapter;
    private DatabaseReference mSavedRecipeReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);
        ButterKnife.bind(this);
        ingredientList = new ArrayList<String>();
        mAddInstructionsButton.setOnClickListener(this);
        mAddIngredientButton.setOnClickListener(this);
        mSaveRecipeButton.setOnClickListener(this);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ingredientList);
        mListView.setAdapter(adapter);

        mSavedRecipeReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.FIREBASE_SAVED_RECIPE);
    }

    public void passNewRecipeDetails(String name2, String time2, String cuisine2, String course2) {
        name = name2;
        time = time2;
        cuisine = cuisine2;
        course = course2;
    }


    public void onClick(View v) {
        if(v == mAddIngredientButton) {
            if(mIngredientName.getText().toString().trim().equalsIgnoreCase("") || mIngredientMeasurement.getSelectedItem().toString().trim().equalsIgnoreCase("") || mIngredientCount.getText().toString().trim().equalsIgnoreCase("")) {
                Toast answerStatus = Toast.makeText(NewRecipeActivity.this, "Fill out all fields", Toast.LENGTH_LONG);
                answerStatus.show();
            } else {
                String ingredient = createIngredientString();
                ingredientList.add(ingredient);
                adapter.notifyDataSetChanged();
                clearIngredientInputs();
                Log.i("NewRecipeActivity", "List includes: " + ingredientList);
            }
        }
        if(v == mAddInstructionsButton) {
            FragmentManager fm = getFragmentManager();
            InstructionsFragment instructionsFragment = new InstructionsFragment();
            instructionsFragment.show(fm, "Sample Fragment");
        }
        if(v == mSaveRecipeButton) {
            Recipe newRecipe = new Recipe(name, ingredientList, time, course, cuisine);
            saveRecipeToFireBase(newRecipe);
            Intent intent = new Intent(NewRecipeActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void saveRecipeToFireBase(Recipe newRecipe) {
        mSavedRecipeReference.setValue(newRecipe);
    }

    public String createIngredientString() {
        String name = mIngredientName.getText().toString();
        String measurement = mIngredientMeasurement.getSelectedItem().toString();
        String count = mIngredientCount.getText().toString();
        String ingredient = String.format("%s %s %s", count, measurement, name);
        return ingredient;

    }

    public void clearIngredientInputs() {
        mIngredientName.setText("");
        mIngredientCount.setText("");
        mIngredientMeasurement.setSelection(0);
    }

}