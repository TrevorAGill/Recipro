package com.epicodus.recipro;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class RecipePagerAdapter extends FragmentPagerAdapter{
    private ArrayList<Recipe> mRecipes;
    private String mSource;

    public RecipePagerAdapter(FragmentManager fm, ArrayList<Recipe> recipes, String source) {
        super(fm);
        mRecipes = recipes;
        mSource = source;
    }

    @Override
    public Fragment getItem(int position) {
        return RecipeDetailFragment.newInstance(mRecipes, position, mSource);
    }

    @Override
    public int getCount() {
        return 5;
//        return mRecipes.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mRecipes.get(position).getName();
    }
}
