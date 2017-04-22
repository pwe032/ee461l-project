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



import butterknife.BindView;
import butterknife.ButterKnife;


/*
    SignInActivity.java is the main login screen for the application.
    References used to develop:
        1. http://stackoverflow.com/questions/4153517/
           how-exactly-does-the-androidonclick-xml-attribute-differ-from-setonclicklistene

    TODO:
        1. Interface with user profile activity
        2. Add "forgot password" activity

 */
public class SignInActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener {


    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;

    @BindView(R.id.emailAddressInput) EditText emailAddressInput;
    @BindView(R.id.passwordInput) EditText passwordInput;
    @BindView(R.id.signInButton) Button signInButton;
    @BindView(R.id.signUpLink) TextView signUpLink;

    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        ButterKnife.bind(this);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        if (auth.getCurrentUser() != null){
            onValidSignIn();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            progressDialog.show();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    progressDialog.dismiss();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }


    //----------onClick Methods-------------//
//    public void signIn() {
//
//        //Check user entry
//        if (!isValidEntry()){
//            onInvalidSignIn();
//            return;
//        }
//
//        String emailAddress = emailAddressInput.getText().toString();
//        String password = passwordInput.getText().toString();
//        progressDialog.setMessage("Signing in...");
//        progressDialog.show();
//
//        //Authorize with Firebase data store
//        auth.signInWithEmailAndPassword(emailAddress, password).addOnCompleteListener(this,
//                new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        progressDialog.dismiss();
//                        if (task.isSuccessful()) {
//                            onValidSignIn();
//                        }
//                        else {
//                            onInvalidSignIn();
//                        }
//                    }
//                }
//        );
//    }

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

    //--------Sign-in helper methods--------//

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);
        }
    }

    private void updateUI(boolean signedIn) {
        progressDialog.dismiss();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    public void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

}
