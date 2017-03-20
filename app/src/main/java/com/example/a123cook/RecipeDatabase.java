package com.example.a123cook;

public class RecipeDatabase {
    //Temporary local database for implementing our application
    //Later, change this to "Internal Storage" or "External Storage" or use Network Connection
    public static Recipe r1 = new Recipe("gn",1.0,"gn_logo","asian","5","instr");
    public static Recipe r2 = new Recipe("mkp",2.0,"mkp_logo","italian","5","instr");
    public static Recipe r3 = new Recipe("mt",3.5,"mt_logo","american","5","instr");
    public static Recipe r4 = new Recipe("pb",0,"pb_logo","american","5","1. prep ingredients\n2.cook food\n3.eat your food");
    public static Recipe[] allRecipes = new Recipe[]{r1,r2,r3,r4};
}
