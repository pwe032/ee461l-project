package com.example.a123cook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.net.URL;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ProfileArrayAdapter extends ArrayAdapter<Recipe>{
    private final Context context;
    private final Recipe[] values;

    public ProfileArrayAdapter(Context context, Recipe[] values) {
        super(context, R.layout.activity_profile, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //determine how Profile Listview will present its contents
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_profile, parent, false);
        //create View components that make up the recipe post
        TextView foodName = (TextView) rowView.findViewById(R.id.label); // Recipe post has a food name
        ImageView imageView = (ImageView) rowView.findViewById(R.id.logo); // Recipe post has food image
        ImageView rating = (ImageView) rowView.findViewById(R.id.rating); // Recipe post has rating images of stars
        TextView foodType = (TextView) rowView.findViewById(R.id.foodType); // food type
        TextView instructions = (TextView) rowView.findViewById(R.id.instruction); // instructions
        TextView difficulty = (TextView) rowView.findViewById(R.id.difficulty); // difficulty
        //give View components their appropriate values from a Recipe object
        foodName.setText(values[position].name);
        foodType.setText(values[position].foodType);
        instructions.setText(values[position].instructions);
        difficulty.setText(values[position].difficulty);

        // Keep track of each Recipe by its name; Later, obtain image url directly from the Recipe object
        String s = values[position].name;

        //match food image
        if (s.equals("mkp")) {
            imageView.setImageResource(R.drawable.mkp_logo);
        } else if (s.equals("pb")) {
            imageView.setImageResource(R.drawable.pb_logo);
        } else if (s.equals("mt")) {
            imageView.setImageResource(R.drawable.mt_logo);
        } else {
            imageView.setImageResource(R.drawable.gn_logo);
        }

        //match star image
        if(values[position].rating == 5.0){
            rating.setImageResource(R.drawable.five_stars);
        } else if (values[position].rating == 4.5){
            rating.setImageResource(R.drawable.fourhalf_stars);
        } else if (values[position].rating == 4){
            rating.setImageResource(R.drawable.four_stars);
        } else if (values[position].rating == 3.5){
            rating.setImageResource(R.drawable.threehalf_stars);
        }else if (values[position].rating == 3){
            rating.setImageResource(R.drawable.three_stars);
        }else if (values[position].rating == 2.5){
            rating.setImageResource(R.drawable.twohalf_stars);
        }else if (values[position].rating == 2){
            rating.setImageResource(R.drawable.two_stars);
        }else if (values[position].rating == 1.5){
            rating.setImageResource(R.drawable.onehalf_stars);
        }else if (values[position].rating == 1){
            rating.setImageResource(R.drawable.one_stars);
        }else if (values[position].rating == 0.5){
            rating.setImageResource(R.drawable.half_stars);
        }else if (values[position].rating == 0){
            rating.setImageResource(R.drawable.no_stars);
        }

        return rowView;
    } //end of getView()

} //end of ProfileArrayAdapter