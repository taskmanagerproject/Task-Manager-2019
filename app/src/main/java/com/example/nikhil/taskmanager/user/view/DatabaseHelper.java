package com.example.nikhil.taskmanager.user.view;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.nikhil.taskmanager.model.Tasks;
import com.example.nikhil.taskmanager.model.Users;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="NEW_DATABASE.db";
    private static final int DATABASE_VERSION=1;
    private static final String TABLE_NAME = "USER";
    private static final String TEAM_NAME = "TEAM_NAME";
    private static final String FULL_NAME = "FULL_NAME";;
    private static final String EMAIL = "EMAIL";
    private static final String  USERNAME = "USERNAME";
    private static final String PASSWORD = "PASSWORD";
    private static final String TASK_TABLE = "TASK";
    private static final String ASSIGN = "ASSIGN";
    private static final String CREATOR = "CREATOR";
    private static final String DATE_OF_CREATION = "DATE_OF_CREATION";
    private static final String DESCRIPTION = "DESCRIPTION";
    private static final String TASK_ID = "TASK_ID";
    private static final String TASK_PRIORITY = "TASK_PRIORITY";
    private static final String TITLE = "TITLE";
    private static final String TASK_STATUS = "TASK_STATUS";
    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query1 = "create table "+ TABLE_NAME + " ( " + FULL_NAME + " text,"+ TEAM_NAME + " text, " + EMAIL + " text UNIQUE NOT NULL," + USERNAME + " text," + PASSWORD + " text, UNIQUE(EMAIL,TEAM_NAME))";
        sqLiteDatabase.execSQL(query1);
        String query2 = "create table "+ TASK_TABLE + " ( " + ASSIGN    +  " text ,"+ CREATOR + " text, " +DATE_OF_CREATION +" text, "+ DESCRIPTION + " text, "+TASK_ID+ " text, "+TASK_PRIORITY+" text, "+TITLE+" text, "+ TASK_STATUS+ " text )";

        sqLiteDatabase.execSQL(query2);
    }
    /*private ArrayList<String> getData(){
        List<String> data = new ArrayList<>();
        String query = "SELECT * FROM USER";
        Cursor cursor = getReadableDatabase().rawQuery(query,null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                data.add(cursor.getString(cursor.getColumnIndex(FULL_NAME)));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return  data;
    }*/
    public void delete_task(){
        SQLiteDatabase writableDatabase=this.getWritableDatabase();
        writableDatabase.delete(TASK_TABLE,null,null);
        writableDatabase.close();
    }
    public void insert_task(Tasks model){
        Log.d(TAG,"Table progress generating....");
        SQLiteDatabase writableDatabase=this.getWritableDatabase();
        ContentValues contentvalues=new ContentValues();
        contentvalues.put(ASSIGN,model.getAssignTo());
        contentvalues.put(CREATOR,model.getCreator());
        contentvalues.put(DATE_OF_CREATION,model.getDate_of_creator());
        contentvalues.put(DESCRIPTION,model.getDescription());
        contentvalues.put(TASK_ID,model.getID());
        contentvalues.put(TASK_PRIORITY,model.getPriority());
        contentvalues.put(TITLE,model.getTitle());
        contentvalues.put(TASK_STATUS,model.getStatus());
        writableDatabase.insert(TASK_TABLE,null,contentvalues);
        writableDatabase.close();
        Log.d(TAG,"Table progress Created Successfully!!");
    }

    public void delete_user(){
        SQLiteDatabase writableDatabase=this.getWritableDatabase();
        writableDatabase.delete(TABLE_NAME,null,null);
        writableDatabase.close();
    }
    public void insert_user(Users model){

        SQLiteDatabase writableDatabase=this.getWritableDatabase();
        ContentValues contentvalues=new ContentValues();
        contentvalues.put(TEAM_NAME,model.getTeamName());
        contentvalues.put(FULL_NAME,model.getFullName());
        contentvalues.put(EMAIL,model.getEmail());
        contentvalues.put(USERNAME,model.getUsername());
        contentvalues.put(PASSWORD,model.getPassword());
        writableDatabase.insert(TABLE_NAME,null,contentvalues);
        writableDatabase.close();
    }
    public ArrayList<String> getAllNames(){
        ArrayList<String> allNames = new ArrayList<>();
        String query = "SELECT DISTINCT " +EMAIL+ " FROM "+TABLE_NAME;

        Cursor cursor = getReadableDatabase().rawQuery(query,null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                allNames.add(cursor.getString(0));
                cursor.moveToNext();
            }
        }
        cursor.close();
        Log.d(TAG,"Sqlite database names are "+allNames);
        return allNames;
    }
    public List<Users> getData(){
        //String query = "SELET * FROM "+TABLE_NAME+" WHERE "+FULL_NAME+ " LIKE %"+searchItem+"%";
        String query1 = " SELECT DISTINCT "+ EMAIL +" , "+ FULL_NAME +" FROM "+TABLE_NAME;
        return innerSelect(query1);
    }

    private List<Users> innerSelect(String query) {
        List<Users> allUsers = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery(query,null);
        Log.d(TAG,"Users Table is "+c.getColumnNames().toString());
        if(c.getCount()>0){
            c.moveToFirst();
            while (!c.isAfterLast()){
                Log.d(TAG,"Count are "+c.isLast());
                Users obj = new Users();
                obj.setFullName(c.getString(c.getColumnIndex(FULL_NAME)));
                obj.setEmail(c.getString(c.getColumnIndex(EMAIL)));
                Log.d(TAG,"Full Name are 1 "+obj.getFullName());
                //Log.d(TAG,"Value from database is "+c.getColumnIndex(FULL_NAME));
                c.moveToNext();
                Log.d(TAG,"Cursor check "+c.isAfterLast());
                allUsers.add(obj);
                Log.d(TAG,"Full Name are 2 "+obj.getFullName());
            }
            allUsers.size();
            Log.d(TAG,"Size of All Users list are "+allUsers.size());
            Log.d(TAG,"Full Name are 01 "+allUsers.get(0).getFullName());
            Log.d(TAG,"Full Name are 02 "+allUsers.get(1).getFullName());
            Log.d(TAG,"Full Name are 03 "+allUsers.get(2).getFullName());
            Log.d(TAG,"Full Name are 04 "+allUsers.get(3).getFullName());
        }
        c.close();
        return allUsers;
    }
    public List<Tasks> getAllTasks(){
        String taskQuery = "Select * FROM "+TASK_TABLE;
        return innerTaskSelect(taskQuery);
    }
    public List<Tasks> getMyTasks(String email){
        Log.d(TAG,"Email in database helper "+email);
        String taskQuery = "SELECT * FROM "+TASK_TABLE+" WHERE ASSIGN"+"='"+email+"'";
        Log.d(TAG,"Query is "+taskQuery);
        return innerTaskSelect(taskQuery);
    }

    private List<Tasks> innerTaskSelect(String taskQuery) {
        List<Tasks> data = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery(taskQuery,null);
        Log.d(TAG,"cursor count: "+c.getCount());
        if(c.getCount()>0){
            c.moveToFirst();
            while (!c.isAfterLast()){
                Log.d(TAG,"Count are "+c.isLast());
                Tasks obj = new Tasks();
                obj.setTitle(c.getString(c.getColumnIndex(TITLE)));
                obj.setDescription(c.getString(c.getColumnIndex(DESCRIPTION)));
                obj.setPriority(c.getString(c.getColumnIndex(TASK_PRIORITY)));
                obj.setAssignTo(c.getString(c.getColumnIndex(ASSIGN)));
                obj.setStatus(c.getString(c.getColumnIndex(TASK_STATUS)));
                obj.setID(c.getString(c.getColumnIndex(TASK_ID)));
                c.moveToNext();
                data.add(obj);
            }
        }
        c.close();
        return  data;
    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
