package com.epicodus.recipro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FindRecipesActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.timeEditText) EditText mTime;
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
            Intent intent = new Intent(FindRecipesActivity.this, FindRecipeResultsActivity.class);
            intent.putExtra("time", time);
            startActivity(intent);
        }
    }
}
