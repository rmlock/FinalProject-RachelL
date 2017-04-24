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

public class WeekOne extends AppCompatActivity {
    int quantity213 = 0;
    int quantity214 = 0;
    int totalHours = quantity213 + quantity214;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    String TAG= "Main";
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                    Intent intent = new Intent(WeekOne.this, StartPage.class);
                    startActivity(intent);
                    finish();
                }
                // ...
            }
        };
    }

    public void increment214(View view) {
        if (quantity214 == 8) {
            Toast.makeText(this, "You can only work a maximum of 8 hours a day", Toast.LENGTH_SHORT).show();
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
                Intent intent=new Intent(WeekOne.this,help.class);
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
