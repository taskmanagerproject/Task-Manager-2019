package com.example.nikhil.taskmanager.signup.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nikhil.taskmanager.Constants.AppConstant;
import com.example.nikhil.taskmanager.Home.view.HomescreenActivity;
import com.example.nikhil.taskmanager.R;
import com.example.nikhil.taskmanager.base.view.BaseActivity;
import com.example.nikhil.taskmanager.model.Teams;
import com.example.nikhil.taskmanager.model.UserModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends BaseActivity {

    public DatabaseReference mDatabase;
    private static final String TAG = "SignUpActivity";
    private String mTeamName,mFullName,mEmail,mPass,mUserName;
    EditText TeamName,FullName,Email,Password,ConfirmPassword,UserNmae;

    static  Button Btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Btn = findViewById(R.id.SignUpBtn);
        TeamName = findViewById(R.id.signup_teamname);
        FullName = findViewById(R.id.signup_fullname);
        Email =  findViewById(R.id.signup_email);
        Password = findViewById(R.id.signup_pass);
        ConfirmPassword = findViewById(R.id.signup_retypepass);
        UserNmae = findViewById(R.id.signup_username);
        Intent intent = getIntent();
        final String RecievedTextFromIntent = intent.getStringExtra("click");
        mTeamName = intent.getStringExtra(AppConstant.BundleKey.TEAM_NAME);
        mEmail = intent.getStringExtra(AppConstant.BundleKey.EMAIL);
        mPass = intent.getStringExtra(AppConstant.BundleKey.PASSWORD);
        mFullName = intent.getStringExtra(AppConstant.BundleKey.FULL_NAME);
        mUserName = intent.getStringExtra(AppConstant.BundleKey.USER_NAME);
        TeamName.setText(mTeamName);
        Log.d(TAG,"message is "+RecievedTextFromIntent);
        switch (RecievedTextFromIntent){
            case "Create":
                Btn.setText("Create");
                break;
            case "Join":
                Btn.setText("Join");
        }

        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String teamName = TeamName.getText().toString();
                final String fullName = FullName.getText().toString();
                final String mPass = Password.getText().toString();
                final String Cpass = ConfirmPassword.getText().toString();
                final String email = Email.getText().toString();
                final String username = UserNmae.getText().toString();
                if(TextUtils.isEmpty(teamName)){
                    Toast.makeText(SignUpActivity.this,"Team Name is Empty",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(fullName)){
                    Toast.makeText(SignUpActivity.this,"Full Name is Empty",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(username)){
                    Toast.makeText(SignUpActivity.this,"username is Empty",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(email)){
                    Toast.makeText(SignUpActivity.this,"Email is Empty",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(mPass)){
                    Toast.makeText(SignUpActivity.this,"Password is Empty",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(Cpass)){
                    Toast.makeText(SignUpActivity.this,"Confirm Password is Empty",Toast.LENGTH_SHORT).show();
                }
                else if(!mPass.equals(Cpass)){
                    Toast.makeText(SignUpActivity.this,"Password not matched",Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.d(TAG, "Database status" + mDatabase);
                    UserModel userModel = new UserModel();
                    userModel.setTeamName(teamName);
                    userModel.setUsername(username);
                    userModel.setEmail(email);
                    userModel.setFullName(fullName);
                    userModel.setPassword(mPass);
                    mDatabase = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference Teams = mDatabase.child("Teams");
                    String id = mDatabase.push().getKey();
                    //String params = dataSnapshot.getValue(UserModel.class);
                    mDatabase.child("Users").child(teamName + "_" + email.substring(0, email.indexOf("@")).toLowerCase()).setValue(userModel)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "On Success");
                                    Intent intent = new Intent(SignUpActivity.this, HomescreenActivity.class);
                                    mPreferenceHelper.putString("full_name",fullName);
                                    mPreferenceHelper.putString("e-mail",email);
                                    startActivity(intent);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "On Failure" + e.toString());
                                }
                            });
                    Teams teamsObj = new Teams();
                    teamsObj.setTeamName(teamName);
                    teamsObj.setId(id);
                    mDatabase.child("Teams").child(teamName.toLowerCase()).setValue(teamsObj)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "Teams Added");
                                    Intent intent1 = new Intent(SignUpActivity.this,HomescreenActivity.class);
                                    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent1);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "Teams Not Added");
                        }
                    });

                }
                }
            //}
        });
    }

    @Override
    protected void init() {

    }
}

