package com.github.angads25.filechooserdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.github.angads25.filechooserdialog.FileChooserDialog;

public class MainActivity extends AppCompatActivity
{   @Override
    protected void onCreate(Bundle savedInstanceState)
    {   super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.file_picker);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    FileChooserDialog dialog=new FileChooserDialog(MainActivity.this,"exe");
                    dialog.show();
                    dialog.setCancelable(true);
            }
        });
    }
}