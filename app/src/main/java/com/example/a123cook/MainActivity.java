package com.example.a123cook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText emailAddressInput;
    private EditText passwordInput;
    private Button signInButton;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailAddressInput = (EditText) findViewById(R.id.emailAddressInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);
        signInButton = (Button) findViewById(R.id.signInButton);
        signUpButton = (Button) findViewById(R.id.signUpButton);

        signInButton.setOnClickListener(this);
        signUpButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view){

        if (view == signUpButton){
            //signUpUser();
            //signInUser();
        }
        else if (view == signInButton){
            //signInUser();
        }
    }
}
