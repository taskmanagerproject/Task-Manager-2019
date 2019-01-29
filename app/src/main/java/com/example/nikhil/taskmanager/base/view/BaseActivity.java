package com.example.nikhil.taskmanager.base.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.example.nikhil.taskmanager.util.PreferenceHelper;


public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";

    protected PreferenceHelper mPreferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_base);
        init();
        Log.d(TAG,"In Base Activity");
        mPreferenceHelper = PreferenceHelper.getInstance(this);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    protected abstract void init();
}
