package com.example.nikhil.taskmanager.splash.view;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;

import com.example.nikhil.taskmanager.Home.view.HomescreenActivity;
import com.example.nikhil.taskmanager.base.view.BaseActivity;
import com.example.nikhil.taskmanager.R;
import com.example.nikhil.taskmanager.landing.view.LandingActivity;
import com.example.nikhil.taskmanager.util.PreferenceHelper;

public class SplashActivity extends BaseActivity {

    private static int FLASH_TIME_OUT = 1000;
    public static boolean isLogin;
    Intent intent;
    private static final String TAG = "LauncherActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        intent = new Intent(SplashActivity.this, com.example.nikhil.taskmanager.launcher.view.LauncherActivity.class);
        Log.d(TAG, "In Launcher Activity");
        isLogin = mPreferenceHelper.getBoolean(PreferenceHelper.IS_LOGIN, false);
        if (isLogin) {

            // navigate to Home Screen
            intent = new Intent(SplashActivity.this, HomescreenActivity.class);
            startActivity(intent);

        } else {
            //Navigate to Next (Landing Activity) screen

            intent = new Intent(SplashActivity.this, LandingActivity.class);
            startActivity(intent);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, com.example.nikhil.taskmanager.launcher.view.LauncherActivity.class);
                startActivity(intent);
                finish();
            }
        }, FLASH_TIME_OUT);


    }

    @Override
    protected void init() {

    }
}
