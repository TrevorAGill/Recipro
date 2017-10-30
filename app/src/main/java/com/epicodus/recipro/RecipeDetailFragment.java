package com.epicodus.recipro;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;
import org.parceler.Parcels;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RecipeDetailFragment extends Fragment implements View.OnClickListener{
    @Bind(R.id.recipeImageView) ImageView mImageLabel;
    @Bind(R.id.recipeNameTextView) TextView mNameLabel;
    @Bind(R.id.cuisineTextView) TextView mCategoriesLabel;
    @Bind(R.id.ratingTextView) TextView mRatingLabel;
    @Bind(R.id.websiteTextView) TextView mWebsiteLabel;
    @Bind(R.id.saveRecipeButton) TextView mSaveRecipeButton;

    private Recipe mRecipe;
    private Context mContext;

    public static RecipeDetailFragment newInstance(Recipe recipe) {
        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("recipe", Parcels.wrap(recipe));
        recipeDetailFragment.setArguments(args);
        return recipeDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecipe = Parcels.unwrap(getArguments().getParcelable("recipe"));
        getRecipe(mRecipe.getId(), mRecipe);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        ButterKnife.bind(this, view);



        mNameLabel.setText(mRecipe.getName());
        mCategoriesLabel.setText((Integer.parseInt(mRecipe.getTime())/60) + " minutes");

        mWebsiteLabel.setOnClickListener(this);
        mSaveRecipeButton.setOnClickListener(this);
        int thumbnailLength = mRecipe.getSmallImageURL().length();
        String largeImage = StringUtils.substring(mRecipe.getSmallImageURL(), 0 , thumbnailLength -2) + "360";
        Picasso.with(view.getContext()).load(largeImage).into(mImageLabel);
//        Picasso.with(view.getContext()).load("https://lh3.googleusercontent.com/2sjI93FnOV7SSSPXrGRKCWTfenwnkkx9u8C4SjqqtZ3ZwO5aOB5KAVx5zaqsknFGphjKjE0vmtpFOMuwxZbpKA=s360").into(mImageLabel);

//        mRatingLabel.setText(mRecipe.getRating() + "/5");

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == mWebsiteLabel) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(mRecipe.getSource()));
            startActivity(webIntent);
        }
        if (v == mSaveRecipeButton) {
            DatabaseReference recipeRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_RECIPES);
            recipeRef.push().setValue(mRecipe);
            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
        }
    }

    private void getRecipe(String id, final Recipe recipe) {
        final YummlyService yummlyService = new YummlyService();

        yummlyService.getRecipeAPI(id, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String[] details = yummlyService.processResults2(response);
                String sourceURL = details[0];
                String imageURL = details[1];
                recipe.setSource(sourceURL);
                recipe.setLargeImageURL(imageURL);
//                String image = mRecipe.getLargeImageURL();
//                Picasso.with(mContext).load(image).into(mImageLabel);

            }
        });
    }
}