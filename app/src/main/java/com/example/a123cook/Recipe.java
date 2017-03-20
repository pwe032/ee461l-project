package com.example.a123cook;

import java.util.ArrayList;


public class Recipe {
    public String name;
    public double rating;
    public int numRatings;
    public String imgUrl;
    public String foodType;
    public String difficulty;
    public String instructions; //INCLUDES INGREDIENTS

    public ArrayList<String> comments = new ArrayList<String>();


    public Recipe(String name, double rating, String imgUrl,String foodType, String difficulty, String instructions){
        this.name = name;
        this.rating = rating;
        this.numRatings = 0;
        this.imgUrl = null;
        this.foodType = null;
        this.difficulty = difficulty;
        this.instructions = instructions;
    }

    public void updateRating(double newRating){
        //calculate new rating
        rating = ((rating*numRatings) + newRating) / (numRatings + 1);
        numRatings++;
        //round to nearest half for half stars
        rating = (Math.round(rating*2))/2;
    }

    public void addComment(String newComment){
        comments.add(newComment);
    }



}
