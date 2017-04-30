package com.example.a123cook;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileIntroActivity extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileintro);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);

        //Get user from Firebase Auth or from Search Activity
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Uri photoUrl = currentUser.getPhotoUrl();
        ImageView profilePic = (ImageView) findViewById(R.id.profilePic);
        profilePic.setImageURI(photoUrl);
    }
    public void startProfileActivity(View view){
        Intent profile = new Intent(ProfileIntroActivity.this, ProfileActivity.class);
        startActivity(profile);
    }
}

