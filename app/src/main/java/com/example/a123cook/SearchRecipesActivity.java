package com.example.a123cook;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Michelle on 4/25/2017.
 */

public class SearchRecipesActivity extends MainActivity {
//    RecipeDatabase rdb;
//    UserIngredientsActivity userIngreAct;

    Spinner foodTypeSpinner;
    Spinner difficultySpinner;

    private static ArrayList<Recipe> allRecipes;
    private static ArrayList<String> userIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_recipes);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);
        foodTypeSpinner = (Spinner)findViewById(R.id.foodTypeSpinner);
        difficultySpinner = (Spinner)findViewById(R.id.diffcultySpinner);

//        rdb = new RecipeDatabase();     //TODO: get this part done/to work with the actual database
//        userIngreAct = new UserIngredientsActivity();

        allRecipes = new ArrayList<Recipe>();
        //TODO: from http://stackoverflow.com/questions/40366717/firebase-for-android-how-can-i-loop-through-a-child-for-each-child-x-do-y
        FirebaseDatabase.getInstance().getReference().child("allRecipes")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Recipe recipe = snapshot.getValue(Recipe.class);
                            if(!(allRecipes.contains(recipe))){
                                allRecipes.add(recipe);                     //don't all duplicates
                                System.out.println(recipe.name);
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

        userIngredients = new ArrayList<String>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().child("users").child(userID).child("userIngredients")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String ingredient = (String) snapshot.getValue();
                            if(!(userIngredients.contains(ingredient))){
                                userIngredients.add(ingredient);                     //don't all duplicates
                                System.out.println(ingredient);
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

//        System.out.println("All of the recipes in the database: ");
////        for(Recipe r : allRecipes){
////            System.out.println(r.name);
////        }
//        System.out.println(allRecipes.get(0).name);
//        System.out.println(allRecipes.get(1).name);




        Button searchRec = (Button)findViewById(R.id.searchButton);
        searchRec.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                searchButtonOnClick();
            }
        });
    }

    public double getPercentIngreCompletion(Recipe r){

//        ArrayList<String> userIngredients = new ArrayList<String>();// = userIngreAct.getUserIngredients();
//
//        if(r.name.equals("Pasta")){
//            userIngredients.add("ingredients");
//        }
//        else{
//            userIngredients.add("random");
//        }

        if(userIngredients == null){        //user doesn't have any ingredients
            return 0;
        }

        String[] recipeIngredientsStringArr = r.ingredients.split(" ");
//        String temp = "ingredients ins";
//        String[] recipeIngredientsStringArr = {"ingredients", "ins"};// r.ingredients.split(" ");
        System.out.println("String[]: ");
        System.out.println("Length: " + recipeIngredientsStringArr.length);
//        System.out.println("Should be ingredients: " + recipeIngredientsStringArr[0]);
//        System.out.println("Should be ins: " + recipeIngredientsStringArr[1]);        //TODO: delete testing print statements

        ArrayList<String> recipeIngredientsArrList = new ArrayList<String>();
        for(int i = 0; i < recipeIngredientsStringArr.length; i++){
            recipeIngredientsArrList.add(recipeIngredientsStringArr[i]);
        }

        System.out.println("ArrayList<String>: ");
        System.out.println("Should be ingredients: " + recipeIngredientsArrList.get(0));
        System.out.println("Should be ins: " + recipeIngredientsArrList.get(1));        //TODO: delete testing print statements


        double percentage = 0;
        int numIngredients = recipeIngredientsArrList.size();
        for(int i = 0; i < numIngredients; i++){
            if(userIngredients.contains(recipeIngredientsArrList.get(i))){
                percentage++;        //increase the percentage if the user has this recipe ingredient
            }
        }
        percentage = percentage / numIngredients;
        System.out.println("Percentage: " + percentage);        //TODO: delete testing print statement
        return percentage;
    }

    public ArrayList<Recipe> sortRecipes(ArrayList<Recipe> recipeSuggestions, ArrayList<Double> sortPercentage){
        //Int i = 0;
        //for(Recipe r : recipeSuggestions){
        for(int i = 0; i < recipeSuggestions.size() - 1; i++){
            for(int j = 0; j < recipeSuggestions.size(); j++){
                if(sortPercentage.get(i) < sortPercentage.get(j)){
                    //swap
                    Recipe tempRecipe = recipeSuggestions.get(i);
                    recipeSuggestions.set(i, recipeSuggestions.get(j));
                    recipeSuggestions.set(j, tempRecipe);
                }
            }
        }
        return recipeSuggestions;
    }

    public ArrayList<Recipe> getAllRecipes(){
        return allRecipes;
    }


    public void searchButtonOnClick() {
        System.out.println("Inside of search button on click.");

        String foodTypeSelection = foodTypeSpinner.getSelectedItem().toString();
        String difficultySelection = difficultySpinner.getSelectedItem().toString();

        System.out.println("Spinner values:" );
        System.out.println(foodTypeSelection);      //TODO: delete this testing print statement
        System.out.println(difficultySelection);    //TODO: delete this testing print statement
        System.out.println();


        ArrayList<Recipe> suggestedRecipes = new ArrayList<Recipe>();
        ArrayList<Double> sortPercentage= new ArrayList<Double>();

        //TODO: change the rdb.getAllRecipes() below
        for (Recipe r : getAllRecipes()) {                    //get all recipes that match user selections
            if (foodTypeSelection.equals(r.foodType) && difficultySelection.equals(r.difficulty)) {
                if(!(suggestedRecipes.contains(r))){          //don't add duplicates
                    suggestedRecipes.add(r);
                }
            }
        }

        if(suggestedRecipes.size() > 1) {       //TODO: make sure lists of 1 recipe don't show twice in next activity
            for (Recipe r : suggestedRecipes) {
                sortPercentage.add(getPercentIngreCompletion(r));   //get recipe ingredient completion for each recipe
            }

            System.out.println("Percentages stored: ");
            for(Double d : sortPercentage){
                System.out.println(d);           //TODO: delete this testing print statement
            }
            suggestedRecipes = sortRecipes(suggestedRecipes, sortPercentage);            //TODO: make sure to link this with sorting the actual recipeSuggestions array list
        }

        System.out.println("All of the suggested recipes:" );
        for(Recipe r : suggestedRecipes){
            System.out.println(r.name);          //TODO: delete this testing print statement
        }

        //TODO: call the recipe suggestions display with this recipeSuggestions array list (activity_profile.xml)
        Intent displayRecipeSuggestions = new Intent(this, DisplayRecipeSuggestionsActivity.class);
        displayRecipeSuggestions.putExtra("suggestedRecipes", suggestedRecipes);
        startActivity(displayRecipeSuggestions);
    }
}