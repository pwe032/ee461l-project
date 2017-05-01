package com.example.a123cook;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class PostActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

        private double rating;
        private Recipe recipe;
        private String thePost;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_post);
            Intent getRecipe = getIntent(); //receive recipe object from RecipeActivity
            recipe = (Recipe)getRecipe.getSerializableExtra("recToUpdate");



//          thePost = (String)getRecipe.getSerializableExtra("newComment");

            // Spinner element
            Spinner spinner = (Spinner) findViewById(R.id.spinner1);

            // Spinner click listener
            spinner.setOnItemSelectedListener(this);

            // Spinner Drop down elements
            List<String> ratings = new ArrayList<String>();
            ratings.add("0");
            ratings.add("0.5");
            ratings.add("1");
            ratings.add("1.5");
            ratings.add("2.0");
            ratings.add("2.5");
            ratings.add("3");
            ratings.add("3.5");
            ratings.add("4");
            ratings.add("4.5");
            ratings.add("5");

            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ratings);

            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spinner.setAdapter(dataAdapter);
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            // On selecting a spinner item
            String item = parent.getItemAtPosition(position).toString();

            rating = Double.parseDouble(item);

            // Showing selected spinner item
            Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
        }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void startUpdatedProfileActivity(View view){


        EditText editPost = (EditText) findViewById(R.id.edit_post);
        String comment = editPost.getText().toString();

        thePost = rating + "\n" + comment;

        ////CRITICAL!!!!!!
        ////UPDATED CODE NEEDED HERE TO ADD A NEW PROFILE POST TO A USERS PROFILE!!!!
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        database.getReference().child("users").child(userID).child("attemptedRecipes").push().setValue(recipe);

        recipe.updateRating(rating);
        recipe.addComment(thePost);
        Intent profile = new Intent(PostActivity.this, ProfileActivity.class);
        startActivity(profile);
    }


}
