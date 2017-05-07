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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class WeekOne extends AppCompatActivity {
    int quantity213 = 0;
    int quantity214 = 0;
    int totalHours = quantity213 + quantity214;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    String TAG = "Main";
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference users;
    DatabaseReference hours;
    DatabaseReference week1hours;
    DatabaseReference userInfo;
    ArrayList<hoursPeriod> hoursPeriodsArrayList;
    Users currentUser;
    String uid;
    String newAccount;
    hoursPeriod week1;
    hoursPeriod week2;
    hoursPeriod week3;
    TextView day1HoursTextView;
    TextView day2HoursTextView;
    TextView day3HoursTextView;
    TextView day4HoursTextView;
    TextView day5HoursTextView;
    TextView day6HoursTextView;
    TextView day7HoursTextView;
    hoursPeriod week4;
    hoursPeriod week5;
    hoursPeriod week6;
    hoursPeriod week7;
    hoursPeriod week8;
    int day1Hours;
    int day2Hours;
    int day3Hours;
    int day4Hours;
    int day5Hours;
    int day6Hours;
    int day7Hours;
    int currentTotal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_one);
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
                    TextView textView = (TextView) findViewById(R.id.title);

//
                    week1hours = database.getReference("users").child(uid).child("hours").child("week1");
                    userInfo=database.getReference("users").child(uid).child("userInfo").child("name");
                    textView.setText(""+userInfo.toString());


                    week1hours.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.
