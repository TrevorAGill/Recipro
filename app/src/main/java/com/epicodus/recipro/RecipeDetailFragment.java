package com.epicodus.recipro;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
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
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RecipeDetailFragment extends Fragment implements View.OnClickListener{
    @Bind(R.id.recipeImageView) ImageView mImageLabel;
    @Bind(R.id.recipeNameTextView) TextView mNameLabel;
    @Bind(R.id.timeTextView) TextView mTimeLabel;
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
        mTimeLabel.setText((Integer.parseInt(mRecipe.getTime())/60) + " minutes");

        mWebsiteLabel.setOnClickListener(this);
        mSaveRecipeButton.setOnClickListener(this);
        int thumbnailLength = mRecipe.getSmallImageURL().length();
        String largeImage = StringUtils.substring(mRecipe.getSmallImageURL(), 0 , thumbnailLength -2) + "360";
        Picasso.with(view.getContext()).load(largeImage).into(mImageLabel);
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
                ArrayList<Object> details = yummlyService.processResults2(response);
                String sourceURL = (String) details.get(0);
                String imageURL = (String) details.get(1);
                ArrayList<String> ingredients = (ArrayList<String>) details.get(2);
                recipe.setSource(sourceURL);
                recipe.setLargeImageURL(imageURL);
                recipe.setIngredients(ingredients);
            }
        });
    }

    public static Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }
}