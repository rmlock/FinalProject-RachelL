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

    Users newUser;
    hoursPeriod week1;
    hoursPeriod week2;
    hoursPeriod week3;
    hoursPeriod week4;
    hoursPeriod week5;
    hoursPeriod week6;
    hoursPeriod week7;
    hoursPeriod week8;
    hoursPeriod week9;
    hoursPeriod week10;
    hoursPeriod week11;
    hoursPeriod week12;
    hoursPeriod week13;
    hoursPeriod week14;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        users = database.getReference("users").child("hours");
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    Intent intent = new Intent(StartPage.this, MainActivity.class);
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
        hoursPeriodsArrayList = new ArrayList<>();


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
        String placement = placementEditText.getText().toString();
        confirmPassword.setVisibility(View.VISIBLE);
        EditText emailEditText = (EditText) findViewById(R.id.email);
        EditText passwordEditText = (EditText) findViewById(R.id.password);
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmPasswordString = confirmPassword.getText().toString();
        EditText firstNameEditText = (EditText) findViewById(R.id.FirstName);
        EditText LastNameEditText = (EditText) findViewById(R.id.LastName);
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        firstName = firstNameEditText.getText().toString();
        lastName = LastNameEditText.getText().toString();
        name = lastName + " " + firstName;
        editor.putString("name", name);
        editor.commit();

        if (email.isEmpty() || password.isEmpty() || confirmPasswordString.isEmpty() || placement.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
            allFields = false;
        }else {
            allFields=true;
        }

        if (allFields) {

            if (confirmPasswordString.equals(password)) {
                newUser = new Users(name, email, placement);
                week1 = new hoursPeriod(0, 0, 0, 0, 0, 0, 0, "one");
                week2 = new hoursPeriod(0, 0, 0, 0, 0, 0, 0, "two");
                week3 = new hoursPeriod(0, 0, 0, 0, 0, 0, 0, "three");
                week4 = new hoursPeriod(0, 0, 0, 0, 0, 0, 0, "four");
                week5 = new hoursPeriod(0, 0, 0, 0, 0, 0, 0, "five");
                week6 = new hoursPeriod(0, 0, 0, 0, 0, 0, 0, "six");
                week7 = new hoursPeriod(0, 0, 0, 0, 0, 0, 0, "seven");
                hoursPeriodsArrayList.add(week1);
                hoursPeriodsArrayList.add(week2);
                hoursPeriodsArrayList.add(week3);
                hoursPeriodsArrayList.add(week4);
                hoursPeriodsArrayList.add(week5);
                hoursPeriodsArrayList.add(week6);
                hoursPeriodsArrayList.add(week7);
                hoursPeriodsArrayList.add(week8);


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
                                    Toast.makeText(StartPage.this, "User Created!",

                                            Toast.LENGTH_SHORT).show();
                                    users.push().setValue(newUser);
                                    users.child("week1").push().setValue(week1);
                                    users.child("week2").push().setValue(week2);
                                    users.child("week3").push().setValue(week3);
                                    users.child("week4").push().setValue(week4);
                                    users.child("week5").push().setValue(week5);
                                    users.child("week6").push().setValue(week6);
                                    users.child("week7").push().setValue(week7);

                                    Intent intent = new Intent(StartPage.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();


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

