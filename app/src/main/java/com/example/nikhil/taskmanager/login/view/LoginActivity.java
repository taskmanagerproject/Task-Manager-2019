package com.example.nikhil.taskmanager.login.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nikhil.taskmanager.Constants.AppConstant;
import com.example.nikhil.taskmanager.Home.view.HomescreenActivity;
import com.example.nikhil.taskmanager.R;
import com.example.nikhil.taskmanager.base.view.BaseActivity;
import com.example.nikhil.taskmanager.user.view.DatabaseHelper;
import com.example.nikhil.taskmanager.util.PreferenceHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends BaseActivity {

    private TextView email,password;
    private Button login;
    private DatabaseReference mDatabase;
    private String mTeamName;
    ProgressDialog mProgress;
    private static final String TAG = "LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent intent = getIntent();
        mTeamName = intent.getStringExtra(AppConstant.BundleKey.TEAM_NAME);
        Log.d(TAG,"Team Name "+mTeamName);
        mProgress = new ProgressDialog(LoginActivity.this);
        mProgress.setTitle("Login.....");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        login = findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
                @Override
            public void onClick(View view) {
                    mProgress.show();
                final String mail = email.getText().toString().toLowerCase();
                final String pass = password.getText().toString();
                Log.d(TAG, "email id : " + mail);
                mDatabase = FirebaseDatabase.getInstance().getReference();
                DatabaseReference Users = mDatabase.child("Users")
                        .child(mTeamName + "_" + mail.substring(0, mail.indexOf("@")));
                Users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.d(TAG, "onDataChange : " + dataSnapshot);
                        /*Log.d(TAG,"Login values "+dataSnapshot.child("email").getValue().toString());
                        Log.d(TAG,"Login values "+dataSnapshot.child("password").getValue().toString());*/
                        if (dataSnapshot.getValue() != null && dataSnapshot.child("email").getValue().toString().equalsIgnoreCase(mail)
                                && dataSnapshot.child("password").getValue().toString().equals(pass)) {
                            mProgress.setMessage("Login Done Successfully!!");
                            mProgress.dismiss();
                            //Navigate to home screen
                            Log.d(TAG,"Login values "+mail);
                            Log.d(TAG,"Login values "+pass);
                            Intent intent = new Intent(LoginActivity.this,HomescreenActivity.class);
                            mPreferenceHelper.putBoolean("is_Login",true);
                            mPreferenceHelper.putString("e-mail",mail);
                            mPreferenceHelper.putString("team_name ",mTeamName);
                            String name = dataSnapshot.child("fullName").getValue().toString();
                            Log.d(TAG,"In Login Activity "+name);
                            mPreferenceHelper.putString("full_name",name);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Email or Password is incorrect", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }

    @Override
    protected void init() {

    }
}

