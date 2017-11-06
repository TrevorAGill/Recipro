package com.epicodus.recipro;


import java.util.ArrayList;

public interface OnRecipeSelectedListener {
    public void onRecipeSelected (Integer position, ArrayList<Recipe> restaurants);
}
