package com.example.a123cook;

import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;


public class Recipe implements Serializable{

    public String name;
    public double rating;
    public double numRatings;
    public String imgUrl;
    public String foodType;
    public String difficulty;
    public String instructions;
    public String ingredients; //INCLUDES INGREDIENTS
    public String comments = "";

    public Recipe(){

        this.name = "no name";
        this.rating = 0.0;
        this.numRatings = 0;
        this.imgUrl = "mkp_logo";
        this.foodType = "n/a";
        this.difficulty = "n/a";
        this.instructions ="n/a";
        this.ingredients = "n/a";
    }

    public Recipe(String name, double rating, String imgUrl,String foodType, String difficulty, String instructions, String ingredients){

        this.name = name;
        this.rating = rating;
        this.numRatings = rating;
        this.imgUrl = imgUrl;
        this.foodType = foodType;
        this.difficulty = difficulty;
        this.instructions = instructions;
        this.ingredients = ingredients;
    }

    public void updateRating(double newRating){
        //calculate new rating
        rating = ((rating*numRatings) + newRating) / (numRatings + 1);
        numRatings++;
        //round to nearest half for half stars
        rating = (Math.round(rating*2))/2;
    }

    public void addComment(String newComment){
        comments = comments + newComment + "\n\n\n";

    }

    public static void addRecipeToDB(Recipe rec){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference("allRecipes").push().setValue(rec);

    }


}
