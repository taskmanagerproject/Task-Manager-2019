package com.example.nikhil.taskmanager.task.view;

import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.nikhil.taskmanager.Constants.AppConstant;
import com.example.nikhil.taskmanager.Home.view.HomescreenActivity;
import com.example.nikhil.taskmanager.R;
import com.example.nikhil.taskmanager.base.view.BaseActivity;
import com.example.nikhil.taskmanager.model.Tasks;
import com.example.nikhil.taskmanager.model.Teams;
import com.example.nikhil.taskmanager.signup.view.SignUpActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TaskActivity extends BaseActivity {
    RadioGroup priority;
    private DatabaseReference mDatabase;
    TextInputEditText title,description,assignTo;
    @BindView(R.id.create_task_btn)
    Button createTask;
    private android.support.v7.widget.Toolbar toolbar;
    ConstraintLayout mConstraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        title = findViewById(R.id.create_task_title);
        description = findViewById(R.id.create_task_description);
        assignTo = findViewById(R.id.create_task_assign);
        ButterKnife.bind(this);
        mConstraintLayout = findViewById(R.id.constraint_layout);
        toolbar = findViewById(R.id.createTaskToolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.arrow_back);
        priority = findViewById(R.id.priorityRadio);
        String value;
        priority.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int radioBtnId = radioGroup.getCheckedRadioButtonId();
                RadioButton rb = findViewById(i);
                AppConstant.BundleKey.Priority  = rb.getText().toString();
                Log.d("cznczx","Values are "+AppConstant.BundleKey.Priority);
            }
        });
    }

    @Override
    protected void init() {

    }

    @OnClick(R.id.create_task_btn)
    public void Onclick(){
        String time= String.valueOf(System.currentTimeMillis());
        Date d = new Date();
        String email = "",teamName;
        email = mPreferenceHelper.getString("e-mail","");
        teamName = mPreferenceHelper.getString("team_name","");
        Log.d("Task Activity","Value of email "+email);
        String date = DateFormat.format("MMMM d, yyyy ", d.getTime()).toString();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Tasks tasks = new Tasks();
        tasks.setID(time);
        tasks.setTitle(title.getText().toString());
        tasks.setDescription(description.getText().toString());
        tasks.setPriority(AppConstant.BundleKey.Priority);
        tasks.setAssignTo("Nikhil");
        tasks.setCreator(email);
        tasks.setDate_of_creator(time);
        mDatabase.child("Tasks").child(time+"_"+teamName).setValue(tasks)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("ds", "On Success");
                        Toast.makeText(getApplicationContext(),"Data success",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("", "On Failure" + e.toString());
                        Toast.makeText(getApplicationContext(),"Data fail",Toast.LENGTH_LONG).show();
                    }
                });
        Toast.makeText(this,"Clicked",Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
