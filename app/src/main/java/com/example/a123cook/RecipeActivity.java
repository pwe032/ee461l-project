package com.example.a123cook;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;

public class RecipeActivity extends MainActivity{ //originally AppCompatActivity

    public Recipe recipe;
    public String previousActivity;
    public String userID;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe);
        Intent getRecipe = getIntent(); //receive recipe object from ProfileActivity
        previousActivity = (String)getRecipe.getSerializableExtra("check");
        userID = (String) getRecipe.getSerializableExtra("userID");

        if (previousActivity.equals("ProfileActivity") || previousActivity.equals("PostActivity")){
            recipe = (Recipe)getRecipe.getSerializableExtra("recipeObject");
        }
        else if(previousActivity.equals("SearchRecipesActivity")){
            recipe = (Recipe)getRecipe.getSerializableExtra("recipeObject2");
        }

        makeDisplay(recipe); //show this recipe
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);
    }

    public void makeDisplay(Recipe recipe) {
        //create View components that make up the recipe post
        TextView foodName = (TextView) findViewById(R.id.foodName); // Recipe post has a food name
        /////////////////////////
        FirebaseDatabase.getInstance().getReference().child("users").child(userID).child("attemptedRecipes").child(recipe.getRecipeID()).child("imgUrl").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String source = dataSnapshot.getValue(String.class);
                System.out.println("Gaurav's image is: " + source);
                new RecipeActivity.DownloadImageTask((ImageView) findViewById(R.id.recPic)) .execute(source);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        ImageView rating = (ImageView) findViewById(R.id.recRating); // Recipe post has rating images of stars
        TextView foodType = (TextView) findViewById(R.id.recType); // food type
        TextView ingredients = (TextView) findViewById(R.id.ingredients);
        TextView instructions = (TextView) findViewById(R.id.instructions); // instructions
        TextView difficulty = (TextView) findViewById(R.id.skillLevel); // difficulty
        TextView comments = (TextView) findViewById(R.id.comments);

        //give View components their appropriate values from a Recipe object
        foodName.setText(recipe.name);
//        int res = getResources().getIdentifier(recipe.imgUrl, "drawable",getPackageName());
        foodType.setText(recipe.foodType);
        ingredients.setText(recipe.ingredients);
        instructions.setText(recipe.instructions);
        difficulty.setText(recipe.difficulty);
        comments.setText(recipe.comments);

        //match star image with rating
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

    public void startPostActivity(View view){
        Intent post = new Intent(RecipeActivity.this, PostActivity.class);
        post.putExtra("recToUpdate",recipe);
        startActivity(post);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}

