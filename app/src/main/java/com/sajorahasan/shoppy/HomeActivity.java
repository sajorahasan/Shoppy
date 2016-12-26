package com.sajorahasan.shoppy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "HomeActivity";

    private SharedPreferences pref;
    private TextView tvUserName, tvEmail;
    private View hView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        hView = navigationView.getHeaderView(0);
        //Initializing Views
        tvUserName = (TextView) hView.findViewById(R.id.tvUserName);
        tvEmail = (TextView) hView.findViewById(R.id.tvUserEmail);

        navigationView.setNavigationItemSelectedListener(this);

        //pref =  getSharedPreferences();

        //Creating a shared preference
        pref = HomeActivity.this.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        tvUserName.setText(pref.getString(Constants.NAME, ""));
        tvEmail.setText(pref.getString(Constants.EMAIL, ""));
        Log.d(TAG, "onCreate: Email " + tvEmail + pref.getString(Constants.EMAIL, ""));

        //add this line to display menu1 when the activity is loaded
        displaySelectedScreen(R.id.nav_home);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_logout) {
            //calling logout method when the logout button is clicked
            logout();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        //calling the method displaySelectedScreen and passing the id of selected menu
        displaySelectedScreen(item.getItemId());
        //make this method blank
        return true;
    }

    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_home:
                fragment = new HomeFragment();
                break;
            case R.id.nav_category:
                fragment = new CategoryFragment();
                break;
            case R.id.nav_deal:
                //fragment = new Menu3();
                break;
            case R.id.nav_cart:
                fragment = new CartFragment();
                break;
            case R.id.nav_profile:
                fragment = new ProfileFragment();
                break;
            case R.id.nav_wishlist:
                //fragment = new Menu3();
                break;
            case R.id.nav_order:
                //fragment = new Menu3();
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    //Logout function
    private void logout() {
        //Creating an alert dialog to confirm logout
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to logout?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        //Getting out sharedPreferences
                        SharedPreferences preferences = getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        //Getting editor
                        SharedPreferences.Editor editor = preferences.edit();

                        //Putting the value false for loggedIn
                        editor.putBoolean(Constants.LOGGEDIN_SHARED_PREF, false);

                        //Putting blank value to name & email
                        editor.putString("uId", "");
                        editor.putString("name", "");
                        editor.putString("email", "");

                        //Saving the sharedPreferences
                        editor.commit();

                        //Starting login activity
                        Intent intent = new Intent(HomeActivity.this, ShoppyActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        //Showing the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
}
