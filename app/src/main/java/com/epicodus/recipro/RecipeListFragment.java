package com.epicodus.recipro;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeListFragment extends Fragment {
    public String time = "";
    String[] allowedIngredients;
    String[] excludedIngredients;
    String course = "";
    String cuisine = "";
    private OnRecipeSelectedListener mOnRecipeSelectedListener;

    public static final String TAG = RecipeListActivity.class.getSimpleName();
    public ArrayList<Recipe> recipes = new ArrayList<>();
    private RecipeListAdapter mAdapter;
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Override
    public void onActivityCreated(Bundle bundle){
        super.onActivityCreated(bundle);
        time = ((RecipeListActivity)this.getActivity()).getTime();
        course = ((RecipeListActivity)this.getActivity()).getCourse();
        cuisine = ((RecipeListActivity)this.getActivity()).getCuisine();
        allowedIngredients = ((RecipeListActivity)this.getActivity()).getAllowedIngredients();
        excludedIngredients = ((RecipeListActivity)this.getActivity()).getExcludedIngredients();
        getRecipes(time,allowedIngredients,excludedIngredients,course,cuisine);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnRecipeSelectedListener = (OnRecipeSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + e.getMessage());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_recipe_list);

    }


    public RecipeListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        ButterKnife.bind(this, view);



        return view;
    }



    private void getRecipes(String time, String[] allowedIngredients, String[] excludedIngredients, String course, String cuisine) {
        final YummlyService yummlyService = new YummlyService();
        yummlyService.findRecipes(time, allowedIngredients, excludedIngredients, course, cuisine, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                recipes = yummlyService.processResults(response);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new RecipeListAdapter(getActivity(), recipes, mOnRecipeSelectedListener);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);
                    }
                });

            }
        });
    }

}
