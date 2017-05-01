package com.example.a123cook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Michelle on 4/25/2017.
 */

public class SearchRecipesActivity extends AppCompatActivity {
    RecipeDatabase rdb;
    IngredientDB ingre;

    Spinner foodTypeSpinner;
    Spinner difficultySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_recipes);

        foodTypeSpinner = (Spinner)findViewById(R.id.foodTypeSpinner);
        difficultySpinner = (Spinner)findViewById(R.id.diffcultySpinner);
        rdb = new RecipeDatabase();     //TODO: get this part done/to work
        ingre = new IngredientDB();
    }

    public double getPercentIngreCompletion(Recipe r){

//        for(Ingredient i : ingre){
//
//        }

        ArrayList<String> userIngredients = ingre.getUserIngredients();
        HashMap hMap = ingre.getHashMap();

        String key = r.name;
        ArrayList<String> recipeIngredients = hMap.get(key);        //TODO: fix this line

        double percentage = 0;
        int numIngredients = recipeIngredients.size();
        for(int i = 0; i < numIngredients; i++){
            if(userIngredients.contains(recipeIngredients.get(i))){
                percentage++;        //increase the percentage if the user has this recipe ingredient
            }
        }
        percentage = percentage / numIngredients;
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





    public void searchButtonOnClick(View view) {
        String foodTypeSelection = foodTypeSpinner.getSelectedItem().toString();
        String difficultySelection = difficultySpinner.getSelectedItem().toString();

        ArrayList<Recipe> suggestedRecipes = new ArrayList<Recipe>();
        ArrayList<Double> sortPercentage= new ArrayList<Double>();

        for (Recipe r : rdb.getAllRecipes()) {                    //get all recipes that match user selections
            if (foodTypeSelection.equals(r.foodType) && difficultySelection.equals(r.difficulty)) {
                suggestedRecipes.add(r);
            }
        }

//        suggestedRecipes = sortRecipes(suggestedRecipes);         //sort suggested recpies based on user ingredients
//        //return suggestedRecipes;
//        //TODO: launch new window view of suggested recipes in order





        //newly added stuff below
        for(Recipe r : suggestedRecipes){
            sortPercentage.add(getPercentIngreCompletion(r));
        }
        suggestedRecipes = sortRecipes(suggestedRecipes, sortPercentage);	//TODO: make sure to link this with sorting the actual recipeSuggestions array list
        //TODO: call the recipe suggestions display with this recipeSuggestions array list
    }
}