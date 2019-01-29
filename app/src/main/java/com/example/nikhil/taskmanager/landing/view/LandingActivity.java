package com.example.nikhil.taskmanager.landing.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.nikhil.taskmanager.base.view.BaseActivity;
import com.example.nikhil.taskmanager.R;
import com.example.nikhil.taskmanager.team.view.EnterTeamNameActivity;
import com.example.nikhil.taskmanager.util.PreferenceHelper;

public class LandingActivity extends BaseActivity {

    Button signIn,createTeam,joinTeam;
    PreferenceHelper preferenceHelper;
    Intent intent1,intent2,intent3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_landing);
        signIn = findViewById(R.id.login_button_landing);
        createTeam = findViewById(R.id.create_team_button);
        joinTeam = findViewById(R.id.joinTeam_button_landing);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //preferenceHelper.putBoolean("SignIn",false);
                intent3 = new Intent(LandingActivity.this, EnterTeamNameActivity.class);
                intent3.putExtra("click","signin");
                startActivity(intent3);
            }
        });
        joinTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //preferenceHelper.putBoolean("JoinTeam",false);
                intent2 = new Intent(LandingActivity.this, EnterTeamNameActivity.class);
                intent2.putExtra("click","join");
                startActivity(intent2);
            }
        });
        createTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // preferenceHelper.putBoolean("CreateTeam",false);
                intent1 = new Intent(LandingActivity.this, EnterTeamNameActivity.class);
                intent1.putExtra("click","create");
                startActivity(intent1);
            }
        });
    }
    public void init(){

    }



}
