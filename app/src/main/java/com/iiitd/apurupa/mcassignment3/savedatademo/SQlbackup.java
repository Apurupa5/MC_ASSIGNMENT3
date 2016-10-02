package com.iiitd.apurupa.mcassignment3.savedatademo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SQlbackup extends AppCompatActivity implements View.OnClickListener {

    private Button mcreateButton;
    private Button mreadButton;
    private Button mupdateButton;
    private Button mdeleteButton;
    private Button msqlbackButton;
    private String mrollno="";
    Cursor cursor;

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
       /* switch (view.getId()) {
            case R.id.create_stButton:

                Intent intent = new Intent(SQLActivity.this, CreateStActivity.class);
                startActivity(intent);
                // showcreatepopup(context);
                break;
            case R.id.read_stButton:
                Intent intent1 = new Intent(SQLActivity.this, ListStAcitvity.class);
                startActivity(intent1);

                break;
            case R.id.update_stButton:
                Intent intent2=new Intent(SQLActivity.this,UpdateActivity.class);
                startActivity(intent2);
                //readrollno();
                break;
            case R.id.delete_stButton:
                Intent intent3=new Intent(SQLActivity.this, DeletestActivity.class);
                startActivity(intent3);
                // readrollno();
                break;
            case R.id.sqlbackButton:
                //Intent intent1 = new Intent(SQLActivity.this, MainActivity.class);
                //startActivity(intent1);
                finish();
                break;
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

   /* private void showStudentData() {

        cursor=dbInstance.rawQuery("SELECT * FROM Student", null);
        StringBuffer buffer=new StringBuffer();

        if (cursor.moveToFirst()) {
            do {
                buffer.append("Rollno: "+cursor.getString(0)+"\n");
                buffer.append("Name:   "+cursor.getString(1)+"\n");
                buffer.append("Marks:  "+cursor.getString(2)+"\n\n");
            } while (cursor.moveToNext());
        }
 String TAG="SQLActivity";
  Log.d(TAG,buffer.toString());


        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Student Details");
        builder.setMessage(buffer);
        builder.show();*/
      /*  AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Student Details");

        builder.setMessage(buffer);
        builder.setPositiveButton("START OVER",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                       dialog.cancel();
                    }
                });


        AlertDialog detailsDialog =  builder.create();
        detailsDialog.show();
*/
        /*AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Student Details");
        builder.setMessage(buffer);
        builder.setPositiveButton("OK",

                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();

                    }

                }).show();*/


/*
    private void showcreatepopup(Context context) {


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View createStudentView = inflater.inflate(R.layout.popup_create, null, false);
        final EditText mStudentName = (EditText) createStudentView.findViewById(R.id.st_nametext);
        final EditText mStudentRollno = (EditText) createStudentView.findViewById(R.id.st_rolltext);
        final EditText mStudentCourse = (EditText) createStudentView.findViewById(R.id.st_coursetext);
        Button msaveButton = (Button) createStudentView.findViewById(R.id.createsavebutton);
        Button mcancelButton = (Button) createStudentView.findViewById(R.id.createcancelbutton);
        msaveButton.setOnClickListener(this);
        mcancelButton.setOnClickListener(this);

        new AlertDialog.Builder(context)
                .setView(createStudentView)
                .setTitle("Create Student")
                .setPositiveButton("BACK",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();

                            }

                        }).show();

        msaveButton.setOnClickListener(new SaveClick(mStudentName,mStudentCourse,mStudentRollno));


    }


    private class SaveClick(EditText sname,EditText course,EditText srollno) implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int duration = Toast.LENGTH_SHORT;
            final String TAG = "SQLActivity";
            String stname=
            Log.v(TAG,"SAved");

        }

    }
*/
}