package com.iiitd.apurupa.mcassignment3.savedatademo;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileActivity extends AppCompatActivity implements View.OnClickListener {


    private Button mreadinternalButton;
    private Button mreadexternalButton;
    private Button mwriteinternalButton;
    private Button mwritexternalButton;
    private Button mfilebackButton;
    private Button mreadextprivateButton;
    private Button mwriteextprivateButton;
    EditText mfiletext;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        init();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void init() {
        mreadinternalButton = (Button) findViewById(R.id.readInternalbutton);
        mreadexternalButton = (Button) findViewById(R.id.readExternalbutton);
        mwriteinternalButton = (Button) findViewById(R.id.writeInternalButton);
        mwritexternalButton = (Button) findViewById(R.id.writeExternalButton);
        mfilebackButton = (Button) findViewById(R.id.fileBackButton);
        mreadextprivateButton=(Button)findViewById(R.id.readextprivateButton);
        mwriteextprivateButton=(Button)findViewById(R.id.writeextprivateButton);
        mfiletext=(EditText)findViewById(R.id.fileEditText);

        mfilebackButton.setOnClickListener(this);

        mwriteinternalButton.setOnClickListener(this);
        mreadinternalButton.setOnClickListener(this);

        mreadexternalButton.setOnClickListener(this);
        mwritexternalButton.setOnClickListener(this);

        mreadextprivateButton.setOnClickListener(this);
        mwriteextprivateButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.writeInternalButton:
                writeInternal();
                break;
            case R.id.readInternalbutton:
                readInternal();
                break;
            case R.id.writeExternalButton:
                writeExternalpublic();
                break;
            case R.id.readExternalbutton:

                readExternalPublic();
                break;
            case R.id.writeextprivateButton: writeExternalPrivate();break;
            case R.id.readextprivateButton:readExternalPrivate();break;
            case R.id.fileBackButton:finish();break;
        }
    }

//Write Data to Internal Storage
    private void writeInternal() {
        if (mfiletext.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Please fill Data", Toast.LENGTH_LONG).show();
            return;
        }
        String filename = "SampleFileInternalDemo";
        String string =String.valueOf(mfiletext.getText());
        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            Toast.makeText(getApplicationContext(),"Data is Written to Internal Storage", Toast.LENGTH_LONG).show();
            outputStream.close();
            mfiletext.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//Read Data From Internal Storage
    private void readInternal() {
        mfiletext.setText("");
        String filename = "SampleFileInternalDemo";
        FileInputStream fileIn= null;
        try {
            fileIn = openFileInput(filename);
            InputStreamReader InputRead= new InputStreamReader(fileIn);

            BufferedReader bufferedReader = new BufferedReader(InputRead);
            StringBuilder s = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                s.append(line);
            }
          /*  char[] filebuffer= new char[100];
            String s="";
            int c;

            while ((c=InputRead.read(filebuffer))>0) {

                String res=String.copyValueOf(filebuffer,0,c);
                s +=res;
            }*/
            InputRead.close();
            mfiletext.setText(s);
        } catch (FileNotFoundException e) {
            Toast.makeText(getApplicationContext(),"File is Not Found", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

//Write Data to External Storage in public mode
    private void writeExternalpublic() {
        if (mfiletext.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Please fill Data", Toast.LENGTH_LONG).show();
            return;
        }

        if (isExternalStorageAvailableForWrite() || !isExternalStorageReadable()) {
            Toast.makeText(getApplicationContext(),"External Storage Available", Toast.LENGTH_LONG).show();

            String filename="SaveExternalPublicDemo1.txt";
            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
            File myDir = new File(path + "/FilesDemo");
            myDir.mkdirs();
            File file = new File(myDir, filename);
            if (!file.exists())
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }



            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                fos.write(mfiletext.getText().toString().getBytes());
                Toast.makeText(getApplicationContext(),"Data is Written to external public Storage", Toast.LENGTH_LONG).show();
                fos.close();
            } catch (FileNotFoundException e) {
                Toast.makeText(getApplicationContext(),"file not found", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(),"IO Exception", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }


        }


    }

    //Read a public File from External Storage
    private void readExternalPublic() {
        String filename="SaveExternalPublicDemo1.txt";
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()+"/FilesDemo";
        File file = new File(path, filename);

        if (!file.exists())
        {
            Toast.makeText(getApplicationContext(), "File Not Found", Toast.LENGTH_LONG).show();
            return;

        }
        FileInputStream fileIn= null;
        try {

            fileIn = new FileInputStream(file);
            InputStreamReader ir= new InputStreamReader(fileIn);
            BufferedReader bufferedReader = new BufferedReader(ir);
            StringBuilder s = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                s.append(line);
            }
            ir.close();
            mfiletext.setText(s);
        } catch (FileNotFoundException e) {
            Toast.makeText(getApplicationContext(), "File Not Found", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
//Wrtie a private File to External Storage
    private void writeExternalPrivate() {
        if (mfiletext.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Please fill Data", Toast.LENGTH_LONG).show();
            return;
        }
        String Filename="SampleDataExternalPrivate";
        //  File file=getExternalFilesDir(Filename);


        File file1=new File(getExternalFilesDir(null),Filename);
        Toast.makeText(getApplicationContext(),String.valueOf(file1) , Toast.LENGTH_LONG).show();
        if(!file1.exists()) {
            try {
                file1.createNewFile();
                Toast.makeText(getApplicationContext(),"File crated", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        File file = new File(Environment.getExternalStorageDirectory()
                , Filename);

        FileOutputStream fos = null,fos1=null;
        try {
            fos = new FileOutputStream(file);
            fos.write(mfiletext.getText().toString().getBytes());
            Toast.makeText(getApplicationContext(),"External File Stored in Private", Toast.LENGTH_LONG).show();
            fos.close();
           // fos1 = new FileOutputStream(file1);
           // BufferedWriter bf=new BufferedWriter(new FileWriter(file1,true));
           // bf.write(mfiletext.getText().toString());
          //fos1.write(mfiletext.getText().toString().getBytes());
            //Toast.makeText(getApplicationContext(),"External File Stored in Private", Toast.LENGTH_LONG).show();
            mfiletext.setText("");
            //fos1.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(getApplicationContext(),"file not found", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(),"io error", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }



    }
//Read a private file from external storage
    private void readExternalPrivate() {
        String Filename="SampleDataExternalPrivate";
        File file = new File(Environment.getExternalStorageDirectory()
                , Filename);

        FileInputStream fileIn= null;
        try {
            fileIn = new FileInputStream(file);
            InputStreamReader ir= new InputStreamReader(fileIn);

            BufferedReader bufferedReader = new BufferedReader(ir);
            StringBuilder s = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                s.append(line);
            }
            ir.close();
            mfiletext.setText(s);
        } catch (FileNotFoundException e) {
            Toast.makeText(getApplicationContext(),"File is not Found", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean isExternalStorageAvailableForWrite() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }





}
