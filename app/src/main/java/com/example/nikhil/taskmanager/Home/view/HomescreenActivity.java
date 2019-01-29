package com.example.nikhil.taskmanager.Home.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.nikhil.taskmanager.R;
import com.example.nikhil.taskmanager.base.view.BaseActivity;
import com.example.nikhil.taskmanager.landing.view.LandingActivity;
import com.example.nikhil.taskmanager.login.view.LoginActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomescreenActivity extends BaseActivity {

    private android.support.v4.widget.DrawerLayout mDrawerLayout;
    private android.support.v7.widget.Toolbar toolbar;
    private MenuItem myTask;
    private DatabaseReference mDatabase;
    TextView fullname,email;
    private static final String TAG = "HomescreenActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        Intent intent = new Intent(HomescreenActivity.this,navigationHeader.class);
        toolbar = findViewById(R.id.toolbar);
        myTask = findViewById(R.id.nav_mytask);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_dehaze_black_24dp);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(HomescreenActivity.this, mDrawerLayout, toolbar,R.string.nav_drawer_settings,R.string.nav_drawer_dashboard);
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Users = mDatabase.child("Users");
        String name = mPreferenceHelper.getString("full_name","");
        Log.d(TAG,"Full Name is "+name);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        fullname = header.findViewById(R.id.nav_full_name);
        email = header.findViewById(R.id.nav_email);
        fullname.setText(mPreferenceHelper.getString("full_name",""));
        email.setText(mPreferenceHelper.getString("e-mail",""));
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        // set item as selected to persist highlight
                        int id = menuItem.getItemId();

                        if(id == R.id.nav_alltask){
                            setTitle("All Task");
                            AllTaskFragment allTaskFragment= new AllTaskFragment();
                            FragmentManager fragmentManager=getSupportFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.frame_layout,allTaskFragment).commit();
                        }
                        else if(id == R.id.nav_mytask){
                            setTitle("My Task");
                            MyTaskFragment myTaskFragment= new MyTaskFragment();
                            FragmentManager fragmentManager=getSupportFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.frame_layout,myTaskFragment).commit();

                        }
                        else if(id == R.id.nav_dashboard){
                            setTitle("Dashboard");
                        }
                        else if(id == R.id.nav_users){
                            setTitle("Team Users");
                        }
                        else if(id == R.id.nav_setting){
                            setTitle("Settings");
                        }
                        else if(id == R.id.nav_logout){
                            mPreferenceHelper.putBoolean("is_Login",false);
                            Intent intent = new Intent(HomescreenActivity.this,LandingActivity.class);
                            startActivity(intent);
                        }
                        Log.d("Itemid","Selected ID:"+id);
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });


        /*mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );*/

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void init() {

    }
}
