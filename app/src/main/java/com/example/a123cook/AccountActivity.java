package com.example.a123cook;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import org.w3c.dom.Text;


public class AccountActivity extends MainActivity{
    private FirebaseUser user;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        user = FirebaseAuth.getInstance().getCurrentUser();
        TextView accountName = (TextView)findViewById(R.id.AccountName);
        TextView accountEmail = (TextView)findViewById(R.id.AccountEmail);
        TextView accountPhotoURL = (TextView)findViewById(R.id.AccountPhotoUrl);
        String name = user.getDisplayName();
        String email = user.getEmail();
        String photoURL = "no image";
        accountName.setText(name);
        accountEmail.setText(email);
        accountPhotoURL.setText(photoURL);

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
    }

}
