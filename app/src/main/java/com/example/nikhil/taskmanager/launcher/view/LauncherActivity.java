package com.example.nikhil.taskmanager.launcher.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.nikhil.taskmanager.Home.view.HomescreenActivity;
import com.example.nikhil.taskmanager.base.view.BaseActivity;
import com.example.nikhil.taskmanager.R;
import com.example.nikhil.taskmanager.landing.view.LandingActivity;
import com.example.nikhil.taskmanager.login.view.LoginActivity;
import com.example.nikhil.taskmanager.util.PreferenceHelper;

import java.util.Timer;
import java.util.TimerTask;

public class LauncherActivity extends BaseActivity {

    public static boolean isLogin;
    Intent intent;
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    private static final String TAG = "LauncherActivity";
    private Timer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        //intent = new Intent(LauncherActivity.this, LauncherActivity.class);
        //Log.d(TAG, "In Launcher Activity");
        isLogin = mPreferenceHelper.getBoolean(PreferenceHelper.IS_LOGIN, false);
        /*if (isLogin) {

            // navigate to Home Screen
            intent = new Intent(LauncherActivity.this, HomescreenActivity.class);
            startActivity(intent);

        } else {
            //Navigate to Next (Landing Activity) screen

            intent = new Intent(LauncherActivity.this, LandingActivity.class);
            startActivity(intent);
        }*/
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(isLogin){
                    intent = new Intent(LauncherActivity.this, HomescreenActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(LauncherActivity.this, LandingActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        },800);
    }

    @Override
    protected void init() {

    }
}
