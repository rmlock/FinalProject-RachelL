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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    FirebaseDatabase database;
    DatabaseReference postRef;
    DatabaseReference users;
    DatabaseReference userInfo;
    String uid;
    String value;
    String name;
    String userString;

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
                    uid = user.getUid();
                    database = FirebaseDatabase.getInstance();
                    users = database.getReference("users").child(uid).child("userInfo");


                    // Read from the database
                    users.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.
                            name= dataSnapshot.child("name").getValue(String.class);
                            userString= "Name: "+ dataSnapshot.child("name").getValue(String.class) +'\n'+
                                    "Email: "+ dataSnapshot.child("email").getValue(String.class) +'\n'+
                                    "Placement: "+ dataSnapshot.child("placement").getValue(String.class);


                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                            Log.w(TAG, "Failed to read value.", error.toException());
                        }
                    });



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
        Intent email = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + contactEmail));
        email.putExtra(Intent.EXTRA_SUBJECT, subject);
        email.putExtra(Intent.EXTRA_TEXT, "Dear " + personName + "," + '\n'+  name+
                 " is having " + subject + '\n' +userString);
        if (email.resolveActivity(getPackageManager()) != null) {
            startActivity(email);

        }
    }


}
