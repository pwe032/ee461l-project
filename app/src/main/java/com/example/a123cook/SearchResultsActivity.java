package com.example.a123cook;


import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class SearchResultsActivity extends ListActivity{
    private String userID;
    private String userName;
    private String[] nameTokens;
    private ArrayList<String> matchingNames = new ArrayList<String>();
    private HashMap<String, String> identifier = new HashMap<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent getEntry = getIntent();
        String name = (String) getEntry.getSerializableExtra("Name");
        nameTokens = name.split(" ");
        final SearchResultArrayAdapter adapter = new SearchResultArrayAdapter(this, matchingNames);
        setListAdapter(adapter);

        FirebaseDatabase.getInstance().getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot user : dataSnapshot.getChildren()) { //for each user objects
                    String name = (String) user.child("name").getValue();
                    String ID = (String) user.child("userID").getValue();
                    String[] username = name.split(" "); //name searched
                    String firstname = username[0].toLowerCase(); //name to compare
                    for (int i = 0; i < nameTokens.length; i++) {
                        if (firstname.equals(nameTokens[i].toLowerCase())) {
                            adapter.add(name);
                            identifier.put(name,ID);
                        }
                    }
                }
                if(adapter.getCount()==0)
                    adapter.add("No Result Found");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Unable to retrieve the users.
            }
        });
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        //on click, get current name, get it's ID and pass that to ProfileAcivity
        if(matchingNames.get(0).equals("No Result Found")){
            Toast.makeText(SearchResultsActivity.this, "Search Again!",
                    Toast.LENGTH_SHORT).show();
        }
        String clickedName = this.matchingNames.get((int)id);
        String userID = identifier.get(clickedName);
        identifier.clear();
        Intent profile = new Intent(this, ProfileIntroActivity.class);
        profile.putExtra("userID", userID);
        startActivity(profile);
    }
}