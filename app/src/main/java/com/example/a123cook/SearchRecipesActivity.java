package com.example.a123cook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by Michelle on 4/25/2017.
 */

public class SearchRecipesActivity extends AppCompatActivity {
    RecipeDatabase rdb;

    Spinner foodTypeSpinner;
    Spinner difficultySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_recipes);

        foodTypeSpinner = (Spinner)findViewById(R.id.foodTypeSpinner);
        difficultySpinner = (Spinner)findViewById(R.id.diffcultySpinner);
        rdb = new RecipeDatabase();
    }

    public ArrayList<Recipe> searchButtonOnClick(View view) {
        String foodTypeSelection = foodTypeSpinner.getSelectedItem().toString();
        String difficultySelection = difficultySpinner.getSelectedItem().toString();

        ArrayList<Recipe> suggestedRecipes = new ArrayList<Recipe>();

        for (Recipe r : rdb.getAllRecipes()) {                    //get all recipes that match user selections
            if (foodTypeSelection.equals(r.foodType)) {
                suggestedRecipes.add(r);
            }
            if (difficultySelection.equals(r.difficulty)) {
                suggestedRecipes.add(r);
            }
        }


        //suggestedRecipes.sort(<enter Comparator>);
        for (Recipe r : suggestedRecipes) {                         //TODO: sort suggested recpies based on user ingredients

        }

        return suggestedRecipes;
    }
}