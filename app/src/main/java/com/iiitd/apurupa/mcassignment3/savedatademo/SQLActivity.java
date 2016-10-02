package com.iiitd.apurupa.mcassignment3.savedatademo;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.auth.api.credentials.internal.DeleteRequest;
import com.google.android.gms.common.api.GoogleApiClient;

import static android.widget.Toast.makeText;
//Activity Showing All Common Operations
public class SQLActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mcreateButton;
    private Button mreadButton;
    private Button mupdateButton;
    private Button mdeleteButton;
    private Button msqlbackButton;
    private String mrollno="";
    Cursor cursor;

    public static final String mypreference = "mypref";
    private SQLiteDatabase dbInstance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql);
        init();

    }

    private void init() {
        mcreateButton = (Button) findViewById(R.id.create_stButton);
        mreadButton = (Button) findViewById(R.id.read_stButton);
        mdeleteButton = (Button) findViewById(R.id.delete_stButton);
        mupdateButton = (Button) findViewById(R.id.update_stButton);
        msqlbackButton=(Button)findViewById(R.id.sqlbackButton);
        mcreateButton.setOnClickListener(this);
        mreadButton.setOnClickListener(this);
        mupdateButton.setOnClickListener(this);
        mdeleteButton.setOnClickListener(this);
        msqlbackButton.setOnClickListener(this);
        dbInstance = openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE, null);
    }

    @Override
    public void onClick(View view) {
        Intent initialIntent= getIntent();
        String res=initialIntent.getStringExtra("SavingType");
        switch (view.getId()) {
            case R.id.create_stButton:

                Intent intent = new Intent(SQLActivity.this, CreateStActivity.class);
                intent.putExtra("SavingType",res);
                startActivity(intent);
                break;
            case R.id.read_stButton:
                Intent intent1 = new Intent(SQLActivity.this, ListStAcitvity.class);
                intent1.putExtra("SavingType",res);
                startActivity(intent1);

                break;
            case R.id.update_stButton:
                Intent intent2=new Intent(SQLActivity.this,UpdateActivity.class);
                intent2.putExtra("SavingType",res);
                startActivity(intent2);

                break;
            case R.id.delete_stButton:
                if(res.equals("Shared"))
                {
                    deleteSharedData();
                    break;
                }
                 Intent intent3=new Intent(SQLActivity.this, DeletestActivity.class);
                intent3.putExtra("SavingType",res);
                startActivity(intent3);

                break;
            case R.id.sqlbackButton:
                 finish();
                break;
        }
    }
//Method to Delete Shared Preferences
    private void deleteSharedData() {
        SharedPreferences preferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor edt = preferences.edit();
        if( preferences.getString("Roll No","").equals("") &&  preferences.getString("Name","").equals("") && preferences.getString("Course","").equals(""))
        {
            Toast.makeText(getApplicationContext(),"No Data Available to Delete", Toast.LENGTH_LONG).show();


        }
else {
            edt.remove("Name");
            edt.remove("Roll No");
            edt.remove("Course");

            edt.apply();
            edt.commit();
            Toast.makeText(getApplicationContext(), "Shared Preferences Deleted Sucessfully", Toast.LENGTH_LONG).show();
        }
    }

    private void readrollno() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Roll no of Student to Delete");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              mrollno=String.valueOf(input.getText());
                Toast.makeText(getApplicationContext(),"Ciev", Toast.LENGTH_LONG).show();
                dbInstance.execSQL("DELETE FROM studentWHERE rollno='"+mrollno+"'");
                Toast.makeText(getApplicationContext(),"Record is Deleted", Toast.LENGTH_LONG).show();
                //checkrollnoinDB(mrollno);
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

    private void checkrollnoinDB(String mrollno) {
        Toast.makeText(getApplicationContext(),"Ciev", Toast.LENGTH_LONG).show();
        dbInstance.execSQL("DELETE FROM studentWHERE rollno='"+mrollno+"'");
        Toast.makeText(getApplicationContext(),"Record is Deleted", Toast.LENGTH_LONG).show();
       /*cursor= dbInstance.rawQuery("SELECT * FROM Student where rollno = ?",new String[]{mrollno});
        if (!(cursor.moveToFirst()) || cursor.getCount() ==0){
            Toast.makeText(getApplicationContext(),"ID Not found", Toast.LENGTH_LONG).show();
        }
      else{
            Toast.makeText(getApplicationContext(),cursor.getString(1), Toast.LENGTH_LONG).show();
            dbInstance.execSQL("DELETE FROM studentWHERE rollno='"+mrollno+"'");
            Toast.makeText(getApplicationContext(),"Record is Deleted", Toast.LENGTH_LONG).show();

        }*/
    }


}
