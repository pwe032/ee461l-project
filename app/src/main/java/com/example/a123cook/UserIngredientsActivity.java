package com.example.a123cook;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserIngredientsActivity extends MainActivity {
    //    RecipeDatabase rdb;
    TableLayout table;
    private ArrayList<String> userIngredients;// = new ArrayList<String>();;
    EditText ingredientsTypeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_ingredients);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);
//        table = (TableLayout) findViewById(R.id.tlGridTable);
        userIngredients = new ArrayList<String>();
        ingredientsTypeEditText = (EditText) findViewById(R.id.ingredientsTypeEditText);

        userIngredients = new ArrayList<String>();
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().child("users").child(userID).child("userIngredients")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String ingredient = snapshot.getValue(String.class);
                            userIngredients.add(ingredient);                     //don't all duplicates
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

        table = (TableLayout)findViewById(R.id.tlGridTable);
        table.removeAllViews();                                                 //clear the table

        for (int i = 0; i < userIngredients.size(); i++) {           //add back the new table
            TableRow row = new TableRow(this);
            String iNameString = userIngredients.get(i);
            TextView iNameTextView = new TextView(this);
            iNameTextView.setText("" + iNameString);
            row.addView(iNameTextView);
            table.addView(row);
        }
    }


    public ArrayList<String> sort(ArrayList<String> userIngredients){//}, ArrayList<String> userIngredientsNum) {
        String tmp;
        String tmpNum;
        for (int i = 0;i < userIngredients.size();i++)
        {
            tmp = userIngredients.get(i);
            tmpNum = userIngredients.get(i);
            for (int j = 0;j < userIngredients.size();j++)
            {
                if (i == j) continue;
                int x = tmp.compareTo(userIngredients.get(j));
                if (x < 0)
                {
                    tmp = userIngredients.get(j);
                    userIngredients.set(j, userIngredients.get(i));
                    userIngredients.set(i, tmp);
                }
            }
        }
        return userIngredients;
    }


    public void addButtonOnClick(View view){
        String ingredientsTypeText = ingredientsTypeEditText.getText().toString();
        ingredientsTypeText = ingredientsTypeText.trim();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if(!(userIngredients.contains(ingredientsTypeText))){            //only add if ingredient isn't already in there
            //below should switch from temp arraylist to adding to the database
            database.getReference().child("users").child(userID).child("userIngredients").push().setValue(ingredientsTypeText);
        }

        if(!(userIngredients.contains(ingredientsTypeText))){
            userIngredients.add(ingredientsTypeText);
        }

        sort(userIngredients);

        //now update the table view of the user ingredients
        table = (TableLayout)findViewById(R.id.tlGridTable);
        table.removeAllViews();                                                 //clear the table

        for (int i = 0; i < userIngredients.size(); i++) {           //add back the new table
            TableRow row = new TableRow(this);
            String iNameString = userIngredients.get(i);
            TextView iNameTextView = new TextView(this);
            iNameTextView.setText("" + iNameString);
            row.addView(iNameTextView);
            table.addView(row);
        }
        ingredientsTypeEditText.getText().clear();
    }

    public void deleteButtonOnClick(View view){
        final String ingredientsTypeText = ingredientsTypeEditText.getText().toString().trim();
        userIngredients.remove(ingredientsTypeText);            //delete the ingredient to the user ArrayList of ingredients

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final DatabaseReference myRef = database.getReference("users").child(userID).child("userIngredients");
        FirebaseDatabase.getInstance().getReference().child("users").child(userID).child("userIngredients").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ingredient : dataSnapshot.getChildren()) { //for each user objects
                    String currentIngredient = (String) ingredient.getValue();
                    if(currentIngredient.equals(ingredientsTypeText)){
                        String recipeIngredientID = ingredient.getKey();
                        myRef.child(recipeIngredientID).setValue(null);     //remove from database
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Unable to retrieve the users.
            }
        });

        sort(userIngredients);

        //now update the table view of the user ingredients
        table = (TableLayout)findViewById(R.id.tlGridTable);
        table.removeAllViews();                                                 //clear the table

        for (int i = 0; i < userIngredients.size(); i++) {           //add back the new table
            TableRow row = new TableRow(this);
            String iNameString = userIngredients.get(i);
            TextView iNameTextView = new TextView(this);
            iNameTextView.setText("" + iNameString);
            row.addView(iNameTextView);
            table.addView(row);
        }

        ingredientsTypeEditText.getText().clear();
    }

    public ArrayList<String> getUserIngredients(){
        return userIngredients;
    }

}