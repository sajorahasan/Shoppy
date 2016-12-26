package com.sajorahasan.shoppy;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class ShoppyActivity extends AppCompatActivity {
    private static final String TAG = "ShoppyActivity";

    private Toolbar toolbar;
    //boolean variable to check user is logged in or not
    //initially it is false
    private boolean loggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppy);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LoginFragment fragment = new LoginFragment();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragment_frame, fragment, "Sign in");
        transaction.commit();

    }

    @Override
    public void onResume() {
        super.onResume();
        //In onResume fetching value from sharedPreference
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedPreferences
        loggedIn = sharedPreferences.getBoolean(Constants.LOGGEDIN_SHARED_PREF, false);

        //If we will get true
        if (loggedIn) {
            //We will start the Profile Activity
            Intent intent = new Intent(ShoppyActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }
}

