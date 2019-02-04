package com.example.nikhil.taskmanager.user.view;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.nikhil.taskmanager.model.Users;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="sample.db";
    private static final int DATABASE_VERSION=1;
    private static final String TABLE_NAME = "USER";
    private static final String TEAM_NAME = "TEAM_NAME";
    private static final String FULL_NAME = "FULL_NAME";
    private static final String EMAIL = "EMAIL";
    private static final String  USERNAME = "USERNAME";
    private static final String PASSWORD = "PASSWORD";
    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query="create table "+ TABLE_NAME +" ( " + FULL_NAME + " text,"+ TEAM_NAME + " text, " + EMAIL + " text," + USERNAME + " text," + PASSWORD + " text)";
        sqLiteDatabase.execSQL(query);
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
    }
    public ArrayList<String> getAllNames(){
        ArrayList<String> allNames = new ArrayList<String>();
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
        return allNames;
    }
    public ArrayList<Users> getData(){
        //String query = "SELET * FROM "+TABLE_NAME+" WHERE "+FULL_NAME+ " LIKE %"+searchItem+"%";
        String query1 = "SELECT " +EMAIL+ "FROM"+TABLE_NAME;
        return innerSelect(query1);
    }

    private ArrayList<Users> innerSelect(String query) {
        Users obj = new Users();
        ArrayList<Users> allUsers = new ArrayList<Users>();
        Cursor c = getReadableDatabase().rawQuery(query,null);
        if(c.getCount()>0){
            c.moveToFirst();
            while (!c.isAfterLast()){
                obj.setFullName(c.getString(c.getColumnIndex(FULL_NAME)));
                Log.d(TAG,"Value from database is "+c.getColumnIndex(FULL_NAME));
                c.moveToNext();
                allUsers.add(obj);
            }

        }
        c.close();
        return allUsers;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
