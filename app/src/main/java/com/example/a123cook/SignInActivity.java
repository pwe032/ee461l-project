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
    SignInActivity.java is the main login screen for the application.
    References used to develop:
        1. http://stackoverflow.com/questions/4153517/
           how-exactly-does-the-androidonclick-xml-attribute-differ-from-setonclicklistene

    TODO:
        1. Interface with user profile activity
 */
public class SignInActivity extends AppCompatActivity {

    @BindView(R.id.emailAddressInput) EditText emailAddressInput;
    @BindView(R.id.passwordInput) EditText passwordInput;
    @BindView(R.id.signInButton) Button signInButton;
    @BindView(R.id.signUpLink) TextView signUpLink;

    private FirebaseAuth auth;
    private ProgressDialog progressDialog;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        ButterKnife.bind(this);

        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        if (auth.getCurrentUser() != null){
            onValidSignIn();
        }
    }

    //----------onClick Methods-------------//
    public void signIn() {

        //Check user entry
        if (!isValidEntry()){
            onInvalidSignIn();
            return;
        }

        String emailAddress = emailAddressInput.getText().toString();
        String password = passwordInput.getText().toString();
        progressDialog.setMessage("Signing in...");
        progressDialog.show();

        //Authorize with Firebase data store
        auth.signInWithEmailAndPassword(emailAddress, password).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            onValidSignIn();
                        }
                        else {
                            onInvalidSignIn();
                        }
                    }
                }
        );
    }

    public void signUpRedirect() {

        finish();
        startActivity(new Intent(this, SignUpActivity.class));
    }

    //---------Verification Methods---------//
    public boolean isValidEntry() {

        return isValidEmail() && isValidPassword();
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
    public void onValidSignIn() {

        finish();
        //Start Intent for user profile;
    }

    public void onInvalidSignIn() {

        Toast.makeText(getBaseContext(), "Incorrect email or password.", Toast.LENGTH_LONG).show();
        signInButton.setEnabled(true);
    }

    public void onBackPressed() {

        moveTaskToBack(true);
    }

}
