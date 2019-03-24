package com.example.nikhil.taskmanager.Home.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nikhil.taskmanager.AllTaskAdapter;
import com.example.nikhil.taskmanager.Constants.AppConstant;
import com.example.nikhil.taskmanager.R;
import com.example.nikhil.taskmanager.base.view.BaseActivity;
import com.example.nikhil.taskmanager.landing.view.LandingActivity;
import com.example.nikhil.taskmanager.model.Tasks;
import com.example.nikhil.taskmanager.model.Users;
import com.example.nikhil.taskmanager.settings.view.SettingsFragment;
import com.example.nikhil.taskmanager.user.view.DatabaseHelper;
import com.example.nikhil.taskmanager.user.view.UsersFragment;
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
    public String mFullName,key;
    TextView fullname,email;
    ImageView profileImg;
    public List<Users> getData;
    private List<Tasks> getTasksData = new ArrayList<>();


    private static final String TAG = "HomescreenActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        View usersFragment = findViewById(R.id.users_fragment);
        Log.d(TAG,"ID OF RECY "+R.id.recy_view_users);
        /*recy_users = usersFragment.findViewById(R.id.recy_view_users);
        recy_users.setLayoutManager(new LinearLayoutManager(this));*/
        //UsersAdapter adapter = new UsersAdapter(this,user_data);
        /*mRecy_view.setLayoutManager(new LinearLayoutManager(this));*/
        /*MyAdapter adapter=new MyAdapter(HomescreenActivity.this,getData());
        mRecy_view.setAdapter(adapter);*/
        Bundle bundle = new Bundle();
        bundle.putString("teamname",mPreferenceHelper.getString("team_name",""));
        AppConstant.BundleKey.nameOfTeam = mPreferenceHelper.getString("team_name","");
        new AllTaskAdapter(mPreferenceHelper.getString("team_name",""));
        UsersFragment fragment = new UsersFragment();
        fragment.setArguments(bundle);
        Log.d(TAG,"Status is a "+bundle);
        Intent intent = new Intent(HomescreenActivity.this,navigationHeader.class);
        toolbar = findViewById(R.id.toolbar);
        myTask = findViewById(R.id.nav_mytask);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_dehaze_black_24dp);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(HomescreenActivity.this, mDrawerLayout, toolbar,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        String name = mPreferenceHelper.getString("full_name","");
        Log.d(TAG,"Full Name is "+name);
        final NavigationView navigationView = findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        fullname = header.findViewById(R.id.nav_full_name);
        email = header.findViewById(R.id.nav_email);
        profileImg = header.findViewById(R.id.profile_pic);
        fullname.setText(mPreferenceHelper.getString("full_name",""));
        email.setText(mPreferenceHelper.getString("e-mail",""));
        //DatabaseHelper helper = new DatabaseHelper(HomescreenActivity.this);
        //DatabaseReference UsersFromDB = mDatabase.child("Users");
        DatabaseReference UsersDB = mDatabase.child("Users");
        final DatabaseHelper helper = new DatabaseHelper(HomescreenActivity.this);
        helper.delete_user();
        UsersDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    key = dataSnapshot1.getKey();
                    String teamNameFromSnapshot = key.substring(0,key.indexOf("_"));
                    if(teamNameFromSnapshot.equals(mPreferenceHelper.getString("team_name",""))){
                        Users users = dataSnapshot1.getValue(Users.class);
                        Log.d(TAG,"Team names are "+dataSnapshot1.child("teamName").getValue().toString());
                        helper.insert_user(users);
                        Log.d(TAG,"Emails names are "+dataSnapshot1.child("email").getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

       /* FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Users>()
                .setQuery(UsersDB, Users.class)
                .build();
        final List<Users> data = new ArrayList<>();
        UsersDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    key = dataSnapshot1.getKey();
                    Log.d(TAG,"On Data Changed "+dataSnapshot1);
                    String teamNameFrmSnapshot = key.substring(0,key.indexOf("_"));
                    if(teamNameFrmSnapshot.equals(mPreferenceHelper.getString("team_name",""))){
                        Users users = dataSnapshot1.getValue(Users.class);
                        Log.d(TAG,"Names are a "+dataSnapshot1.child("fullName").getValue().toString());
                        data.add(users);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        UsersFragmentAdapter adapter = new UsersFragmentAdapter(options,this,data);
        View view = LayoutInflater.from(this).inflate(R.layout.fragment_users,null);
        View rootView = view.getRootView();

        RecyclerView recyclerView = rootView.findViewById(R.id.recy_view_users);
        Log.d(TAG,"Recycler view ID "+recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.startListening();*/


        /*DatabaseReference UsersDB = mDatabase.child("Users").child(mPreferenceHelper.getString("team_name ","") + "_" + mPreferenceHelper.getString("e-mail","").substring(0, mPreferenceHelper.getString("e-mail","").indexOf("@")).toLowerCase());
        */
       /* UsersDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG,"DataSnapshot is "+dataSnapshot);
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
        //Log.d(TAG,"Database Table find :"+UsersDB.getKey());
        /*Users users = new Users();
        users.setEmail(UsersDB);*/

        final List<Tasks> allTaskList = new ArrayList<>();
        final List<Tasks> myTaskList  = new ArrayList<>();
        DatabaseReference tasks = mDatabase.child("Tasks");
        helper.delete_task();
        tasks.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    String doc_id = dataSnapshot1.getKey();
                    String teamNameFromSnapshotOfTask = doc_id.substring(doc_id.indexOf("_")+1);
                    if(teamNameFromSnapshotOfTask.equals(mPreferenceHelper.getString("team_name",""))){
                        Tasks task = dataSnapshot1.getValue(Tasks.class);
                        allTaskList.add(task);
                        helper.insert_task(task);
                        String assignMembers = dataSnapshot1.child("assignTo").getValue().toString();
                        String taskEmail = mPreferenceHelper.getString("e-mail","");
                        AppConstant.BundleKey.EMAIL = taskEmail;
                        Log.d(TAG,"Check in Task Assign To "+assignMembers);
                        Log.d(TAG,"Check in Task Team Name "+taskEmail);
                        Log.d(TAG,"Check in Task "+assignMembers.contains(taskEmail));
                        if(assignMembers.contains(taskEmail)){
                            myTaskList.add(task);
                        }
                    }
                }
                Log.d(TAG,"Task List "+allTaskList);
                Log.d(TAG,"Task List "+myTaskList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



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
                            replaceFragment(allTaskFragment);
                        }
                        else if(id == R.id.nav_mytask){
                            setTitle("My Task");
                            MyTaskFragment myTaskFragment= new MyTaskFragment();
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
                            SettingsFragment fragment = new SettingsFragment();
                            replaceFragment(fragment);
                        }
                        else if(id == R.id.nav_logout){
                            mPreferenceHelper.putBoolean("is_Login",false);
                            Intent intent = new Intent(HomescreenActivity.this,LandingActivity.class);
                            startActivity(intent);
                        }
                        Log.d("Itemid","Selected ID:"+id);
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        menuItem.setChecked(true);
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
                MyTaskFragment Fragment = new MyTaskFragment();
                setTitle("My Task");
                addFragment(Fragment);
                item.setChecked(true);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void init() {

    }
}
