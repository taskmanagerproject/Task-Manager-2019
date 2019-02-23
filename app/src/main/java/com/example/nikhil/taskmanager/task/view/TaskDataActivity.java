package com.example.nikhil.taskmanager.task.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toolbar;

import com.example.nikhil.taskmanager.R;
import com.example.nikhil.taskmanager.base.view.BaseActivity;
import com.example.nikhil.taskmanager.model.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TaskDataActivity extends BaseActivity {

    android.support.v7.widget.Toolbar toolbar;
    DatabaseReference mDatabase,TasksDatabaseValue;
    Spinner taskSpinner;
    TextInputEditText taskTitle,taskDescription,taskStatus,taskAssignee,taskReporter,taskCreatedDate;
    RadioGroup taskPriority;
    Context context = this;
    private static final String TAG = "TaskDataActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_data);
        taskTitle = findViewById(R.id.edit_text_task_title);
        taskDescription = findViewById(R.id.edit_text_task_description);
        //taskStatus = findViewById(R.id.edit_text_task_status);
        taskAssignee  = findViewById(R.id.edit_text_task_assignee);
        taskReporter = findViewById(R.id.edit_text_task_reporter);
        taskPriority = findViewById(R.id.taskDataPriority);
        //taskCreatedDate  = findViewById(R.id.edit_text_task_created_date);
        taskSpinner = findViewById(R.id.taskStatusSpinner);
        Intent fromAllTask = getIntent();
        final String ID  = fromAllTask.getStringExtra("TASK_ID");
        Log.d(TAG,"TAsk Data ID is " + ID);
        toolbar = findViewById(R.id.taskDataToolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.arrow_back);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        TasksDatabaseValue = mDatabase.child("Tasks");
        TasksDatabaseValue.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG,"On data changed "+dataSnapshot);
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    String key = dataSnapshot1.getKey();
                    String databaseID = key.substring(0,key.indexOf("_"));
                    Log.d(TAG,"Database ID "+ID.equals(databaseID));
                    if(ID.equals(databaseID)){
                        final Tasks tasks = dataSnapshot1.getValue(Tasks.class);
                        taskTitle.setText(tasks.getTitle());
                        taskDescription.setText(tasks.getDescription());
                        //taskStatus.setText(tasks.getStatus());
                        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,R.array.spinnerItems,android.R.layout.simple_spinner_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        taskSpinner.setAdapter(adapter);
                        if (tasks.getStatus() != null){
                            int spinpos = adapter.getPosition(tasks.getStatus());
                            taskSpinner.setSelection(spinpos);
                        }


                        /*taskSpinner.getChildCount();
                        for(int i=0; i<taskSpinner.getChildCount();i++){
                            String spin = taskSpinner.getSelectedItem().toString();
                        }*/
                        taskAssignee.setText(tasks.getAssignTo());
                        taskReporter.setText(tasks.getCreator());
                        for(int id=0; id < taskPriority.getChildCount(); id++){
                            RadioButton btn = (RadioButton) taskPriority.getChildAt(id);
                            Log.d(TAG,"radio Buttons are "+btn.getText());
                            //int ID = btn.getId();
                            if(btn.getText().equals(tasks.getPriority())){
                                btn.setChecked(true);
                                //taskPriority.check(ID);
                            }
                        }
                        //==taskCreatedDate.setText(tasks.getDate_of_creator());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void init() {

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
