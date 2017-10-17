package com.epicodus.recipro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import okhttp3.Response;




public class FindRecipeResultsActivity extends AppCompatActivity {
    public static final String TAG = FindRecipeResultsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_recipe_results);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String time = intent.getStringExtra("time");
        getRecipes(time);
    }

    private void getRecipes(String time) {
        final YummlyService yelpService = new YummlyService();
        yelpService.findRecipes(Integer.parseInt(time), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String jsonData = response.body().string();
                    Log.v(TAG, jsonData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}