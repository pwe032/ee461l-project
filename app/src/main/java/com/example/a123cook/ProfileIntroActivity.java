package com.example.a123cook;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class ProfileIntroActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileintro);
        ImageView profilePic = (ImageView) findViewById(R.id.profilePic);
        profilePic.setImageResource(R.drawable.example_profile);
    }
    public void startProfileActivity(View view){
        Intent profile = new Intent(ProfileIntroActivity.this, ProfileActivity.class);
        startActivity(profile);
    }
}

