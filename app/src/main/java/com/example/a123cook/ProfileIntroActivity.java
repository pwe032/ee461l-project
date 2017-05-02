package com.example.a123cook;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class ProfileIntroActivity extends MainActivity {
    private String profileID = "";
    private Bitmap profileBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileintro);
        Intent getID = getIntent(); //receive selected userID from SearchResultActivity
        profileID = (String) getID.getSerializableExtra("userID");
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);

        //if activity knows the UID of the person to be uploaded
    }

    public void startProfileActivity(View view) {
        Intent profile = new Intent(ProfileIntroActivity.this, ProfileActivity.class);
        profile.putExtra("profileID", profileID);
        startActivity(profile);
    }


}