package com.example.a123cook;

import java.util.ArrayList;

public class RecipeDatabase {
    //Temporary local database for implementing our application
    private static RecipeDatabase uniqueInstance;
    private static ArrayList<Recipe> allRecipes = new ArrayList<Recipe>();
    //Later, change this to "Internal Storage" or "External Storage" or use Network Connection
    private RecipeDatabase() {
        Recipe r1 = new Recipe("Pizza", 1.0, "gn_logo", "Italian", "5", "instr");
        Recipe r2 = new Recipe("Sushi Rolls", 2.0, "mkp_logo", "Japanese", "5", "instr");
        Recipe r3 = new Recipe("Crepe", 3.5, "mt_logo", "French", "5", "instr");
        Recipe r4 = new Recipe("Baked Salmon", 0, "pb_logo", "American", "5", "1. prep ingredients\n2.cook food\n3.eat your food");
        allRecipes.add(r1);
        allRecipes.add(r2);
        allRecipes.add(r3);
        allRecipes.add(r4);
    }

    public static RecipeDatabase getUniqueInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new RecipeDatabase();
        }
        return uniqueInstance;
    }
    public static ArrayList<Recipe> getAllRecipes() {
        return allRecipes;
    }
}

