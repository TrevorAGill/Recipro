package com.epicodus.recipro;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import butterknife.Bind;
import butterknife.ButterKnife;

    public class InstructionsFragment extends DialogFragment {
        @Bind(R.id.newName) EditText mName;
        @Bind(R.id.newTime) EditText mTime;
        @Bind(R.id.newCuisineSpinner) Spinner mCuisine;
        @Bind(R.id.newCourseSpinner) Spinner mCourse;
        OnSubmitButtonListener mCallback;

        // Container Activity must implement this interface
        public interface OnSubmitButtonListener {
            public void passNewRecipeDetails(String name, String time, String cuisine, String course);
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
            getDialog().setTitle("Simple Dialog");
            submitButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String name = mName.getText().toString();
                    String time = mTime.getText().toString();
                    String cuisine = mCuisine.getSelectedItem().toString();
                    String course = mCourse.getSelectedItem().toString();
                    mCallback.passNewRecipeDetails(name,time,cuisine,course);
                    dismiss();
                }
            });
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            return rootView;

        }
    }