package com.example.a123cook;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MakeRecipeActivity extends MainActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makerecipe);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);

    }

    public void startUploadActivity(View view){
        EditText recNameGet = (EditText) findViewById(R.id.editTextRecName);
        String recName = recNameGet.getText().toString();

        EditText recFoodType = (EditText) findViewById(R.id.editTextFoodType);
        String foodType = recFoodType.getText().toString();

        //////!!! LOOK HERE
        //CRITICAL!!! VERY VERY IMPORTANT!!!
        //NEED AN INSTRUCTION HERE TO ACTUALLY ADD RECIPE TO DATABASE!!!!

        EditText recDifficulty = (EditText) findViewById(R.id.editTextDifficulty);
        String difficulty = recDifficulty.getText().toString();

        EditText recIngredients = (EditText) findViewById(R.id.editTextIngredients);
        String ingredients = recIngredients.getText().toString();

        EditText recInstructions = (EditText) findViewById(R.id.editTextInsructions);
        String instructions = recInstructions.getText().toString();

        EditText recPictureUrl = (EditText) findViewById(R.id.editTextPictureURL);
        String imgSrc = recPictureUrl.getText().toString();

        Recipe newRec = new Recipe(recName,0, imgSrc,foodType,difficulty,instructions,ingredients);
        //add this recipe to Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference AllRecipeRef = database.getReference("allRecipes");
        DatabaseReference newRecipeRef = AllRecipeRef.push();
        String id = newRecipeRef.getKey();
        newRec.setRecipeID(id);
        newRecipeRef.setValue(newRec);
        //also, add this to this user's triedRecipe
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String UID = currentUser.getUid();
        DatabaseReference triedRecipeRef = database.getReference("users").child(UID).child("attemptedRecipes");
        DatabaseReference RecipeRef = triedRecipeRef.push();
        String id2 = RecipeRef.getKey();
        newRec.setRecipeID(id2);
        RecipeRef.setValue(newRec);


        Intent startUpload = new Intent(MakeRecipeActivity.this, MakeRecipeActivity.class);    //CAN BE CHANGED LATER TO ACCOUNT FOR
        startActivity(startUpload);
    }
}
