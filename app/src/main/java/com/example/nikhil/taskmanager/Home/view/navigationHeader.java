package com.example.nikhil.taskmanager.Home.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.nikhil.taskmanager.R;
import com.example.nikhil.taskmanager.base.view.BaseActivity;

public class navigationHeader extends BaseActivity {

    private static TextView fullname,email;
    private static final String TAG = "navigationHeader";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_header);
        Log.d(TAG,"In Navigation Header ");
        fullname = findViewById(R.id.nav_full_name);
        Log.d(TAG,"Full name is "+fullname);
        fullname.setText(mPreferenceHelper.getString("full_name",""));
        email = findViewById(R.id.nav_email);
        email.setText(mPreferenceHelper.getString("e-mail",""));
    }

    @Override
    protected void init() {

    }
}