//                            String value = dataSnapshot.getValue(String.class);
                            day1Hours = dataSnapshot.child("day1Hours").getValue(int.class);
                            day2Hours = dataSnapshot.child("day2Hours").getValue(int.class);
                            day3Hours = dataSnapshot.child("day3Hours").getValue(int.class);
                            day4Hours = dataSnapshot.child("day4Hours").getValue(int.class);
                            day5Hours = dataSnapshot.child("day5Hours").getValue(int.class);
                            day6Hours = dataSnapshot.child("day6Hours").getValue(int.class);
                            day7Hours = dataSnapshot.child("day7Hours").getValue(int.class);
                            String period = dataSnapshot.child("period").getValue(String.class);
                            currentTotal = (day1Hours + day2Hours + day3Hours + day4Hours + day5Hours + day6Hours + day7Hours);
                            TextView currentTotalHours = (TextView) findViewById(R.id.totalhoursshowing);
                            currentTotalHours.setText("" + currentTotal);

                            hoursPeriod currentHoursPeriod = new hoursPeriod(day1Hours, day2Hours, day3Hours, day4Hours, day5Hours, day6Hours, day7Hours, "one");

                            Log.d(TAG, "Value is: " + hours);
                            day1HoursTextView = (TextView) findViewById(R.id.day1TextView);
                            day2HoursTextView = (TextView) findViewById(R.id.day2TextView);
                            day3HoursTextView = (TextView) findViewById(R.id.day3TextView);
                            day4HoursTextView = (TextView) findViewById(R.id.day4TextView);
                            day5HoursTextView = (TextView) findViewById(R.id.day5TextView);
                            day6HoursTextView = (TextView) findViewById(R.id.day6TextView);
                            day7HoursTextView = (TextView) findViewById(R.id.day7TextView);
                            day1HoursTextView.setText("" + day1Hours);
                            day2HoursTextView.setText("" + day2Hours);
                            day3HoursTextView.setText("" + day3Hours);
                            day4HoursTextView.setText("" + day4Hours);
                            day5HoursTextView.setText("" + day5Hours);
                            day6HoursTextView.setText("" + day6Hours);
                            day7HoursTextView.setText("" + day7Hours);


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



    public void increment214(View view) {
        if (quantity214 == 8) {
            Toast.makeText(this, "You can only work a maximum of 8 hours a day", Toast.LENGTH_SHORT).show();
            user.getUid();
            return;
        }
        quantity214++;

        display214(quantity214);
        displayTotal();


    }

    public void decrement214(View view) {
        if (quantity214 == 0) {
            Toast.makeText(this, "You cannot work less then 0 hours a day", Toast.LENGTH_SHORT).show();

            return;
        }
        quantity214--;
        display214(quantity214);
        displayTotal();


    }

    private void display214(int numberofHours) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view214);
        quantityTextView.setText("" + numberofHours);
    }

    public void increment213(View view) {
        if (quantity213 == 8) {
            Toast.makeText(this, "You can only work a maximum of 8 hours a day", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity213++;
        display(quantity213);
        displayTotal();

    }

    public void decrement213(View view) {
        if (quantity213 == 0) {
            Toast.makeText(this, "You cannot work less then 0 hours a day", Toast.LENGTH_SHORT).show();

            return;
        }
        quantity213--;
        display(quantity213);
        displayTotal();


    }

    private void display(int numberofHours) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberofHours);
    }

    private void displayTotal() {
        totalHours = quantity213 + quantity214;
        TextView totalHoursTextView = (TextView) findViewById(R.id.totalhours);
        totalHoursTextView.setText("Total Hours: " + totalHours);
    }

    public void increment(View v) {

        switch (v.getId()) {

            case R.id.day1I:
                day1Hours++;
                currentTotal++;
                day1HoursTextView.setText("" + day1Hours);
                week1hours.child("day1Hours").setValue(day1Hours);
                break;
            case R.id.day2I:
                day2Hours++;
                currentTotal++;
                day2HoursTextView.setText("" + day2Hours);
                week1hours.child("day2Hours").setValue(day2Hours);
                break;

            case R.id.day3I:
                day3Hours++;
                currentTotal++;
                day1HoursTextView.setText("" + day3Hours);
                week1hours.child("day3Hours").setValue(day3Hours);
                break;

            case R.id.day4I:
                day4Hours++;
                currentTotal++;
                day4HoursTextView.setText("" + day4Hours);
                week1hours.child("day4Hours").setValue(day4Hours);
                break;

            case R.id.day5I:
                day5Hours++;
                currentTotal++;
                day5HoursTextView.setText("" + day5Hours);
                week1hours.child("day5Hours").setValue(day5Hours);
                break;

            case R.id.day6I:
                day6Hours++;
                currentTotal++;
                day6HoursTextView.setText("" + day6Hours);
                week1hours.child("day6Hours").setValue(day6Hours);
                break;

            case R.id.day7I:
                day7Hours++;
                currentTotal++;
                day7HoursTextView.setText("" + day7Hours);
                week1hours.child("day7Hours").setValue(day7Hours);
                break;

            default:
                    Toast.makeText(this, "error, could not update", Toast.LENGTH_SHORT).show();

                break;


        }
    }


    public void decrement(View v) {
        switch (v.getId()) {

            case R.id.day1d:
                day1Hours--;
                currentTotal--;
                day1HoursTextView.setText("" + day1Hours);
                week1hours.child("day1Hours").setValue(day1Hours);
                break;
            case R.id.day2d:
                day2Hours--;
                currentTotal--;
                day2HoursTextView.setText("" + day2Hours);
                week1hours.child("day2Hours").setValue(day2Hours);
                break;

            case R.id.day3d:
                day3Hours--;
                currentTotal--;
                day1HoursTextView.setText("" + day3Hours);
                week1hours.child("day3Hours").setValue(day3Hours);
                break;

            case R.id.day4d:
                day4Hours--;
                currentTotal--;
                day4HoursTextView.setText("" + day4Hours);
                week1hours.child("day4Hours").setValue(day4Hours);
                break;

            case R.id.day5d:
                day5Hours--;
                currentTotal--;
                day5HoursTextView.setText("" + day5Hours);
                week1hours.child("day5Hours").setValue(day5Hours);
                break;

            case R.id.day6d:
                day6Hours--;
                currentTotal--;
                day6HoursTextView.setText("" + day6Hours);
                week1hours.child("day6Hours").setValue(day6Hours);
                break;

            case R.id.day7d:
                day7Hours--;
                currentTotal--;
                day7HoursTextView.setText("" + day7Hours);
                week1hours.child("day7Hours").setValue(day7Hours);
                break;

            default:
                Toast.makeText(this, "error, could not update", Toast.LENGTH_SHORT).show();

                break;
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

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.help:
                Intent intent = new Intent(WeekOne.this, help.class);
                startActivity(intent);
                return true;
            case R.id.logOut:
                mAuth.signOut();
                return true;
            case R.id.nav_camera:
                Intent camera = new Intent(WeekOne.this, Camera.class);
                startActivity(camera);


            default:
                return super.onOptionsItemSelected(item);
        }
    }


}


