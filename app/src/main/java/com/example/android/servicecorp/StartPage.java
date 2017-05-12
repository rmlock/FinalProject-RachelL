package com.example.android.servicecorp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class StartPage extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String TAG = "StartPage";
    FirebaseDatabase database;
    DatabaseReference users;
    ArrayList<Users> usersArrayList;
    ArrayList<hoursPeriod> hoursPeriodsArrayList;

    String firstName;
    String lastName;
    String name;
    FirebaseUser user;
    boolean allFields = false;
    boolean newAccount;

    Users newUserRegistered;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    Intent intent = new Intent(StartPage.this, MainActivity.class);
                    intent.putExtra("isNew", "no");

                    startActivity(intent);
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
        setContentView(R.layout.content_start_page);
        usersArrayList = new ArrayList<>();


    }

    public void register(View view) {
        EditText confirmPassword = (EditText) findViewById(R.id.ConfirmPassword);
        EditText placement = (EditText) findViewById(R.id.Placement);
        EditText firstName = (EditText) findViewById(R.id.FirstName);
        EditText lastName = (EditText) findViewById(R.id.LastName);
        firstName.setVisibility(View.VISIBLE);
        lastName.setVisibility(View.VISIBLE);
        placement.setVisibility(View.VISIBLE);
        Button login = (Button) findViewById(R.id.loginButton);
        login.setVisibility(View.GONE);
        Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setVisibility(View.GONE);
        confirmPassword.setVisibility(View.VISIBLE);
        Button newUser = (Button) findViewById(R.id.CreateNewUser);
        newUser.setVisibility(View.VISIBLE);


    }

    public void CreateNewUser(View view) {
        EditText confirmPassword = (EditText) findViewById(R.id.ConfirmPassword);
        EditText placementEditText = (EditText) findViewById(R.id.Placement);
        final String placement = placementEditText.getText().toString();
        confirmPassword.setVisibility(View.VISIBLE);
        EditText emailEditText = (EditText) findViewById(R.id.email);
        EditText passwordEditText = (EditText) findViewById(R.id.password);
        final String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmPasswordString = confirmPassword.getText().toString();
        EditText firstNameEditText = (EditText) findViewById(R.id.FirstName);
        EditText LastNameEditText = (EditText) findViewById(R.id.LastName);
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        firstName = firstNameEditText.getText().toString();
        lastName = LastNameEditText.getText().toString();
        name = firstName + " " + lastName;
        editor.putString("name", name);
        editor.apply();

        if (email.isEmpty() || password.isEmpty() || confirmPasswordString.isEmpty() || placement.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
            allFields = false;
        } else {
            allFields = true;
        }

        if (allFields) {

            if (confirmPasswordString.equals(password)) {





                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(StartPage.this, "User Creation failed",
                                            Toast.LENGTH_SHORT).show();

                                } else {
                                    Intent intent = new Intent(StartPage.this, MainActivity.class);
                                    intent.putExtra("isNew", "yes");
                                    intent.putExtra("name",name);
                                    intent.putExtra("email",email);
                                    intent.putExtra("placement",placement);
                                    Toast.makeText(StartPage.this, "User Created!",

                                            Toast.LENGTH_SHORT).show();
                                    newUserRegistered=new Users(name,email,placement);
                                    users= database.getReference();
                                    startActivity(intent);



                                }

                            }
                        });


            } else {
                Toast.makeText(StartPage.this, "Password and confirm password don't match",
                        Toast.LENGTH_SHORT).show();
                return;


            }
        } else {
            Toast.makeText(StartPage.this, "Looks like some fields are blank",
                    Toast.LENGTH_SHORT).show();

        }
    }

    public void Login(View view) {
        EditText emailEditText = (EditText) findViewById(R.id.email);
        EditText passwordEditText = (EditText) findViewById(R.id.password);
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(StartPage.this, "It looks like your email or password is blank",
                    Toast.LENGTH_SHORT).show();

        } else {


            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "signInWithEmail:failed", task.getException());
                                Toast.makeText(StartPage.this, "Login failed- please check that your User Info is correct",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intent = new Intent(StartPage.this, MainActivity.class);
                                intent.putExtra("isNew", "no");

                                startActivity(intent);

                            }

                            // ...
                        }
                    });
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


}

