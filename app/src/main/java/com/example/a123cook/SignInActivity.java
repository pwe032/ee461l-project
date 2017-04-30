package com.example.a123cook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;


/*
    SignInActivity.java is the main login screen for the application.
    References used to develop:
        1. http://stackoverflow.com/questions/4153517/
           how-exactly-does-the-androidonclick-xml-attribute-differ-from-setonclicklistene

    TODO:
        1. Interface with user profile activity
        2. Add "sign-out" page

 */
public class SignInActivity extends AppCompatActivity {

    private EditText emailAddressInput, passwordInput;
    private Button signInButton;
    private TextView signUpLink;

    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        emailAddressInput = (EditText) findViewById(R.id.emailAddressInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);
        signInButton = (Button) findViewById(R.id.signInButton);
        signUpLink = (TextView) findViewById(R.id.signUpLink);

        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

//        if (auth.getCurrentUser() != null){
//            onValidSignIn();
//        }
    }


    //----------onClick Methods-------------//
    public void signIn(View view) {

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
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignInActivity.this, "Incorrect email or password.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {
                            onValidSignIn();
                        }
                    }
                }
        );
    }

    public void signUpRedirect(View view) {
        finish();
        startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
    }

    //---------Verification Methods---------//
    public boolean isValidEntry() {
        return isValidEmail() && isValidPassword();
    }

    public boolean isValidEmail() { //Check email properties
        String emailAddress = emailAddressInput.getText().toString();
        boolean isValid = !emailAddress.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches();

        return isValid;
    }

    public boolean isValidPassword() { //Check password properties
        String password = passwordInput.getText().toString();
        boolean isValid = !password.isEmpty();

        return isValid;
    }

    //--------Event-handling Methods--------//
    public void onValidSignIn() {
        finish();
        //TODO: Start Intent for user profile;
        startActivity(new Intent(SignInActivity.this, ProfileActivity.class));
    }

    public void onInvalidSignIn() {
        Toast.makeText(getBaseContext(), "Incorrect email or password.", Toast.LENGTH_LONG).show();
        signInButton.setEnabled(true);
    }

    public void onBackPressed() {
        moveTaskToBack(true);
    }

}
