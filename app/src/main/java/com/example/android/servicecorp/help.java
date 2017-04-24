package com.example.android.servicecorp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class help extends AppCompatActivity {
    String subject;
    String contactEmail;
    String personName;
    int quantity213 = 0;
    int quantity214 = 0;
    int totalHours = quantity213 + quantity214;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    String TAG = "Main";
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    Intent intent = new Intent(help.this, StartPage.class);
                    startActivity(intent);
                    finish();
                }
                // ...
            }
        };
    }


    public void helpClick(View v) {
        switch (v.getId()) {
            case R.id.PlacementSupport:
                subject = "Issue with placement";
                contactEmail = "Cecilia.Britez@qc.cuny.edu";
                personName = "Ms. Britez";
                break;
            case R.id.TechSupport:
                subject = "Issue with the App";
                contactEmail = "Rachel.Stern@qc.cuny.edu";
                personName = "Ms. Stern";
                break;
            case R.id.RFCunySupport:
                subject = "Issue with the RF System";
                contactEmail = "Rachel.Stern@qc.cuny.edu";
                personName = "Ms. Stern";
                break;

        }
        email();
    }

    public void email() {
        Intent email = new Intent(Intent.ACTION_SENDTO);
        email.setData(Uri.parse("mailto:"));
        email.putExtra(Intent.EXTRA_EMAIL, contactEmail);
        email.putExtra(Intent.EXTRA_SUBJECT, subject);
        email.putExtra(Intent.EXTRA_TEXT, "Dear " + personName + "," + " has been having an " + subject + ". The student can type more info below." + '\n' +
                "Name:");


        if (email.resolveActivity(getPackageManager()) != null) {
            startActivity(email);
        }

    }
}
