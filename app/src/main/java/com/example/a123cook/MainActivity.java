package com.example.a123cook;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("123cook");
    }
    public void startProfileIntro(View view){
        Intent profile = new Intent(MainActivity.this, ProfileIntroActivity.class);
        startActivity(profile);
    }
}
