package com.example.a123cook;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
import java.util.ArrayList;


public class RecipeSuggestionAdapter extends ArrayAdapter<Recipe> { //just like ProfileArrayAdapter
    private final Context context;
    private final ArrayList<Recipe> values;
    private User user;

    public RecipeSuggestionAdapter(Context context, ArrayList<Recipe> values, User user) {
        super(context, R.layout.activity_recipe_suggestion, values);
        this.context = context;
        this.values = values;
        this.user = user;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //determine how Profile Listview will present its contents
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_recipe_suggestion, parent, false);
        //create View components that make up the recipe post
        TextView smallName = (TextView) rowView.findViewById(R.id.userNameSmall2); // Recipe post has a food name
        final ImageView smallPic = (ImageView) rowView.findViewById(R.id.profilePicSmall2); // Recipe post has food image
        TextView foodName = (TextView) rowView.findViewById(R.id.label2); // Recipe post has a food name
        final ImageView foodPic = (ImageView)rowView.findViewById(R.id.logo2);
        //////////////////////////////////////////////////////////
        String source = values.get(position).imgUrl;
        new RecipeSuggestionAdapter.DownloadImageTasks2(foodPic) .execute(source);

        ImageView rating = (ImageView) rowView.findViewById(R.id.rating2); // Recipe post has rating images of stars
        TextView foodType = (TextView) rowView.findViewById(R.id.foodType2); // food type
        TextView difficulty = (TextView) rowView.findViewById(R.id.difficulty2); // difficulty
        //give View components their appropriate values from a Recipe object
        foodName.setText(values.get(position).name);
        foodType.setText("Food Type: " + values.get(position).foodType);
        difficulty.setText("Difficulty: "+values.get(position).difficulty);
        smallName.setText("Recipe Suggestion");
        FirebaseDatabase.getInstance().getReference().child("users").child(user.getUserID()).child("photoUrl").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String source = dataSnapshot.getValue(String.class);
                new RecipeSuggestionAdapter.DownloadImageTasks2(smallPic) .execute(source);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        // Keep track of each Recipe by its name; Later, obtain image url directly from the Recipe object
        String s = values.get(position).imgUrl;

        //match star image
        if(values.get(position).rating == 5.0){
            rating.setImageResource(R.drawable.five_stars);
        } else if (values.get(position).rating == 4.5){
            rating.setImageResource(R.drawable.fourhalf_stars);
        } else if (values.get(position).rating == 4){
            rating.setImageResource(R.drawable.four_stars);
        } else if (values.get(position).rating == 3.5){
            rating.setImageResource(R.drawable.threehalf_stars);
        }else if (values.get(position).rating == 3){
            rating.setImageResource(R.drawable.three_stars);
        }else if (values.get(position).rating == 2.5){
            rating.setImageResource(R.drawable.twohalf_stars);
        }else if (values.get(position).rating == 2){
            rating.setImageResource(R.drawable.two_stars);
        }else if (values.get(position).rating == 1.5){
            rating.setImageResource(R.drawable.onehalf_stars);
        }else if (values.get(position).rating == 1){
            rating.setImageResource(R.drawable.one_stars);
        }else if (values.get(position).rating == 0.5){
            rating.setImageResource(R.drawable.half_stars);
        }else if (values.get(position).rating == 0){
            rating.setImageResource(R.drawable.no_stars);
        }

        return rowView;
    } //end of getView()

    public class DownloadImageTasks2 extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        public DownloadImageTasks2(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            System.out.println("Url[0] in RecipeSuggestionAdapter: " + urldisplay);
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

} //end of ProfileArrayAdapter