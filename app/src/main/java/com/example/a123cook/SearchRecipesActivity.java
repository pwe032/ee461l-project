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
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Michelle on 4/25/2017.
 */

public class SearchRecipesActivity extends MainActivity {
    Spinner foodTypeSpinner;
    Spinner difficultySpinner;

    private static ArrayList<Recipe> allDatabaseRecipes;
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

        allDatabaseRecipes = new ArrayList<Recipe>();
        //TODO: from http://stackoverflow.com/questions/40366717/firebase-for-android-how-can-i-loop-through-a-child-for-each-child-x-do-y
        FirebaseDatabase.getInstance().getReference().child("allRecipes")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Recipe recipe = snapshot.getValue(Recipe.class);
//                            if(!(allDatabaseRecipes.contains(recipe))){
                            allDatabaseRecipes.add(recipe);                     //don't all duplicates
//                                System.out.println(recipe.name);
//                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });


        userIngredients = new ArrayList<String>();
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().child("users").child(userID).child("userIngredients")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String ingredient = snapshot.getValue(String.class);
//                            if(!(allDatabaseRecipes.contains(recipe))){
                            userIngredients.add(ingredient);                     //don't all duplicates
//                                System.out.println(recipe.name);
//                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });


        Button searchRec = (Button)findViewById(R.id.searchButton2);
        searchRec.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                searchButtonOnClick();
            }
        });
    }

    public double getPercentIngreCompletion(Recipe r){
        if(userIngredients == null){        //user doesn't have any ingredients
            return 0;
        }

        String[] recipeIngredientsStringArr = r.ingredients.split("\n");
        System.out.println("Size of String[]: " + recipeIngredientsStringArr.length);

        System.out.println("Ingredients the recipe needs: (from the String[])");
        for(int i = 0; i < recipeIngredientsStringArr.length; i++){                     //TODO: delete testing print statements
            System.out.println(recipeIngredientsStringArr[i]);
        }

        ArrayList<String> recipeIngredientsArrList = new ArrayList<>(Arrays.asList(recipeIngredientsStringArr));

        System.out.println("User ingredients check: ");
        System.out.println("Size of ArrayList<String>: " + recipeIngredientsArrList.size());
        for(int i = 0; i < recipeIngredientsArrList.size(); i++){
            System.out.println(recipeIngredientsArrList.get(i));
        }

        System.out.println("User ingredients check: ");
        for(String s : userIngredients){
            System.out.println(s);
        }

        double percentage = 0;
        int numIngredients = recipeIngredientsArrList.size();
        System.out.println("Size of ArrayList<String>: " + numIngredients);
        for(int i = 0; i < numIngredients; i++){
            String currentIngredient = recipeIngredientsArrList.get(i);
            System.out.println("Current ingredient: " + currentIngredient);
            if(userIngredients.contains(currentIngredient)){
                percentage++;        //increase the percentage if the user has this recipe ingredient
                System.out.println("percentage++");
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
        return allDatabaseRecipes;
    }


    public void searchButtonOnClick() {
        System.out.println("Inside of search button on click.");

        String foodTypeSelection = foodTypeSpinner.getSelectedItem().toString();
        String difficultySelection = difficultySpinner.getSelectedItem().toString();
        ArrayList<Recipe> suggestedRecipes = new ArrayList<Recipe>();
        ArrayList<Double> sortPercentage= new ArrayList<Double>();

        for (Recipe r : allDatabaseRecipes) {                    //get all recipes that match user selections
            if (foodTypeSelection.equals(r.foodType) && difficultySelection.equals(r.difficulty)) {
                if(!(suggestedRecipes.contains(r))){          //don't add duplicates
                    suggestedRecipes.add(r);
                    System.out.println("Suggest this recipe: " + r.name);
                }
            }
        }

        if(suggestedRecipes.size() > 1) {
            for (Recipe r : suggestedRecipes) {
                sortPercentage.add(getPercentIngreCompletion(r));   //get recipe ingredient completion for each recipe
            }

            suggestedRecipes = sortRecipes(suggestedRecipes, sortPercentage);
        }

        Intent displayRecipeSuggestions = new Intent(this, DisplayRecipeSuggestionsActivity.class);
        displayRecipeSuggestions.putExtra("suggestedRecipes", suggestedRecipes);
        startActivity(displayRecipeSuggestions);
    }
}