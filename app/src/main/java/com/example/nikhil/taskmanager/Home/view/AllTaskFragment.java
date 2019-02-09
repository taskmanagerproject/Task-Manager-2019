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


public class AllTaskFragment extends BaseFragment {

    @BindView(R.id.FloatingPlusBtnAllTask)
    Button allTaskFragment;
    private RecyclerView mRecyclerView;
    private AllTaskAdapter adapter;
    private DatabaseReference mDatabase,mTasksDatabase;

    public AllTaskFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mTasksDatabase = mDatabase.child("Tasks");
        final List<Tasks> allTaskList = new ArrayList<>();
        final List<Tasks> myTaskList = new ArrayList<>();
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
                        allTaskList.add(task);
                        Log.d(TAG,"Task Data "+dataSnapshot1.getValue().toString());
                        String assignMembers = dataSnapshot1.child("assignTo").getValue().toString();
                        String taskEmail = AppConstant.BundleKey.EMAIL;
                        if(assignMembers.contains(taskEmail)){
                            myTaskList.add(task);
                        }
                    }
                }
                adapter = new AllTaskAdapter(getActivity(),allTaskList);
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
        View view = inflater.inflate(R.layout.fragment_all_task, container, false);
        mRecyclerView = view.findViewById(R.id.allTaskRecyView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ButterKnife.bind(this, view);
        return view;
    }



    @Override
    protected void init() {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    @OnClick(R.id.FloatingPlusBtnAllTask)
    public void onButtonClick(View view)
    {
        Intent intent = new Intent(getActivity(),TaskActivity.class);
        startActivity(intent);

    }
}
