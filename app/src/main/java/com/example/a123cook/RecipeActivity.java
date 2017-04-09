package com.example.a123cook;

import com.example.a123cook.ProfileArrayAdapter;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import java.util.ArrayList;
import com.example.a123cook.Recipe;

public class RecipeActivity extends AppCompatActivity{

    public RecipeDatabase center = RecipeDatabase.getUniqueInstance();
    public RecipeActivity(){
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        Intent getRecipe = getIntent();
        Recipe recipe = (Recipe)getRecipe.getSerializableExtra("recipeObject");
        makeDisplay(recipe);
    }

    public void makeDisplay(Recipe recipe) {
        //determine how Profile Listview will present its contents
        //create View components that make up the recipe post
        TextView foodName = (TextView) findViewById(R.id.foodName); // Recipe post has a food name
        ImageView foodPic = (ImageView) findViewById(R.id.recPic); // Recipe post has food image
        ImageView rating = (ImageView) findViewById(R.id.recRating); // Recipe post has rating images of stars
        TextView foodType = (TextView) findViewById(R.id.recType); // food type
        TextView ingredients = (TextView) findViewById(R.id.ingredients);
        TextView instructions = (TextView) findViewById(R.id.instructions); // instructions
        TextView difficulty = (TextView) findViewById(R.id.skillLevel); // difficulty
        //give View components their appropriate values from a Recipe object

        foodName.setText(recipe.name);
        int res = getResources().getIdentifier(recipe.imgUrl, "drawable",getPackageName());
        foodPic.setImageResource(res);
        foodType.setText(recipe.foodType);
        ingredients.setText(recipe.ingredients);
        instructions.setText(recipe.instructions);
        difficulty.setText(recipe.difficulty);

        //match star image
        if(recipe.rating == 5.0){
            rating.setImageResource(R.drawable.five_stars);
        } else if (recipe.rating == 4.5){
            rating.setImageResource(R.drawable.fourhalf_stars);
        } else if (recipe.rating == 4){
            rating.setImageResource(R.drawable.four_stars);
        } else if (recipe.rating == 3.5){
            rating.setImageResource(R.drawable.threehalf_stars);
        }else if (recipe.rating == 3){
            rating.setImageResource(R.drawable.three_stars);
        }else if (recipe.rating == 2.5){
            rating.setImageResource(R.drawable.twohalf_stars);
        }else if (recipe.rating == 2){
            rating.setImageResource(R.drawable.two_stars);
        }else if (recipe.rating == 1.5){
            rating.setImageResource(R.drawable.onehalf_stars);
        }else if (recipe.rating == 1){
            rating.setImageResource(R.drawable.one_stars);
        }else if (recipe.rating == 0.5){
            rating.setImageResource(R.drawable.half_stars);
        }else if (recipe.rating == 0){
            rating.setImageResource(R.drawable.no_stars);
        }

    }




}