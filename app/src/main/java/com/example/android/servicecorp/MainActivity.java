package com.example.android.servicecorp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    int quantity213 = 0;
    int quantity214 = 0;
    int totalHours = quantity213 + quantity214;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    String TAG = "Main";
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference postRef;
    DatabaseReference users;
    DatabaseReference userInfo;
    DatabaseReference hours;
    DatabaseReference week1hours;
    DatabaseReference week2hours;
    ArrayList<hoursPeriod> hoursPeriodsArrayList;
    Users currentUser;
    String uid;
    String newAccount;
    String userName;
    hoursPeriod week1;
    hoursPeriod week2;
    hoursPeriod week3;
    hoursPeriod week4;
    hoursPeriod week5;
    hoursPeriod week6;
    hoursPeriod week7;
    int week1HoursInProgress;
    int week2HoursInProgress;

    int hoursInProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = FirebaseDatabase.getInstance();
        hoursPeriodsArrayList = new ArrayList<>();


        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    uid = user.getUid();
//
                    Intent i = getIntent();
                    newAccount = i.getStringExtra("isNew");
                    users = database.getReference("users");

                    if (newAccount != null) {
                        if (newAccount.equals("yes")) {

                            String name = i.getStringExtra("name");
                            String placement = i.getStringExtra("placement");
                            String email = i.getStringExtra("email");
                            currentUser = new Users(name, email, placement);


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

                            users.child(uid).child("hours").child("week1").setValue(week1);
                            users.child(uid).child("hours").child("week2").setValue(week2);
                            users.child(uid).child("hours").child("week3").setValue(week3);
                            users.child(uid).child("hours").child("week4").setValue(week4);
                            users.child(uid).child("hours").child("week5").setValue(week5);
                            users.child(uid).child("hours").child("week6").setValue(week6);
                            users.child(uid).child("hours").child("week7").setValue(week7);
                            users.child(uid).child("userInfo").setValue(currentUser);


                        }
                    }

//
//
                    week1hours = database.getReference("users").child(uid).child("hours").child("week1");
                    week2hours = database.getReference("users").child(uid).child("hours").child("week2");
                    userInfo = database.getReference("users").child(uid).child("userInfo");
                    userInfo.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.
//                            String value = dataSnapshot.getValue(String.class);

                            TextView title = (TextView) findViewById(R.id.title);
                            userName = dataSnapshot.child("name").getValue(String.class);
                            String greeting= R.string.MainGreeting+" "+ userName;


                            title.setText(userName);


                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                            Log.w(TAG, "Failed to read value.", error.toException());
                        }
                    });
//
                    week1hours.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.
//                            String value = dataSnapshot.getValue(String.class);
                            int day1Hoursc = dataSnapshot.child("day1Hours").getValue(int.class);
                            int day2Hoursc = dataSnapshot.child("day2Hours").getValue(int.class);
                            int day3Hoursc = dataSnapshot.child("day3Hours").getValue(int.class);
                            int day4Hoursc = dataSnapshot.child("day4Hours").getValue(int.class);
                            int day5Hoursc = dataSnapshot.child("day5Hours").getValue(int.class);
                            int day6Hoursc = dataSnapshot.child("day6Hours").getValue(int.class);
                            int day7Hoursc = dataSnapshot.child("day7Hours").getValue(int.class);
                            String periodc = dataSnapshot.child("period").getValue(String.class);

                            TextView textView = (TextView) findViewById(R.id.totalhoursshowing);
                            TextView week1 = (TextView) findViewById(R.id.week1Hours);
                            week1HoursInProgress = (day1Hoursc + day2Hoursc + day3Hoursc + day4Hoursc + day5Hoursc + day6Hoursc + day7Hoursc);
                            textView.setText("" + week1HoursInProgress);
                            week1.setText("" + week1HoursInProgress);

                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                            Log.w(TAG, "Failed to read value.", error.toException());
                        }
                    });
                    week2hours.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.
//                            String value = dataSnapshot.getValue(String.class);
                            int day1Hoursc = dataSnapshot.child("day1Hours").getValue(int.class);
                            int day2Hoursc = dataSnapshot.child("day2Hours").getValue(int.class);
                            int day3Hoursc = dataSnapshot.child("day3Hours").getValue(int.class);
                            int day4Hoursc = dataSnapshot.child("day4Hours").getValue(int.class);
                            int day5Hoursc = dataSnapshot.child("day5Hours").getValue(int.class);
                            int day6Hoursc = dataSnapshot.child("day6Hours").getValue(int.class);
                            int day7Hoursc = dataSnapshot.child("day7Hours").getValue(int.class);
                            String periodc = dataSnapshot.child("period").getValue(String.class);

                            TextView textView = (TextView) findViewById(R.id.totalhoursshowing);
                            TextView week2 = (TextView) findViewById(R.id.week2Hours);
                            week2HoursInProgress = (day1Hoursc + day2Hoursc + day3Hoursc + day4Hoursc + day5Hoursc + day6Hoursc + day7Hoursc);
                            week2.setText("" + week2HoursInProgress);

                            hoursInProgress=week1HoursInProgress+week2HoursInProgress;
                            textView.setText("" + hoursInProgress);



                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                            Log.w(TAG, "Failed to read value.", error.toException());
                        }
                    });

//
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
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

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.help:
                Intent intent = new Intent(MainActivity.this, help.class);
                startActivity(intent);
                return true;
            case R.id.logOut:
                mAuth.signOut();
                Intent logout = new Intent(MainActivity.this, StartPage.class);
                startActivity(logout);
                return true;
            case R.id.nav_camera:
                Intent camera = new Intent(MainActivity.this, Camera.class);
                startActivity(camera);


            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void launchWeek1(View view) {
        Intent week1 = new Intent(MainActivity.this, WeekOne.class);
        startActivity(week1);
    }

    public void launchWeek2(View view) {
        Intent week2 = new Intent(MainActivity.this, WeekTwo.class);
        startActivity(week2);

    }
}

