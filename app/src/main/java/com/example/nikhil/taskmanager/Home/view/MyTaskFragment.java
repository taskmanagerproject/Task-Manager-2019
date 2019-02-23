package com.example.nikhil.taskmanager.Home.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.nikhil.taskmanager.AllTaskAdapter;
import com.example.nikhil.taskmanager.Constants.AppConstant;
import com.example.nikhil.taskmanager.MyTaskAdapter;
import com.example.nikhil.taskmanager.R;
import com.example.nikhil.taskmanager.base.view.BaseFragment;
import com.example.nikhil.taskmanager.model.Tasks;
import com.example.nikhil.taskmanager.task.view.TaskActivity;
import com.example.nikhil.taskmanager.user.view.DatabaseHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.constraint.Constraints.TAG;

public class MyTaskFragment extends BaseFragment {


    private static final String TAG ="In My task" ;
    private RecyclerView mRecyclerView;
    Context context;
    private MyTaskAdapter adapter;
    DatabaseReference mDatabase,mTasksDatabase;
    List<Tasks> myTaskList;
    public MyTaskFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myTaskList = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mTasksDatabase = mDatabase.child("Tasks");
        mTasksDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    String doc_id = dataSnapshot1.getKey();
                    String teamNameFromSnapshotOfTask = doc_id.substring(doc_id.indexOf("_")+1);
                    Log.d(TAG,"On Data Changed "+dataSnapshot1.getValue().toString());
                    Log.d(TAG,"Team Name value is of pref"+AppConstant.BundleKey.nameOfTeam);
                    Log.d(TAG,"Team Name Value is snap"+teamNameFromSnapshotOfTask);
                    Log.d(TAG,"Ckeck in "+teamNameFromSnapshotOfTask.equals(AppConstant.BundleKey.EMAIL));
                    if(teamNameFromSnapshotOfTask.equals(AppConstant.BundleKey.nameOfTeam)){
                        Tasks task = dataSnapshot1.getValue(Tasks.class);
                        Log.d(TAG,"Task Data "+dataSnapshot1.getValue().toString());
                        String assignMembers = dataSnapshot1.child("assignTo").getValue().toString();
                        String taskEmail = AppConstant.BundleKey.EMAIL;
                        if(assignMembers.contains(taskEmail)){
                            myTaskList.add(task);
                        }
                    }
                }
                adapter = new MyTaskAdapter(getActivity(),myTaskList);
                mRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_task, container, false);
        context  = getActivity();
        mRecyclerView = view.findViewById(R.id.myTaskRecyView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Log.d(TAG,"In my task fragment on create view");
        DatabaseHelper helper = new DatabaseHelper(getActivity());
        Log.d(TAG,"In my task fragment on create");
        Log.d(TAG,"email is "+AppConstant.BundleKey.EMAIL);
        List<Tasks> data = helper.getMyTasks(AppConstant.BundleKey.EMAIL);
        adapter = new MyTaskAdapter(context,data);
        mRecyclerView.setAdapter(adapter);
        ButterKnife.bind(this, view);
        return view;
    }
    @OnClick(R.id.FloatingPlusBtnMyTask)
    public void onButtonClick(View view){
        Intent intent = new Intent(getActivity(),TaskActivity.class);
        startActivity(intent);
    }

    @Override
    protected void init() {

    }
}
