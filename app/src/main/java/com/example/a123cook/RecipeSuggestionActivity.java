package com.example.a123cook;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import java.io.InputStream;

public class RecipeSuggestionActivity extends MainActivity{ // this is like recipeActivity

    public Recipe recipe;
    public String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe2);
        Intent getRecipe = getIntent(); //receive recipe object from ProfileActivity
        recipe = (Recipe)getRecipe.getSerializableExtra("recipeObject");

        makeDisplay(recipe); //show this recipe
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);
    }

    public void makeDisplay(Recipe recipe) {
        //create View components that make up the recipe post
        TextView foodName = (TextView) findViewById(R.id.foodName2); // Recipe post has a food name
        new RecipeSuggestionActivity.DownloadImageTask((ImageView) findViewById(R.id.recPic2)) .execute(recipe.imgUrl );
        ImageView rating = (ImageView) findViewById(R.id.recRating2); // Recipe post has rating images of stars
        TextView foodType = (TextView) findViewById(R.id.recType2); // food type
        TextView ingredients = (TextView) findViewById(R.id.ingredients2);
        TextView instructions = (TextView) findViewById(R.id.instructions2); // instructions
        TextView difficulty = (TextView) findViewById(R.id.skillLevel2); // difficulty
        TextView comments = (TextView) findViewById(R.id.comments2);

        //give View components their appropriate values from a Recipe object
        foodName.setText(recipe.name);
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

    public void startPostActivity2(View view){
        Intent post = new Intent(RecipeSuggestionActivity.this, PostActivity.class);
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
            System.out.println("Url[0]: " + urldisplay);
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