package com.iiitd.apurupa.mcassignment3.savedatademo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity implements View.OnClickListener {

    EditText mupstname;
    EditText mupstrollno;
    EditText mupstcourse;
    private Button mupdatebutton;
    private Button mupdatecancelbutton;
    SQLiteDatabase dbInstance;
    Cursor cursor;

    public static final String mypreference = "mypref";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        init();
    }
    private void init() {
        mupstname=(EditText)findViewById(R.id.upst_nametext);
        mupstcourse=(EditText)findViewById(R.id.upst_coursetext);
        mupstrollno=(EditText)findViewById(R.id.upst_rolltext);
        mupdatebutton=(Button)findViewById(R.id.updatebutton);
        mupdatecancelbutton=(Button)findViewById(R.id.updatecancelbutton);

        mupdatebutton.setOnClickListener( this);
        mupdatecancelbutton.setOnClickListener(this);
        dbInstance = openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE, null);

    }

    @Override
    public void onClick(View view) {
        Intent initialIntent= getIntent();
        String res=initialIntent.getStringExtra("SavingType");
        switch(view.getId())
        {
            case R.id.updatebutton:updatedetails(res);break;
            case R.id.updatecancelbutton: finish();break;
        }
    }
//Method to get all Values from text Fields
    private void updatedetails(String mode) {
        String sname= String.valueOf(mupstname.getText());
        String scourse=String.valueOf(mupstcourse.getText());
        String sroll=String.valueOf(mupstrollno.getText());
        if(sname.equals("") ||scourse.equals("")||sroll.equals("")){
            Toast.makeText(getApplicationContext(),"Please fill all fields", Toast.LENGTH_LONG).show();
            return;
        }
        switch(mode){
            case "Shared":updateInShared(sname,scourse,sroll);break;
            case "SQL":updateInSQL(sname,scourse,sroll);break;
        }

    }

    //Update a Record in SQL
    private void updateInSQL(String sname, String scourse, String sroll) {
        cursor = dbInstance.rawQuery("SELECT * FROM Student where rollno = ?", new String[]{sroll.trim()});
        if (cursor.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "ID Not found", Toast.LENGTH_LONG).show();
        } else {
            dbInstance.execSQL("UPDATE student SET name='" + sname + "',course='" + scourse +
                    "' WHERE rollno='" + sroll.trim() + "'");
            Toast.makeText(getApplicationContext(), "Record is Updated", Toast.LENGTH_LONG).show();
            finish();

        }
    }
//Update Data in Shared PReferences
    private void updateInShared(String sname, String scourse, String sroll) {
        SharedPreferences preferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        //SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edt = preferences.edit();
        edt.putString("Name","sname");
        edt.putString("Roll No",sroll);
        edt.putString("Course",scourse);
        edt.apply();
        edt.commit();
        Toast.makeText(getApplicationContext(),"Shared Preferences Saved", Toast.LENGTH_LONG).show();
        finish();

    }
}
