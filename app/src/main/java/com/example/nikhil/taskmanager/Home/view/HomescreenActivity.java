package com.example.nikhil.taskmanager.Home.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nikhil.taskmanager.R;
import com.example.nikhil.taskmanager.base.view.BaseActivity;
import com.example.nikhil.taskmanager.landing.view.LandingActivity;
import com.example.nikhil.taskmanager.model.Users;
import com.example.nikhil.taskmanager.user.view.DatabaseHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomescreenActivity extends BaseActivity {

    private android.support.v4.widget.DrawerLayout mDrawerLayout;
    private android.support.v7.widget.Toolbar toolbar;
    private MenuItem myTask;
    private DatabaseReference mDatabase;
    TextView fullname,email;
    ImageView profileImg;
    protected RecyclerView mRecy_view,recy_users;
    public List<Users> data1;

    private static final String TAG = "HomescreenActivity";
    /*private List<Product> getData(){
        List<Product> data=new ArrayList<>();
        Product p1=new Product();
        p1.setName("Television");
        p1.setUnitPrice(90);
        data.add(p1);
        Product p2=new Product();
        p2.setName("Refrigerator");
        p2.setUnitPrice(80);
        data.add(p2);
        return data;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mRecy_view = findViewById(R.id.allTaskRecyView);
        View usersFragment = findViewById(R.id.users_fragment);
        Log.d(TAG,"ID OF RECY "+R.id.recy_view_users);
        /*recy_users = usersFragment.findViewById(R.id.recy_view_users);
        recy_users.setLayoutManager(new LinearLayoutManager(this));*/
        //UsersAdapter adapter = new UsersAdapter(this,user_data);
        /*mRecy_view.setLayoutManager(new LinearLayoutManager(this));*/
        /*MyAdapter adapter=new MyAdapter(HomescreenActivity.this,getData());
        mRecy_view.setAdapter(adapter);*/
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
        String name = mPreferenceHelper.getString("full_name","");
        Log.d(TAG,"Full Name is "+name);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        fullname = header.findViewById(R.id.nav_full_name);
        email = header.findViewById(R.id.nav_email);
        profileImg = header.findViewById(R.id.profile_pic);
        fullname.setText(mPreferenceHelper.getString("full_name",""));
        email.setText(mPreferenceHelper.getString("e-mail",""));
        DatabaseHelper helper = new DatabaseHelper(HomescreenActivity.this);
        DatabaseReference UsersFromDB = mDatabase.child("Users");
        data1 = new ArrayList<>();
        UsersFromDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                /*if(dataSnapshot.child("teamName").equals(mPreferenceHelper.getString("team_name","")))
                {
                    user_data.add(dataSnapshot.child("fullName").toString());
                }*/
                if (dataSnapshot.getValue()!=null){
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                    String Key = dataSnapshot1.getKey();
                    String teamNameFrmSnapshot = Key.substring(0,Key.indexOf("_"));
                    Log.d(TAG,"Team name in home Screen "+mPreferenceHelper.getString("team_name",""));
                    Log.d(TAG,"Check "+teamNameFrmSnapshot.equalsIgnoreCase(mPreferenceHelper.getString("team_name","")));
                    if(teamNameFrmSnapshot.equalsIgnoreCase(mPreferenceHelper.getString("team_name",""))){
                        DatabaseHelper helper = new DatabaseHelper(HomescreenActivity.this);
                        Users users = new Users();
                        Log.d(TAG,"Value changed "+dataSnapshot1.child("email").getValue().toString());
                        users.setEmail( dataSnapshot1.child("email").getValue().toString());
                        users.setFullName(dataSnapshot1.child("fullName").getValue().toString());
                        users.setPassword( dataSnapshot1.child("password").getValue().toString());
                        users.setTeamName( dataSnapshot1.child("teamName").getValue().toString());
                        users.setUsername( dataSnapshot1.child("username").getValue().toString());
                        data1.add(users);
                        Log.d(TAG,"Value of user in home "+data1.toString());
                        helper.insert_user(users);
                    }
                    Log.d(TAG,"teamNameFrmSnapshot "+teamNameFrmSnapshot);
                }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        /*DatabaseReference UsersDB = mDatabase.child("Users").child(mPreferenceHelper.getString("team_name ","") + "_" + mPreferenceHelper.getString("e-mail","").substring(0, mPreferenceHelper.getString("e-mail","").indexOf("@")).toLowerCase());
        */DatabaseReference UsersDB = mDatabase.child("Users");
        UsersDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG,"DataSnapshot is "+dataSnapshot);
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Log.d(TAG,"Database Table find :"+UsersDB.getKey());
        /*Users users = new Users();
        users.setEmail(UsersDB);*/
        MyTaskFragment Fragment = new MyTaskFragment();
        setTitle("My Task");
        addFragment(Fragment);
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
                            replaceFragment(allTaskFragment);
                        }
                        else if(id == R.id.nav_mytask){
                            setTitle("My Task");
                            MyTaskFragment myTaskFragment= new MyTaskFragment();
                            FragmentManager fragmentManager=getSupportFragmentManager();
                            replaceFragment(myTaskFragment);

                        }
                        else if(id == R.id.nav_dashboard){
                            setTitle("Dashboard");
                            DashboardFragment fragment = new DashboardFragment();
                            replaceFragment(fragment);
                        }
                        else if(id == R.id.nav_users){
                            setTitle("Team Users");
                            UsersFragment fragment = new UsersFragment();
                            replaceFragment(fragment);
                        }
                        else if(id == R.id.nav_setting){
                            setTitle("Settings");
                            replaceFragment(null);
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
    public void addFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_layout, fragment)
                .commit();
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
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
