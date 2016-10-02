package com.iiitd.apurupa.mcassignment3.savedatademo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListStAcitvity extends AppCompatActivity {


    ListView stlistview;
    SQLiteDatabase dbInstance;
    public static final String mypreference = "mypref";
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_st_acitvity);
        stlistview=(ListView)findViewById(R.id.StListView);
        dbInstance = openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE, null);
        checkSavingMode();
    }

    private void checkSavingMode() {
        Intent initialIntent= getIntent();
        String res=initialIntent.getStringExtra("SavingType");
        switch(res){
            case "Shared":retrieveSharedData();break;
            case "SQL":ShowSQLiteDBdata();break;
        }
    }
//Retrieving Shared Data
    private void retrieveSharedData() {
        SharedPreferences prefs = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        StringBuffer buffer = new StringBuffer();
        if( prefs.getString("Roll No","").equals("") && prefs.getString("Name","").equals("") &&prefs.getString("Course","").equals(""))
        {

            buffer.append("No Data Found");

        }

 else {
            buffer.append("Rollno: " + prefs.getString("Roll No", "") + "\n");
            buffer.append("Name:   " + prefs.getString("Name", "") + "\n");
            buffer.append("Course:  " + prefs.getString("Course", "") + "\n\n");
        }
        showDetails(buffer);
    }
//Alert Dialog to show the retrieved Details
    private void showDetails(StringBuffer buffer) {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Student Details");

        builder.setMessage(buffer);
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                        finish();
                    }
                });


        AlertDialog detailsDialog =  builder.create();
        detailsDialog.show();
    }
//Retrieving SQL data from Database
    private void ShowSQLiteDBdata() {

        cursor=dbInstance.rawQuery("SELECT * FROM Student", null);
        StringBuffer buffer=new StringBuffer();
        if (!(cursor.moveToFirst()) || cursor.getCount() == 0) {
            buffer.append("No Details Found");
        }
        if (cursor.moveToFirst()) {
            do {
                buffer.append("Rollno: "+cursor.getString(0)+"\n");
                buffer.append("Name:   "+cursor.getString(1)+"\n");
                buffer.append("Course:  "+cursor.getString(2)+"\n\n");
            } while (cursor.moveToNext());
        }

        showDetails(buffer);

    }


}

