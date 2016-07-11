package com.github.angads25.filechooserdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.angads25.filechooserdialog.controller.DialogSelectionListener;
import com.github.angads25.filechooserdialog.model.DialogConfigs;
import com.github.angads25.filechooserdialog.model.DialogProperties;
import com.github.angads25.filechooserdialog.view.FileChooserDialog;

import java.io.File;

public class MainActivity extends AppCompatActivity
{   private FileChooserDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {   super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DialogProperties properties=new DialogProperties();
        properties.extensions=new String[]{"png","jpg","zip"};
        properties.offset= new File("/mnt/sdcard");
        properties.selection_type= DialogConfigs.FILE_AND_DIR_SELECT;
        properties.selection_mode=DialogConfigs.MULTI_MODE;

        dialog=new FileChooserDialog(MainActivity.this,properties);
        Button button = (Button) findViewById(R.id.file_picker);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                dialog.setCancelable(true);
            }
        });
        dialog.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onFilesSelected(File[] files) {
                for(File file:files)
                {   Log.e("TAG","GOT:"+file.getName());
                }
            }
        });
    }
}