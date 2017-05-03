package com.example.a123cook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.SignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

/*
    SignUpActivity.java provides sig-up functionalities for the user
    References used to develop:

    TODO:
        1. Implement isValidName()
        2. Check if account is already signed up
 */
public class SignUpActivity extends AppCompatActivity {

    //user input fields
    private EditText nameInput, emailAddressInput, passwordInput;
    private Button signUpButton;
    private TextView signInLink;

    //firebase authentication fields
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        nameInput = (EditText) findViewById(R.id.nameInput);
        emailAddressInput = (EditText) findViewById(R.id.emailAddressInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);
        signUpButton = (Button) findViewById(R.id.signUpButton);
        signInLink = (TextView) findViewById(R.id.signInLink);

        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

    }

    //----------onClick Methods-------------//

    public void signUp(View view) {
        if (!isValidEntry()) {
            onInvalidSignUp();
            return ;
        }

        String emailAddress = emailAddressInput.getText().toString();
        String password = passwordInput.getText().toString();
        final String name = nameInput.getText().toString();
        progressDialog.setMessage("Signing up...");
        progressDialog.show();

        auth.createUserWithEmailAndPassword(emailAddress, password).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressDialog.dismiss();
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "Sign up failed! Try again.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            //Construct and add user object to realtime database
                            FirebaseAuth.getInstance().getCurrentUser().updateProfile(
                                    new UserProfileChangeRequest.Builder()
                                            .setDisplayName(name).build());
                            User newUser = new User(FirebaseAuth.getInstance().getCurrentUser(), name);
                            System.out.println("LOG: new user has signed up with name: " + newUser.getAttemptedRecipes());
//                            newUser.addAttemptedRecipe(new Recipe("gn", 1.0, "https://static.pexels.com/photos/5317/food-salad-restaurant-person.jpg", "asian", "5", "instr", "ingredients"));
                            onValidSignUp();
                        }
                    }
                }
        );
    }

    public void signInRedirect(View view) {
        finish();
        startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
    }

    //---------Verification Methods---------//
    public boolean isValidEntry() {

        return isValidEmail() && isValidPassword() && isValidName();
    }

    public boolean isValidName(){ //Check name properties
        boolean isValid = true;
        String name = nameInput.getText().toString();

        if (name.isEmpty()) {
            nameInput.setError("Invalid name. Try again.");
            isValid = false;
        }

        return isValid;
    }

    public boolean isValidEmail() { //Check email properties
        boolean isValid = true;
        String emailAddress = emailAddressInput.getText().toString();

        if (emailAddress.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            emailAddressInput.setError("Invalid email. Try again.");
            isValid = false;
        }

        return isValid;
    }

    public boolean isValidPassword() { //Check password properties
        boolean isValid = true;
        String password = passwordInput.getText().toString();

        if (password.isEmpty()) {
            passwordInput.setError("Invalid password. Try again.");
            isValid = false;
        }

        return isValid;
    }

    //--------Event-handling Methods--------//
    public void onValidSignUp() {
        finish();
        startActivity(new Intent(this, SignInActivity.class));
    }

    public void onInvalidSignUp(){
        signUpButton.setEnabled(true);
    }

}
