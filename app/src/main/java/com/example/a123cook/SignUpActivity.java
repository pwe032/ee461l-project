package com.example.a123cook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
    SignUpActivity.java provides sig-up functionalities for the user
    References used to develop:

    TODO:
        1. Implement isValidName()
        2. Check if account is already signed up
 */
public class SignUpActivity extends AppCompatActivity {

    @BindView(R.id.nameInput) EditText nameInput;
    @BindView(R.id.emailAddressInput) EditText emailAddressInput;
    @BindView(R.id.passwordInput) EditText passwordInput;
    @BindView(R.id.signUpButton) Button signUpButton;

    private FirebaseAuth auth;
    private ProgressDialog progressDialog;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);


    }

    //----------onClick Methods-------------//

    public void signUp() {

        if (!isValidEntry()) {
            onInvalidSignUp();
            return ;
        }

        String name = nameInput.getText().toString();
        String emailAddress = emailAddressInput.getText().toString();
        String password = passwordInput.getText().toString();
        progressDialog.setMessage("Signing up...");
        progressDialog.show();

        auth.createUserWithEmailAndPassword(emailAddress, password).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            onValidSignUp();
                        }
                        else {
                            onInvalidSignUp();
                        }
                    }
                }
        );
    }

    public void signInRedirect(){

        finish();
        startActivity(new Intent(this, SignInActivity.class));
    }

    //---------Verification Methods---------//
    public boolean isValidEntry() {

        return isValidEmail() && isValidPassword() && isValidName();
    }

    public boolean isValidName(){ //Check name properties


        return false;
    }

    public boolean isValidEmail() { //Check email properties

        boolean isValid = true;
        String emailAddress = emailAddressInput.getText().toString();

        if (emailAddress.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            emailAddressInput.setError("Sorry, we don't recognize that email.");
            isValid = false;
        }

        return isValid;
    }

    public boolean isValidPassword() { //Check password properties

        boolean isValid = true;
        String password = passwordInput.getText().toString();

        if (password.isEmpty()) {
            passwordInput.setError("Invalid password. Please try again.");
        }

        return isValid;
    }

    //--------Event-handling Methods--------//
    public void onValidSignUp() {

        progressDialog.setMessage("Thank you for registering!");
        progressDialog.show();
        finish();
        progressDialog.dismiss();
        startActivity(new Intent(this, MainActivity.class));
    }

    public void onInvalidSignUp(){

        Toast.makeText(getBaseContext(), "Incorrect registration information.", Toast.LENGTH_LONG).show();
        signUpButton.setEnabled(true);
    }

}