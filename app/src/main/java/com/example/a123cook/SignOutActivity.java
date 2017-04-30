package com.example.a123cook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.SignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Minkoo on 2017-04-30.
 */

public class SignOutActivity extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //sign out the current user
        FirebaseAuth.getInstance().signOut();
        Intent signedOut = new Intent(this, SignInActivity.class);
        startActivity(signedOut);
    }

}
