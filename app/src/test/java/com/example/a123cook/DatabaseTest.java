package com.example.a123cook;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.example.a123cook.Recipe;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class DatabaseTest {
    ArrayList<Recipe> allRecipes = RecipeDatabase.getAllRecipes();

    @After
    public void tearDown() throws Exception {
        allRecipes.clear();
    }

    @Test
    public void instantiation_isCorrect() throws Exception {
        int size = allRecipes.size();
        assertEquals(size, 0);
    }

    @Test
    public void addingRecipe_isCorrect() throws Exception {
        Recipe testRecipe = new Recipe("Pizza", 3.0, "gn_logo", "Italian", "Hard", "instr", "ingredients");
        allRecipes.add(testRecipe);
        assertEquals(allRecipes.size(), 1);
        Recipe rp = allRecipes.get(0);
        assertEquals(rp.foodType, "Italian");
        assertEquals(rp.difficulty, "Hard");
        assertEquals(rp.name, "Pizza");
    }

    @Test
    public void checkRecipeIdentity() throws Exception{
        Recipe testRecipe = new Recipe("Pizza", 3.0, "gn_logo", "Italian", "Hard", "instr", "ingredients");
        Recipe testRecipe2 = new Recipe("Sushi", 2.5, "mkp_logo", "Japanese", "Medium", "instr", "ingredients2");
        allRecipes.add(testRecipe);
        allRecipes.add(testRecipe2);
        assertFalse(allRecipes.get(0).equals(allRecipes.get(1)));
    }

    @Test
    public void testSingletonGetter() throws Exception{
        assertNotNull(RecipeDatabase.getAllRecipes());
        assertEquals(RecipeDatabase.getAllRecipes().size(), 0);
        RecipeDatabase.getAllRecipes().add(new Recipe("Pizza", 3.0, "gn_logo", "Italian", "Hard", "instr", "ingredients"));
        assertEquals(RecipeDatabase.getAllRecipes().size(), 1);
    }

    @Test
    public void testSingletonList() throws Exception{
        assertNotNull(RecipeDatabase.getAllRecipes());
        assertEquals(RecipeDatabase.getAllRecipes().size(), 0);
        RecipeDatabase.getAllRecipes().add(new Recipe("Pizza", 3.0, "gn_logo", "Italian", "Hard", "instr", "ingredients"));
        assertEquals(RecipeDatabase.getAllRecipes().size(), 1);
        ArrayList<Recipe> copy = (ArrayList<Recipe>) RecipeDatabase.getAllRecipes().clone();
        assertFalse(copy == RecipeDatabase.getAllRecipes());
    }

    @Test
    public void testSingletonList2() throws Exception{
        ArrayList<Recipe> copy = RecipeDatabase.getAllRecipes();
        assertTrue(copy == RecipeDatabase.getAllRecipes());
    }
}
