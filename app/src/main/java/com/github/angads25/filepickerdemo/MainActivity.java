/*
 * Copyright (C) 2016 Angad Singh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.angads25.filepickerdemo;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{   private FilePickerDialog dialog;
    private ArrayList<ListItem> listItem;
    private FileListAdapter mFileListAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {   super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listItem=new ArrayList<>();
        listView = (ListView) findViewById(R.id.listView);
        mFileListAdapter=new FileListAdapter(listItem,MainActivity.this);
        listView.setAdapter(mFileListAdapter);

        //Create a DialogProperties object.
        final DialogProperties properties=new DialogProperties();

        //Instantiate FilePickerDialog with Context and DialogProperties.
        dialog=new FilePickerDialog(MainActivity.this,properties);
        RadioGroup modeRadio=(RadioGroup)findViewById(R.id.modeRadio);
        modeRadio.check(R.id.singleRadio);
        modeRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId)
                {   case R.id.singleRadio:  //Setting selection mode to single selection.
                                            properties.selection_mode= DialogConfigs.SINGLE_MODE;
                                            break;

                    case R.id.multiRadio:   //Setting selection mode to multiple selection.
                                            properties.selection_mode= DialogConfigs.MULTI_MODE;
                                            break;
                }
            }
        });
        RadioGroup typeRadio=(RadioGroup)findViewById(R.id.typeRadio);
        typeRadio.check(R.id.selFile);
        typeRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId)
                {   case R.id.selFile:  //Setting selection type to files.
                                        properties.selection_type=DialogConfigs.FILE_SELECT;
                                        break;

                    case R.id.selDir:   //Setting selection type to directories.
                                        properties.selection_type=DialogConfigs.DIR_SELECT;
                                        break;

                    case R.id.selfilenddir: //Setting selection type to files and directories.
                                            properties.selection_type=DialogConfigs.FILE_AND_DIR_SELECT;
                                            break;
                }
            }
        });
        final EditText extension=(EditText)findViewById(R.id.extensions);
        final EditText root=(EditText)findViewById(R.id.root);
        Button apply = (Button) findViewById(R.id.apply);
        Button showDialog = (Button) findViewById(R.id.show_dialog);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fextension=extension.getText().toString();
                if(fextension.length()>0) {
                    //Add extensions to be sorted from the EditText input to the array of String.
                    int commas = countCommas(fextension);

                    //Array representing extensions.
                    String[] exts = new String[commas + 1];
                    StringBuffer buff = new StringBuffer();
                    int i = 0;
                    for (int j = 0; j < fextension.length(); j++) {
                        if (fextension.charAt(j) == ',') {
                            exts[i] = buff.toString();
                            buff = new StringBuffer();
                            i++;
                        } else {
                            buff.append(fextension.charAt(j));
                        }
                    }
                    exts[i] = buff.toString();

                    //Set String Array of extensions.
                    properties.extensions=exts;
                }
                else
                {   //If EditText is empty, Initialise with null reference.
                    properties.extensions=null;
                }
                String foffset=root.getText().toString();
                if(foffset.length()>0||!foffset.equals(""))
                {   //Setting Parent Directory.
                    properties.root=new File(foffset);
                }
                else
                {   //Setting Parent Directory to Default SDCARD.
                    properties.root=new File(DialogConfigs.DEFAULT_DIR);
                }

                //Set new properties of dialog.
                dialog.setProperties(properties);
            }
        });

        showDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Showing dialog when Show Dialog button is clicked.
                dialog.show();
            }
        });

        //Method handle selected files.
        dialog.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(String[] files) {
                //files is the array of paths selected by the App User.
                listItem.clear();
                for(String path:files)
                {   File file=new File(path);
                    ListItem item=new ListItem();
                    item.setName(file.getName());
                    item.setPath(file.getAbsolutePath());
                    listItem.add(item);
                }
                mFileListAdapter=new FileListAdapter(listItem,MainActivity.this);
                listView.setAdapter(mFileListAdapter);
            }
        });
    }

    private int countCommas(String fextension) {
        int count=0;
        for(char ch:fextension.toCharArray())
        {   if(ch==',') {
                count++;
            }
        }
        return count;
    }

    //Add this method to show Dialog when the required permission has been granted to the app.
    @Override
    public void onRequestPermissionsResult(int requestCode,@NonNull String permissions[],@NonNull int[] grantResults) {
        switch (requestCode) {
            case FilePickerDialog.EXTERNAL_READ_PERMISSION_GRANT: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(dialog!=null)
                    {   //Show dialog if the read permission has been granted.
                        dialog.show();
                    }
                }
                else {
                    //Permission has not been granted. Notify the user.
                    Toast.makeText(MainActivity.this,"Permission is Required for getting list of files",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}