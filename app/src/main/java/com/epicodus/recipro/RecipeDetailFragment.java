package com.epicodus.recipro;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

import static android.R.layout.simple_list_item_1;

public class RecipeDetailFragment extends Fragment implements View.OnClickListener{
    @Bind(R.id.recipeImageView) ImageView mImageLabel;
    @Bind(R.id.recipeNameTextView) TextView mNameLabel;
    @Bind(R.id.timeTextView) TextView mTimeLabel;
    @Bind(R.id.ratingTextView) TextView mRatingLabel;
    @Bind(R.id.websiteTextView) TextView mWebsiteLabel;
    @Bind(R.id.saveRecipeButton) TextView mSaveRecipeButton;
    @Bind(R.id.ingredientListView) ListView mIngredientList;

    private Recipe mRecipe;
    private ArrayList<Recipe> mRecipes;
    private int mPosition;
    private ArrayList<String> ingredients;
    private ArrayAdapter<String> adapter;
    private String mSource;

    public static RecipeDetailFragment newInstance(ArrayList<Recipe> recipes, Integer position, String source) {
        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        Bundle args = new Bundle();

        args.putParcelable(Constants.EXTRA_KEY_RECIPES, Parcels.wrap(recipes));
        args.putInt(Constants.EXTRA_KEY_POSITION, position);
        args.putString(Constants.KEY_SOURCE, source);

        recipeDetailFragment.setArguments(args);
        return recipeDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecipes = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_KEY_RECIPES));
        mPosition = getArguments().getInt(Constants.EXTRA_KEY_POSITION);
        mRecipe = mRecipes.get(mPosition);

        mSource = getArguments().getString(Constants.KEY_SOURCE);
        setHasOptionsMenu(true);

        getRecipe(mRecipe.getId(), mRecipe);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        ButterKnife.bind(this, view);



        mNameLabel.setText(mRecipe.getName());
        mTimeLabel.setText((Integer.parseInt(mRecipe.getTime())/60) + " minutes");

        mWebsiteLabel.setOnClickListener(this);

        if (mSource.equals(Constants.SOURCE_SAVED)) {
            mSaveRecipeButton.setVisibility(View.GONE);
        } else {
            mSaveRecipeButton.setOnClickListener(this);
        }

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
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            DatabaseReference recipeRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_RECIPES)
                    .child(uid);

            DatabaseReference pushRef = recipeRef.push();
            String pushId = pushRef.getKey();
            mRecipe.setPushId(pushId);
            pushRef.setValue(mRecipe);

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
                System.out.println("Details= " + details);
                System.out.println("Details[0] = " + details.get(0));
                String sourceURL = (String) details.get(0);
                String imageURL = (String) details.get(1);
                ingredients = (ArrayList<String>) details.get(2);
                recipe.setSource(sourceURL);
                recipe.setLargeImageURL(imageURL);
                recipe.setIngredients(ingredients);

                adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, ingredients);
                displayIngredients(adapter);

            }
        });
    }

    public void displayIngredients(final ArrayAdapter adapter) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mIngredientList.setAdapter(adapter);
            }
        });
    }

    public static Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }
}