package com.example.nikhil.taskmanager.team.view;

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
import com.example.nikhil.taskmanager.base.view.BaseActivity;
import com.example.nikhil.taskmanager.login.view.LoginActivity;
import com.example.nikhil.taskmanager.R;
import com.example.nikhil.taskmanager.signup.view.SignUpActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EnterTeamNameActivity extends BaseActivity {
    String RecievedTextFromIntent;
    Button NextBtn;
    EditText team_name;
    DatabaseReference mDatabase;
    private static final String TAG = "EnterTeamNameActivity";
    String valueFromDatabase,valueFromEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_team_name);
        final Intent intent = getIntent();
        team_name = findViewById(R.id.enterTeam);
        RecievedTextFromIntent = intent.getStringExtra("click");
        mDatabase =   FirebaseDatabase.getInstance().getReference();
        NextBtn = findViewById(R.id.NextBtn);
        NextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String teamName = team_name.getText().toString().trim().toLowerCase();
                mDatabase = FirebaseDatabase.getInstance().getReference();
                DatabaseReference TeamReference = mDatabase.child("Teams").child(teamName);

                switch (RecievedTextFromIntent){
                    case "create":
                        Log.d(TAG,"On Create");
                        TeamReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(TextUtils.isEmpty(teamName)){
                                    Toast.makeText(EnterTeamNameActivity.this,"Please enter a Team Name",MODE_PRIVATE).show();
                                }
                               else if (dataSnapshot.getValue() != null) {// team is already exist in database
                                    //Show Error Message
                                    Toast.makeText(EnterTeamNameActivity.this,"TeamExists",Toast.LENGTH_SHORT).show();
                                } else {
                                    // Start New Activity
                                    Intent createIntent = new Intent(EnterTeamNameActivity.this, SignUpActivity.class);
                                    createIntent.putExtra("click","Create");
                                    createIntent.putExtra(AppConstant.BundleKey.TEAM_NAME, teamName);
                                    startActivity(createIntent);
                                }

                                Log.d(TAG, "User Existence : " + dataSnapshot);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        break;
                    case "join":
                        TeamReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(TextUtils.isEmpty(teamName)){
                                    Toast.makeText(EnterTeamNameActivity.this,"Please enter a Team Name",MODE_PRIVATE).show();
                                }
                                else if(dataSnapshot.getValue() !=null){
                                    Intent joinIntent = new Intent(EnterTeamNameActivity.this, SignUpActivity.class);
                                    joinIntent.putExtra("click","Join");
                                    joinIntent.putExtra(AppConstant.BundleKey.TEAM_NAME,teamName);
                                    startActivity(joinIntent);
                                }
                                else{
                                        Toast.makeText(EnterTeamNameActivity.this,"Team Not created Yet!",Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        break;
                    case "signin":
                        TeamReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(TextUtils.isEmpty(teamName)){
                                    Toast.makeText(EnterTeamNameActivity.this,"Please enter a Team Name",MODE_PRIVATE).show();
                                }
                                else if(dataSnapshot.getValue() != null){
                                    Intent loginIntent = new Intent(EnterTeamNameActivity.this, LoginActivity.class);
                                    loginIntent.putExtra(AppConstant.BundleKey.TEAM_NAME, teamName);
                                    mPreferenceHelper.putString("team_name",teamName);
                                    startActivity(loginIntent);
                                } else {
                                    Toast.makeText(EnterTeamNameActivity.this,"Team Does not Exist",Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        break;

                }
            }
        });
    }

    @Override
    protected void init() {

    }
}
