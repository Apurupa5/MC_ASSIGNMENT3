package com.iiitd.apurupa.mcassignment3.savedatademo;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


public class DeleteActivity extends AppCompatActivity {
    SQLiteDatabase dbInstance;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_st_acitvity);

        dbInstance = openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE, null);
        DeleteRecord();
    }
//Delete a Record in Database
    private void DeleteRecord() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Roll no of Student to Delete");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               String  mrollno=String.valueOf(input.getText());
                Toast.makeText(getApplicationContext(),"Ciev", Toast.LENGTH_LONG).show();
                dbInstance.execSQL("DELETE FROM studentWHERE rollno='"+mrollno+"'");
                Toast.makeText(getApplicationContext(),"Record is Deleted", Toast.LENGTH_LONG).show();

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
