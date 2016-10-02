package com.iiitd.apurupa.mcassignment3.savedatademo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateStActivity extends AppCompatActivity implements View.OnClickListener {

    EditText mstname;
    EditText mstrollno;
    EditText mstcourse;
    private Button msavebutton;
    private Button mcancelbutton;
    public static final String mypreference = "mypref";
    SQLiteDatabase dbInstance;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_st);
        init();

    }

    private void init() {
        mstname = (EditText) findViewById(R.id.st_nametext);
        mstcourse = (EditText) findViewById(R.id.st_coursetext);
        mstrollno = (EditText) findViewById(R.id.st_rolltext);
        msavebutton = (Button) findViewById(R.id.createsavebutton);
        mcancelbutton = (Button) findViewById(R.id.createcancelbutton);

        msavebutton.setOnClickListener(this);
        mcancelbutton.setOnClickListener(this);
        dbInstance = openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE, null);
        dbInstance.execSQL("CREATE TABLE IF NOT EXISTS Student(rollno VARCHAR,name VARCHAR,course VARCHAR);");
    }

    @Override
    public void onClick(View view) {
        Intent initialIntent = getIntent();
        String res = initialIntent.getStringExtra("SavingType");
        switch (view.getId()) {
            case R.id.createsavebutton:
                savedetails(res);

                break;
            case R.id.createcancelbutton:
                finish();
                break;
        }
    }

    private void savedetails(String mode) {

        String sname = String.valueOf(mstname.getText());
        String scourse = String.valueOf(mstcourse.getText());
        String sroll = String.valueOf(mstrollno.getText());
        if (sname.equals("") || scourse.equals("") || sroll.equals("")) {
            Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_LONG).show();
            return;
        }

        switch (mode) {
            case "Shared":
                saveAsShared(sname, scourse, sroll);
                break;
            case "SQL":
                saveInSQL(sname, scourse, sroll);
                break;
        }

    }

    //Save Data as Shared Preferences
    private void saveAsShared(String sname, String scourse, String sroll) {
        SharedPreferences preferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor edt = preferences.edit();
        edt.putString("Name", "sname");
        edt.putString("Roll No", sroll);
        edt.putString("Course", scourse);
        edt.apply();
        edt.commit();
        Toast.makeText(getApplicationContext(), "Shared Preferences Saved", Toast.LENGTH_LONG).show();
        finish();

    }

    //Save Data in SQLite Database
    private void saveInSQL(String sname, String scourse, String sroll) {
        cursor = dbInstance.rawQuery("SELECT * FROM Student where rollno = ?", new String[]{sroll});
        if (!(!(cursor.moveToFirst()) || cursor.getCount() == 0)) {
            Toast.makeText(getApplicationContext(), "Give a Unique Roll no", Toast.LENGTH_LONG).show();
            mstrollno.setText("");
            return;
        } else {
            String query = "INSERT INTO Student(rollno,name,course) VALUES('" + sroll + "', '" + sname + "', '" + scourse + "');";
            dbInstance.execSQL(query);
            Toast.makeText(getApplicationContext(), "Saved Successfully", Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
