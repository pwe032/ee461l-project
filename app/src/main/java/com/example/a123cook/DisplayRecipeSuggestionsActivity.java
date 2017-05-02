package com.example.a123cook;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class DisplayRecipeSuggestionsActivity extends ListActivity implements Serializable {

    private ArrayList<Recipe> suggestedRecipes;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Intent getEntry = getIntent();
        suggestedRecipes = (ArrayList<Recipe>)getEntry.getSerializableExtra("suggestedRecipes");     //TODO: get actual recipes from database based on the name

        System.out.println("Checking if suggested recipes are the same: ");
        for(Recipe r: suggestedRecipes){
            System.out.println(r.name);     //TODO: delete this teting line
        }

        final ProfileArrayAdapter adapter = new ProfileArrayAdapter(this, suggestedRecipes);
        setListAdapter(adapter);
    }



    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        //on click, pass current Recipe object to RecipeActivity to show full post
        Recipe recipe = this.suggestedRecipes.get((int)id);
        Intent profile = new Intent(DisplayRecipeSuggestionsActivity.this, RecipeActivity.class);
        profile.putExtra("recipeObject", recipe);
        startActivity(profile);
    }
}