package com.example.nikhil.taskmanager.user.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nikhil.taskmanager.Constants.AppConstant;
import com.example.nikhil.taskmanager.R;
import com.example.nikhil.taskmanager.SimpleDividerItemDecoration;
import com.example.nikhil.taskmanager.UsersAdapter;
import com.example.nikhil.taskmanager.UsersFragmentAdapter;
import com.example.nikhil.taskmanager.base.view.BaseFragment;
import com.example.nikhil.taskmanager.model.Users;
import com.example.nikhil.taskmanager.user.view.DatabaseHelper;
import com.firebase.ui.database.FirebaseListOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter_LifecycleAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class UsersFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "UserFragment";

    public String teamName,key;
    protected RecyclerView usersRecyclerView;
    FirebaseRecyclerOptions options;
    private DatabaseReference mUsersDatabase,UsersFromDB;
    List<Users> data = new ArrayList<Users>();
    UsersAdapter adapter;
    Context context;

    public UsersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*mUsersDatabase = FirebaseDatabase.getInstance().getReference();
        UsersFromDB = mUsersDatabase.child("Users");
        UsersFromDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    key = dataSnapshot1.getKey();
                    String teamNameFrmSnapshot = key.substring(0,key.indexOf("_"));
                    if(teamNameFrmSnapshot.equals(AppConstant.BundleKey.nameOfTeam)){
                        Users users = dataSnapshot1.getValue(Users.class);
                        data.add(users);
                    }
                }
                adapter = new UsersAdapter(context,data);
                usersRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

    }

    @Override
    protected void init() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_users, container, false);
        context = getActivity();
        usersRecyclerView = rootView.findViewById(R.id.recy_view_users);
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        usersRecyclerView.addItemDecoration(new
                DividerItemDecoration(getActivity(),
                DividerItemDecoration.HORIZONTAL
        ));
        usersRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        /*DatabaseHelper helper = new DatabaseHelper(getActivity());
        List<Users> usersData = helper.getData();
        for (int i=0;i<usersData.size();i++){
            Users users = new Users();
            users.getFullName();
            Log.d(TAG,"Users FullName is "+users.getFullName());
            usersData.add(users);
        }
        adapter = new UsersAdapter(context,usersData);
        Log.d(TAG,"ID of View "+usersRecyclerView);
        usersRecyclerView.setAdapter(adapter);*/
        DatabaseHelper helper = new DatabaseHelper(getActivity());
        List<Users> data = helper.getData();
        adapter = new UsersAdapter(context,data);
        Log.d(TAG,"Data in users fragment are "+data);
        usersRecyclerView.setAdapter(adapter);
        usersRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        return rootView;
    }

}

