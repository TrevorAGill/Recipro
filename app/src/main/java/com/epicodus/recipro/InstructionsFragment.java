package com.epicodus.recipro;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;

    public class InstructionsFragment extends DialogFragment {
        @Bind(R.id.newName) EditText mName;
        @Bind(R.id.newTime) EditText mTime;
        @Bind(R.id.newCuisineSpinner) Spinner mCuisine;
        @Bind(R.id.newCourseSpinner) Spinner mCourse;
        OnSubmitButtonListener mCallback;
        private static final int REQUEST_IMAGE_CAPTURE = 111;
        String image = "";

        // Container Activity must implement this interface
        public interface OnSubmitButtonListener {
            public void passNewRecipeDetails(String name, String time, String cuisine, String course, String image);
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);

            // This makes sure that the container activity has implemented
            // the callback interface. If not, it throws an exception
            try {
                mCallback = (OnSubmitButtonListener) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString()
                        + " must implement OnSubmitButtonListener");
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_instructions, container, false);
            ButterKnife.bind(this,rootView);
            Button cancelButton = (Button) rootView.findViewById(R.id.cancelButton);
            Button submitButton = (Button) rootView.findViewById(R.id.submitButton);
            Button photoButton = (Button) rootView.findViewById(R.id.photoButton);
            getDialog().setTitle("Simple Dialog");

            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = mName.getText().toString();
                    String time = mTime.getText().toString();
                    String cuisine = mCuisine.getSelectedItem().toString();
                    String course = mCourse.getSelectedItem().toString();
                    mCallback.passNewRecipeDetails(name,time,cuisine,course,image);
                    dismiss();
                }
            });
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            photoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onLaunchCamera();
                }
            });

            return rootView;

        }

        public void onLaunchCamera() {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
//                mImageLabel.setImageBitmap(imageBitmap);
                encodeBitmapAndSaveToFirebase(imageBitmap);
            }
        }

        public void encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            image = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);


//            DatabaseReference ref = FirebaseDatabase.getInstance()
//                    .getReference(Constants.FIREBASE_CHILD_RECIPES)
//                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                    .child(mRecipe.getPushId())
//                    .child("imageUrl");
//            ref.setValue(imageEncoded);
        }
    }