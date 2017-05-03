package com.example.a123cook;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.view.View;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import android.content.Context;

public class ProfileActivity extends ListActivity{

    private ArrayList<Recipe> recipes = new ArrayList<Recipe>();
    private Context context;
    private String source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList <Recipe> reversedRecipes;
        reversedRecipes = reverse(recipes);

        Intent getProfileID = getIntent();
        User user = (User) getProfileID.getSerializableExtra("profileUser");
        final ProfileArrayAdapter adapter = new ProfileArrayAdapter(this, recipes, user);
        setListAdapter(adapter);
        String profileID = user.getUserID();
        //read from real-time Firebase database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(profileID).child("attemptedRecipes");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Recipe rec = dataSnapshot.getValue(Recipe.class);
//                adapter.add(rec);
                adapter.insert(rec,0);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Recipe rec = dataSnapshot.getValue(Recipe.class);
                adapter.remove(rec);
                adapter.add(rec);
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Recipe rec = dataSnapshot.getValue(Recipe.class);
                adapter.remove(rec);
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Recipe rec = dataSnapshot.getValue(Recipe.class);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        //on click, pass current Recipe object to RecipeActivity to show full post
        Recipe recipe = this.recipes.get((int)id);
        Intent profile = new Intent(ProfileActivity.this, RecipeActivity.class);
        profile.putExtra("recipeObject", recipe);
        profile.putExtra("check", "ProfileActivity");

//      Intent recResults = new Intent(SearchRecipesActivity.this, RecipeActivity.class);
//      recResults.putExtra("recipeObject2", recipe);
//      profile.putExtra("check", "SearchRecipesActivity" );

        startActivity(profile);
    }

    public ArrayList<Recipe> reverse(ArrayList<Recipe> recipes){
        ArrayList<Recipe> reversedList = new ArrayList<Recipe>();
        int size = recipes.size();
        for (int i=0; i< recipes.size(); i++) {
            reversedList.add(recipes.get(size-1-i));
        }

        return reversedList;
    }

}






