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

package com.github.angads25.filechooserdemo;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.github.angads25.filechooser.controller.DialogSelectionListener;
import com.github.angads25.filechooser.model.DialogConfigs;
import com.github.angads25.filechooser.model.DialogProperties;
import com.github.angads25.filechooser.view.FileChooserDialog;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{   private FileChooserDialog dialog;
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
        final DialogProperties properties=new DialogProperties();
        dialog=new FileChooserDialog(MainActivity.this,properties);
        RadioGroup modeRadio=(RadioGroup)findViewById(R.id.modeRadio);
        modeRadio.check(R.id.singleRadio);
        modeRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId)
                {   case R.id.singleRadio:  properties.selection_mode= DialogConfigs.SINGLE_MODE;
                                            break;

                    case R.id.multiRadio:   properties.selection_mode= DialogConfigs.MULTI_MODE;
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
                {   case R.id.selFile:  properties.selection_type=DialogConfigs.FILE_SELECT;
                                        break;

                    case R.id.selDir:   properties.selection_type=DialogConfigs.DIR_SELECT;
                                        break;

                    case R.id.selfilenddir: properties.selection_type=DialogConfigs.FILE_AND_DIR_SELECT;
                                            break;
                }
            }
        });
        final EditText extension=(EditText)findViewById(R.id.extensions);
        final EditText offset=(EditText)findViewById(R.id.offset);
        Button apply = (Button) findViewById(R.id.apply);
        Button showDialog = (Button) findViewById(R.id.show_dialog);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fextension=extension.getText().toString();
                if(fextension.length()>0) {
                    int commas = countCommas(fextension);
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
                    properties.extensions=exts;
                    for (String ext:exts)
                        Log.e("TAG",ext);
                }
                else
                {   properties.extensions=null;
                }
                String foffset=offset.getText().toString();
                if(foffset.length()>0||!foffset.equals(""))
                {   properties.offset=new File(foffset);
                }
                else
                {   properties.offset=new File(DialogConfigs.DEFAULT_DIR);
                }
                dialog.setProperties(properties);
            }
        });
        showDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
        dialog.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onFilesSelected(File[] files) {
                listItem.clear();
                for(File file:files)
                {   ListItem item=new ListItem();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case FileChooserDialog.EXTERNAL_READ_PERMISSION_GRANT: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(dialog!=null)
                    {   dialog.show();
                    }
                }
                else {
                    Toast.makeText(MainActivity.this,"Permission is Required for getting list of files",Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}