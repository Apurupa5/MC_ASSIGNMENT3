package com.iiitd.apurupa.mcassignment3.savedatademo;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DeletestActivity extends AppCompatActivity implements View.OnClickListener {

    SQLiteDatabase dbInstance;
    Cursor cursor;
    EditText mdtrollno;
    private Button deleteButton;
    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deletest);
        dbInstance = openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE, null);
        init();
    }

    private void init() {

        mdtrollno = (EditText) findViewById(R.id.delete_strollno);
        deleteButton = (Button) findViewById(R.id.deletebutton);
        cancelButton = (Button) findViewById(R.id.deletecancelbutton);
        deleteButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.deletebutton:

                deleteRecord();
                break;
            case R.id.deletecancelbutton:
                finish();
                break;

        }
    }

    private void deleteRecord() {
        String sroll = String.valueOf(mdtrollno.getText());
        if (sroll.equals("")) {
            Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_LONG).show();
            return;
        }
        sroll=sroll.trim();
        cursor = dbInstance.rawQuery("SELECT * FROM Student where rollno = ?", new String[]{sroll});
        if (!(cursor.moveToFirst()) || cursor.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "ID Not found", Toast.LENGTH_LONG).show();
        } else {

            dbInstance.delete("student", " rollno =?", new String[]{sroll});
            Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_LONG).show();
            finish();
        }

    }
}