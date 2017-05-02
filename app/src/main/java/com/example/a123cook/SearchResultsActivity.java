package com.example.a123cook;


import android.app.ListActivity;
import android.content.Intent;
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
import java.util.ArrayList;
import java.util.Iterator;

public class SearchResultsActivity extends ListActivity implements Serializable {

    private String[] parsedEntry;
    private String Name;
    private String[] matches = {"adsf", "asdf"};
    private String[] tempName;
    private ArrayList<User> users;
    private ArrayList<String> nameMatches;

    protected void onCreate(Bundle savedInstanceState) {

        String tempName;

        super.onCreate(savedInstanceState);
        Intent getEntry = getIntent();
        Name = (String)getEntry.getSerializableExtra("Name");
        parsedEntry = Name.split(" ");

        //read from real-time Firebase database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        DataSnapshot dataSnapshot;


        getAllUsersFromFirebase();

        nameMatches.clear();

        for (User u: users){
            this.tempName = u.getName().split(" ");

            if ((u.getName()).equals(Name)){         //IF THE NAMES MATCH EXACTLY
                nameMatches.add(0,u.getName());
            }

            else if( (this.tempName[0]).equals(parsedEntry[0]) ){    //IF ONLY THE FIRST NAME MATCHES
                nameMatches.add(u.getName());
            }


        }

        matches = new String[nameMatches.size()];
        for (int i=0; i < nameMatches.size(); i++) {
            matches[i] = nameMatches.get(i);
        }

        setListAdapter(new ArrayAdapter<String>(this, R.layout.activity_searchresults,matches));
        ListView listView = getListView();
        listView.setTextFilterEnabled(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Toast.makeText(getApplicationContext(),
                        ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void getAllUsersFromFirebase() {
        FirebaseDatabase.getInstance()
                .getReference()
                .child("users")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> dataSnapshots = dataSnapshot.getChildren()
                                .iterator();
                        users = new ArrayList<>();
                        while (dataSnapshots.hasNext()) {
                            DataSnapshot dataSnapshotChild = dataSnapshots.next();
                            User user = dataSnapshotChild.getValue(User.class);
                            if (!TextUtils.equals(user.getUserID(),
                                    FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                                users.add(user);
                            }
                        }
                        // All users are retrieved except the one who is currently logged
                        // in device.
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Unable to retrieve the users.
                    }
                });
    }
}