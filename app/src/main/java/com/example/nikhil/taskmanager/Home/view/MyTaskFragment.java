package com.example.nikhil.taskmanager.Home.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyTaskFragment extends BaseFragment {


    private static final String TAG ="In My task" ;
    private RecyclerView mRecyclerView;
    Context context;
    private MyTaskAdapter adapter;
    public MyTaskFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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
