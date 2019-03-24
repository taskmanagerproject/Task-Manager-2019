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
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nikhil.taskmanager.Constants.AppConstant;
import com.example.nikhil.taskmanager.Home.view.HomescreenActivity;
import com.example.nikhil.taskmanager.R;
import com.example.nikhil.taskmanager.SearchItemArrayAdapter;
import com.example.nikhil.taskmanager.base.view.BaseActivity;
import com.example.nikhil.taskmanager.model.Tasks;
import com.example.nikhil.taskmanager.model.Teams;
import com.example.nikhil.taskmanager.model.Users;
import com.example.nikhil.taskmanager.signup.view.SignUpActivity;
import com.example.nikhil.taskmanager.user.view.DatabaseHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TaskActivity extends BaseActivity {
    private static final String TAG = "Task Activity";
    String email = "",teamName;
    String assignMember;
    RadioGroup priority;
    private DatabaseReference mDatabase;
    TextInputEditText title,description;
    AutoCompleteTextView assignTo;
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
        DatabaseHelper helper = new DatabaseHelper(TaskActivity.this);
        ArrayList<Users> allNames = helper.getAllNames();
        Log.d(TAG,"Allnames are"+allNames);
        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(TaskActivity.this,
                R.layout.activity_autocomplete_layout,allNames);*/

        Log.d(TAG,"Helper.getData() "+helper.getData());
        assignTo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "View : " + view);
                ConstraintLayout parentLayout = (ConstraintLayout) view;
                TextView textView = (TextView) parentLayout.getChildAt(1);
                TextView emailTv = (TextView) parentLayout.getChildAt(2);
                assignMember = emailTv.getText().toString();
                String email = textView.getText().toString();
                Log.d(TAG,"Email in task activity is "+assignMember);
                assignTo.setText(email);
                //Log.d(TAG, "Child Count ; " + view.getC)
            }
        });
        SearchItemArrayAdapter adapter = new SearchItemArrayAdapter(this, R.layout.activity_autocomplete_layout,allNames);
        assignTo.setThreshold(0);
        assignTo.setAdapter(adapter);


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
        if(TextUtils.isEmpty(title.getText().toString())){
            Toast.makeText(TaskActivity.this,"Title is Empty!",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(description.getText().toString())){
            Toast.makeText(TaskActivity.this,"Description is Empty!",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(assignTo.getText().toString())){
            Toast.makeText(TaskActivity.this,"Please Assign a Task",Toast.LENGTH_LONG).show();
        }
        else{
            String time= String.valueOf(System.currentTimeMillis());
            Date d = new Date();
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
            tasks.setAssignTo(assignMember);
            tasks.setCreator(email);
            tasks.setDate_of_creator(time);
            tasks.setStatus("In Progress");
            mDatabase.child("Tasks").child(time+"_"+teamName).setValue(tasks)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("ds", "On Success");
                            Toast.makeText(getApplicationContext(),"Data success",Toast.LENGTH_LONG).show();
                            finish();
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                //NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
