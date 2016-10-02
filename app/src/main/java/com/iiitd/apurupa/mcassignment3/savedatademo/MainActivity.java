package com.iiitd.apurupa.mcassignment3.savedatademo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mshareButton;
    private Button mfileButton;
    private Button msqlButton;

    @Override
     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mshareButton=(Button)findViewById(R.id.sharedbuttton);
        mfileButton=(Button)findViewById(R.id.filebutton);
        msqlButton=(Button)findViewById(R.id.sqlbutton);
        mshareButton.setOnClickListener(this);
        mfileButton.setOnClickListener(this);
        msqlButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, SQLActivity.class);
        switch(view.getId())
        {
            case R.id.sharedbuttton:intent.putExtra("SavingType","Shared");
                                      startActivity(intent);
                                        break;
            case R.id.filebutton: Intent intent1 = new Intent(MainActivity.this, FileActivity.class);startActivity(intent1);break;
            case R.id.sqlbutton:
                intent.putExtra("SavingType","SQL");
                startActivity(intent);
                break;
        }

    }
}
