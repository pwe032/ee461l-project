package com.example.a123cook;


import android.net.Uri;
import com.google.firebase.auth.FirebaseAuth;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class User implements Serializable{

    private String userID;
    private String email;
    private String name;
    private String photoUrl;
    private List<Recipe> attemptedRecipes;

    public User() {} //for Firebase data snapshot
    private ArrayList<String> userIngredients;

    public User(FirebaseUser firebaseUser, String name) {
        this.userID = firebaseUser.getUid();
        this.email = firebaseUser.getEmail();
        this.name = name;
        this.photoUrl = "http://www.t5a.com/wp-content/uploads/2014/03/bluehead.png";
        this.attemptedRecipes = new ArrayList<Recipe>();
        this.userIngredients = new ArrayList<String>();

        //add to database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference().child("users").child(userID).setValue(this);
        database.getReference().child("users").child(userID).child("attemptedRecipes").push();
        database.getReference().child("users").child(userID).child("userIngredients").push();

    }

    public User(String userID, String email, String name, List<Recipe> attemptedRecipes, ArrayList<String> userIngredients) { //Datasnapshot
        this.userID = userID;
        this.email = email;
        this.name = name;
        this.attemptedRecipes = attemptedRecipes;
        this.userIngredients = userIngredients;
    }
    public User(String userID, String email, String name) { //Datasnapshot
        this.userID = userID;
        this.email = email;
        this.name = name;
        this.attemptedRecipes = attemptedRecipes;
        this.userIngredients = userIngredients;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhotoUrl(){return photoUrl;}

    public void setPhotoUri(String url){photoUrl = url;}

    public List<Recipe> getAttemptedRecipes() {
        return attemptedRecipes;
    }


    public String getUserID() {
        return userID;
    }


    public void addAttemptedRecipe(Recipe attemptedRecipe) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        attemptedRecipes.add(attemptedRecipe);
        database.getReference().child("users").child(userID).child("attemptedRecipes").push().setValue(attemptedRecipe);
        //make change to database
    }

    public void addUserIngredients(String userIngredient) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        userIngredients.add(userIngredient);
        database.getReference().child("users").child(userID).child("userIngredients").push().setValue(userIngredient);
        //make change to database
    }
}
