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
    UserIngredientsActivity userIngreAct;

    Spinner foodTypeSpinner;
    Spinner difficultySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_recipes);

        foodTypeSpinner = (Spinner)findViewById(R.id.foodTypeSpinner);
        difficultySpinner = (Spinner)findViewById(R.id.diffcultySpinner);

        rdb = new RecipeDatabase();     //TODO: get this part done/to work with the actual database
        userIngreAct = new UserIngredientsActivity();
    }

    public double getPercentIngreCompletion(Recipe r){

        ArrayList<String> userIngredients = userIngreAct.getUserIngredients();
        if(userIngredients == null){
            return 0;
        }
        ArrayList<String> recipeIngredientsArrList = new ArrayList<String>();

        String[] recipeIngredientsStringArr = r.ingredients.split(" ");

        for(int i = 0; i < recipeIngredientsStringArr.length; i++){
            recipeIngredientsArrList.add(recipeIngredientsStringArr[i]);
        }

        double percentage = 0;
        int numIngredients = recipeIngredientsArrList.size();
        for(int i = 0; i < numIngredients; i++){
            if(userIngredients.contains(recipeIngredientsArrList.get(i))){
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
                if(sortPercentage.get(i) > sortPercentage.get(j)){
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

        System.out.println(foodTypeSelection);      //TODO: delete this testing print statement
        System.out.println(difficultySelection);    //TODO: delete this testing print statement

        ArrayList<Recipe> suggestedRecipes = new ArrayList<Recipe>();
        ArrayList<Double> sortPercentage= new ArrayList<Double>();

        for (Recipe r : rdb.getAllRecipes()) {                    //get all recipes that match user selections
            if (foodTypeSelection.equals(r.foodType) && difficultySelection.equals(r.difficulty)) {
                suggestedRecipes.add(r);
            }
        }

        for(Recipe r : suggestedRecipes){
            sortPercentage.add(getPercentIngreCompletion(r));   //get recipe ingredient completion for each recipe
        }
        sortRecipes(suggestedRecipes, sortPercentage);	        //TODO: make sure to link this with sorting the actual recipeSuggestions array list

        for(Recipe r : suggestedRecipes){
            System.out.println(r.name);          //TODO: delete this testing print statement
        }

        //TODO: call the recipe suggestions display with this recipeSuggestions array list (activity_profile.xml)
    }
}