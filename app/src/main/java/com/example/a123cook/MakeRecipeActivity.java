package com.example.a123cook;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class MakeRecipeActivity extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);


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

        Recipe newRec = new Recipe(recName,0,"the IMAGE",foodType,difficulty,instructions,ingredients);

        Recipe.addRecipeToDB(newRec);



        Intent startUpload = new Intent(MakeRecipeActivity.this, ProfileActivity.class);    //CAN BE CHANGED LATER TO ACCOUNT FOR
        startActivity(startUpload);
    }
}
