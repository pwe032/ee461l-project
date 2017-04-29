package com.example.a123cook;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

/**
 * The User class helps to maintain information about the user
 * (i.e. recipes they have tried, image, userID, etc.)
 * By: Gaurav Nagar
 * Date: 4/29/2017
 */

public class User {

    private String userID;
    private String email;
    private String name;
    private ArrayList<Recipe> attemptedRecipes;

    public User() {} //for Firebase data snapshot

    public User(FirebaseUser firebaseUser) {
        this.userID = firebaseUser.getUid();
        this.email = firebaseUser.getEmail();
        this.name = firebaseUser.getDisplayName();
        this.attemptedRecipes = new ArrayList<Recipe>();
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

    public ArrayList<Recipe> getAttemptedRecipes() {
        return attemptedRecipes;
    }

    public String getUserID() {
        return userID;
    }

    public void addAttemptedRecipe(Recipe attemptedRecipe) {
        attemptedRecipes.add(attemptedRecipe);
        //make change to database
    }
}
