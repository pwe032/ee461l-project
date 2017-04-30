package com.example.a123cook;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * The User class helps to maintain information about the user
 * (i.e. recipes they have tried, image, userID, etc.)
 * By: Gaurav Nagar
 * Date: 4/29/2017
 */

public class User {

    private static String userID;
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

    public User(String userID, String email, String name) {
        this.userID = userID;
        this.email = email;
        this.name = name;
        this.attemptedRecipes = new ArrayList<Recipe>();
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

    public static String getUserID() {
        return userID;
    }

    public void addAttemptedRecipe(Recipe attemptedRecipe) {
        attemptedRecipes.add(attemptedRecipe);
        database.getReference().child("users").child(userID).child("attemptedRecipes").push().setValue(attemptedRecipe);
        //make change to database
    }
}
