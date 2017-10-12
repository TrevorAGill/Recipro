package com.epicodus.recipro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewRecipeActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.addIngredientButton) Button mAddIngredientButton;
    @Bind(R.id.ingredientCount) EditText mIngredientCount;
    @Bind(R.id.ingredientMeasurement) Spinner mIngredientMeasurement;
    @Bind(R.id.ingredientName) EditText mIngredientName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);
        ButterKnife.bind(this);
        mAddIngredientButton.setOnClickListener(this);
    }

    public void onClick(View v) {
        if(v == mAddIngredientButton) {
            String name = mIngredientName.getText().toString();
            String measurement = mIngredientMeasurement.getSelectedItem().toString();
            String count = mIngredientCount.getText().toString();
            clearIngredientInputs();
            Log.i("NewRecipeActivity", "Data" + count + name + measurement);
        }
    }

    public void clearIngredientInputs() {
        mIngredientName.setText("");
        mIngredientCount.setText("");
        mIngredientMeasurement.setSelection(0);
    }
}
