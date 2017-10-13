package com.epicodus.recipro;

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

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewRecipeActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.addIngredientButton) Button mAddIngredientButton;
    @Bind(R.id.ingredientCount) EditText mIngredientCount;
    @Bind(R.id.ingredientMeasurement) Spinner mIngredientMeasurement;
    @Bind(R.id.ingredientName) EditText mIngredientName;
    @Bind(R.id.listView) ListView mListView;
    ArrayList<String> ingredientList = new ArrayList<>();
    ArrayAdapter<ArrayList<String>> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);
        ButterKnife.bind(this);
        mAddIngredientButton.setOnClickListener(this);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ingredientList);
        mListView.setAdapter(adapter);

    }

    public void onClick(View v) {
        if(v == mAddIngredientButton) {
            String ingredient = createIngredientString();
            ingredientList.add(ingredient);
//            clearIngredientInputs();
            adapter.add(ingredientList);
            adapter.notifyDataSetChanged();
            Log.i("NewRecipeActivity","List includes: " + ingredientList);
        }
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