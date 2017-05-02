package com.example.a123cook;

import android.content.Intent;
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

public class SearchRecipesActivity extends AppCompatActivity {
    RecipeDatabase rdb;
    UserIngredientsActivity userIngreAct;

    Spinner foodTypeSpinner;
    Spinner difficultySpinner;

    private static ArrayList<Recipe> allRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_recipes);

        foodTypeSpinner = (Spinner)findViewById(R.id.foodTypeSpinner);
        difficultySpinner = (Spinner)findViewById(R.id.diffcultySpinner);

        rdb = new RecipeDatabase();     //TODO: get this part done/to work with the actual database
        userIngreAct = new UserIngredientsActivity();







//        allRecipes = new ArrayList<Recipe>();


//        FirebaseDatabase.getInstance().getReference().child("allRecipes").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot recipe : dataSnapshot.getChildren()){
//                    allRecipes.add((Recipe) recipe.getValue());             //populates on start of activity
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });



//        FirebaseDatabase.getInstance()
//                .getReference()
//                .child("myRecipes")
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        Iterator<DataSnapshot> dataSnapshots = dataSnapshot.getChildren()
//                                .iterator();
//                        allRecipes = new ArrayList<>();
//                        while (dataSnapshots.hasNext()) {
//                            DataSnapshot dataSnapshotChild = dataSnapshots.next();
//                            User user = dataSnapshotChild.getValue(User.class);
//                            if (!TextUtils.equals(user.getUserID(),
//                                    FirebaseAuth.getInstance().getCurrentUser().getUid())) {
//                                allRecipes.add(user);
//                            }
//                        }
//                        // All users are retrieved except the one who is currently logged
//                        // in device.
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        // Unable to retrieve the users.
//                    }
//                });


//        System.out.println("All of the recipes in the database: ");
//        for(Recipe r : allRecipes){
//            System.out.println(r.name);
//        }





        Button searchRec = (Button)findViewById(R.id.searchButton);
        searchRec.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                searchButtonOnClick();
            }
        });
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

//    public ArrayList<Recipe> getAllRecipes(){
//        return allRecipes;
//    }


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
        for (Recipe r : rdb.getAllRecipes()) {                    //get all recipes that match user selections
            if (foodTypeSelection.equals(r.foodType) && difficultySelection.equals(r.difficulty)) {
                suggestedRecipes.add(r);
            }
        }

        for(Recipe r : suggestedRecipes){
            sortPercentage.add(getPercentIngreCompletion(r));   //get recipe ingredient completion for each recipe
        }
        suggestedRecipes = sortRecipes(suggestedRecipes, sortPercentage);	        //TODO: make sure to link this with sorting the actual recipeSuggestions array list


        System.out.println("All of the suggested recipes:" );
        for(Recipe r : suggestedRecipes){
            System.out.println(r.name);          //TODO: delete this testing print statement
        }

//        suggestedRecipes.add(new Recipe("Pizza", 1.0, "gn_logo", "Italian", "5", "instr", "ingredients"));  //TODO: delete this testing

        //TODO: call the recipe suggestions display with this recipeSuggestions array list (activity_profile.xml)
        Intent displayRecipeSuggestions = new Intent(this, DisplayRecipeSuggestionsActivity.class);
        displayRecipeSuggestions.putExtra("suggestedRecipes", suggestedRecipes);
        startActivity(displayRecipeSuggestions);
    }
}