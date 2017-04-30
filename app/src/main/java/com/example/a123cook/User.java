package com.example.a123cook;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class User {

    private String userID;
    private String email;
    private String name;
    private List<Recipe> attemptedRecipes;
    private FirebaseDatabase database;

    public User() {} //for Firebase data snapshot

    public User(FirebaseUser firebaseUser, String name) {
        this.userID = firebaseUser.getUid();
        this.email = firebaseUser.getEmail();
        this.name = name;
        this.attemptedRecipes = new ArrayList<Recipe>();

        //add to database
        database = FirebaseDatabase.getInstance();
        database.getReference().child("users").child(userID).setValue(this);
        database.getReference().child("users").child(userID).child("attemptedRecipes").push();
    }

    public User(String userID, String email, String name, List<Recipe> attemptedRecipes) {
        this.userID = userID;
        this.email = email;
        this.name = name;
        this.attemptedRecipes = attemptedRecipes;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<Recipe> getAttemptedRecipes() {
        return attemptedRecipes;
    }


    public String getUserID() {
        return userID;
    }


    public void addAttemptedRecipe(Recipe attemptedRecipe) {
        attemptedRecipes.add(attemptedRecipe);
        database.getReference().child("users").child(userID).child("attemptedRecipes").push().setValue(attemptedRecipe);
        //make change to database
    }
}
