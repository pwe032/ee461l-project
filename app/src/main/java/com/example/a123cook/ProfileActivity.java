package com.example.a123cook;

import com.example.a123cook.ProfileArrayAdapter;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import android.view.View;

import java.util.ArrayList;
import com.example.a123cook.Recipe;

public class ProfileActivity extends ListActivity {
//This activity(separate screen) displays each user's profile feed
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ProfileArrayAdapter(this, RecipeDatabase.getUniqueInstance().getAllRecipes()));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Recipe recipe = RecipeDatabase.getAllRecipes().get((int)id);
        Intent profile = new Intent(ProfileActivity.this, RecipeActivity.class);
        profile.putExtra("recipeObject", recipe);
        startActivity(profile);
    }
}





