package com.example.a123cook;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PostActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

        private double rating;
        private Recipe recipe;
        private String thePost;
        private String Name;
        private ArrayList<Recipe> allAttemptedRecipes;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_post);
            Intent getRecipe = getIntent(); //receive recipe object from RecipeActivity
            recipe = (Recipe)getRecipe.getSerializableExtra("recToUpdate");
            String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();


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

            allAttemptedRecipes = new ArrayList<Recipe>();
            //TODO: from http://stackoverflow.com/questions/40366717/firebase-for-android-how-can-i-loop-through-a-child-for-each-child-x-do-y
            FirebaseDatabase.getInstance().getReference().child("users").child(userID).child("attemptedRecipes")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Recipe recipe = snapshot.getValue(Recipe.class);
                                if(!(allAttemptedRecipes.contains(recipe))){
                                    allAttemptedRecipes.add(recipe);                     //don't all duplicates
                                    //System.out.println(recipe.name);
                                }
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });

            FirebaseDatabase.getInstance().getReference().child("users").child(userID).child("name").
                    addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Name = (String) dataSnapshot.getValue();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

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

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        EditText editPost = (EditText) findViewById(R.id.edit_post);
        String comment = editPost.getText().toString();

        thePost = Name + " rated this recipe: " + rating + "\n" + comment;

        ////CRITICAL!!!!!!
        ////UPDATED CODE NEEDED HERE TO ADD A NEW PROFILE POST TO A USERS PROFILE!!!!


        if (!allAttemptedRecipes.contains(recipe)) {
            database.getReference().child("users").child(userID).child("attemptedRecipes").push().setValue(recipe);
        }


        recipe.updateRating(rating);
        recipe.addComment(thePost);


        Intent recPage = new Intent(PostActivity.this, RecipeActivity.class);

        recPage.putExtra("recipeObject", recipe);
        recPage.putExtra("check", "PostActivity");

        startActivity(recPage);


    }








}
