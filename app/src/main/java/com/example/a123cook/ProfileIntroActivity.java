package com.example.a123cook;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class ProfileIntroActivity extends MainActivity {
    private String profileID = "";
    private String profileEmail = "";
    private String profileName = "";
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileintro);
        Intent getID = getIntent(); //receive selected userID from SearchResultActivity
        user  = (User) getID.getSerializableExtra("SelectedUser");
        profileID = user.getUserID();
        profileEmail = user.getEmail();
        profileName = user.getName();
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);

        //set image with user choosen url
        FirebaseDatabase.getInstance().getReference().child("users").child(profileID).child("photoUrl").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String source = dataSnapshot.getValue(String.class);
                new DownloadImageTask((ImageView) findViewById(R.id.profilePic)) .execute(source);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        //set email with user info
        TextView userEmail = (TextView)findViewById(R.id.textEmail);
        userEmail.setText(profileEmail);
        TextView userName = (TextView)findViewById(R.id.textName);
        userName.setText(profileName);
    }

    public void startProfileActivity(View view) {
        Intent profile = new Intent(ProfileIntroActivity.this, ProfileActivity.class);
        profile.putExtra("profileUser", user);
        startActivity(profile);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}