package com.example.a123cook;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;


public class AccountActivity extends MainActivity{
    private FirebaseUser user;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);
        user = FirebaseAuth.getInstance().getCurrentUser();
        TextView accountName = (TextView)findViewById(R.id.AccountName);
        TextView accountEmail = (TextView)findViewById(R.id.AccountEmail);
        final TextView accountPhotoURL = (TextView)findViewById(R.id.AccountPhotoUrl);
        String name = user.getDisplayName();
        String email = user.getEmail();
        FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("photoUrl").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String source = dataSnapshot.getValue(String.class);
                accountPhotoURL.setText(source);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        accountName.setText(name);
        accountEmail.setText(email);

        Button updateName = (Button)findViewById(R.id.nameButton);
        updateName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    changeName();
            }
        });

        Button updateEmail = (Button)findViewById(R.id.button4);
        updateEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeEmail();
            }
        });

        Button updatephotoURL = (Button)findViewById(R.id.changepicurl);
        updatephotoURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editPhotoURL = (EditText)findViewById(R.id.editText12);
                String newUrl = editPhotoURL.getText().toString();
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                String userID = currentUser.getUid();
                FirebaseDatabase.getInstance().getReference().child("users").child(userID).child("photoUrl").setValue(newUrl);
                editPhotoURL.getText().clear();
            }
        });
    }

    public void changeName(){
        EditText editName = (EditText)findViewById(R.id.editText7);
        String name = editName.getText().toString();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();
        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("AccountActivity", "User profile updated.");
                        }
                    }
                });
        editName.getText().clear();
    }

    public void changeEmail(){
        EditText editEmail = (EditText)findViewById(R.id.editText11);
        String email = editEmail.getText().toString();
        user.updateEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("AccountActivity", "User email address updated.");
                        }
                    }
                });
        editEmail.getText().clear();
    }
}
