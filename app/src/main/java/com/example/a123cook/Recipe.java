package com.example.a123cook;

import com.google.firebase.database.DatabaseReference;
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
    public String recipeID = "";

    public Recipe(){

        this.name = "no name";
        this.rating = 0.0;
        this.numRatings = 0;
        this.imgUrl = "http://smokeybones.com/wp-content/uploads/2015/11/loaded-bbq-burger.jpg";
        this.foodType = "n/a";
        this.difficulty = "n/a";
        this.instructions ="n/a";
        this.ingredients = "n/a";
        this.comments=".";
        this.recipeID = "n/a";
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
        this.comments = ".";
        this.recipeID = "n/a";
        parseIngredients();
    }

    public void updateRating(double newRating){
        //calculate new rating
        rating = ((rating*numRatings) + newRating) / (numRatings + 1);
        numRatings++;
        //round to nearest half for half stars
        rating = (Math.round(rating*2))/2;
    }

    public void parseIngredients(){
        String[] recIng = ingredients.split(" ");
        ingredients = "";
        for (int i=0; i < recIng.length; i++){
            ingredients = ingredients + recIng[i] + "\n";
        }
    }

    public void setRecipeID(String id){
        this.recipeID = id;
    }

    public String getRecipeID(){
        return this.recipeID;
    }

    public void addComment(String newComment){
        comments = comments + newComment + "\n\n\n";

    }


}
