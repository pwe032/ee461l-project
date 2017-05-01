package com.example.a123cook;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;

public class RecipeDatabase {

    private static RecipeDatabase uniqueInstance;
    private static ArrayList<Recipe> allRecipes = new ArrayList<Recipe>();

    public RecipeDatabase() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference AllRecipeRef = database.getReference("allRecipes");
        DatabaseReference newRecipeRef = AllRecipeRef.push();
        newRecipeRef.setValue(new Recipe("Pizza", 1.0, "gn_logo", "Italian", "5", "instr", "ingredients"));
        AllRecipeRef.push().setValue(new Recipe("Sushi Rolls", 2.0, "mkp_logo", "Japanese", "5", "instr", "ingredients"));
        AllRecipeRef.push().setValue(new Recipe("Crepe", 3.5, "mt_logo", "French", "5", "instr", "ingredients"));
        AllRecipeRef.push().setValue(new Recipe("Baked Salmon", 0, "pb_logo", "American", "5", "1. prep ingredients\n2.cook food\n3.eat your food", "ingredients1 ingredient 2 ingredient3"));
    }

    public static RecipeDatabase getUniqueInstance() {

        if (uniqueInstance == null) {

            uniqueInstance = new RecipeDatabase();
        }
        return uniqueInstance;
    }

    public static ArrayList<Recipe> getAllRecipes() {
        allRecipes.add(new Recipe("Pizza", 1.0, "gn_logo", "Italian", "5", "instr", "ingredients"));
        allRecipes.add(new Recipe("Pasta", 1.0, "gn_logo", "Italian", "5", "instr", "ingredients"));

        allRecipes.add(new Recipe("Sushi Rolls", 2.0, "mkp_logo", "Japanese", "5", "instr", "ingredients"));

        allRecipes.add(new Recipe("Crepe", 3.5, "mt_logo", "French", "5", "instr", "ingredients"));

        allRecipes.add(new Recipe("Baked Salmon", 0, "pb_logo", "American", "5", "1. prep ingredients\n2.cook food\n3.eat your food", "ingredients1 ingredient 2 ingredient3"));

        return allRecipes;
    }
}

