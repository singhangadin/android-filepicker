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

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.github.angads25.filechooserdialog.controller.DialogSelectionListener;
import com.github.angads25.filechooserdialog.model.DialogConfigs;
import com.github.angads25.filechooserdialog.model.DialogProperties;
import com.github.angads25.filechooserdialog.view.FileChooserDialog;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{   private FileChooserDialog dialog;
    private ArrayList<ListItem> listItem;
    private FileListAdapter mFileListAdapter;
    private ListView listView;
    private static final int REQUEST_READ_STORAGE = 112;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {   super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean hasPermission = false;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            hasPermission = (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
            if (!hasPermission)
            {   ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_STORAGE);
            }
        }
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
                {   properties.offset=new File(DialogConfigs.ROOT_MOUNT_DIR);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {   case REQUEST_READ_STORAGE: {
            if (grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getBaseContext(), "The app was not allowed to write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
            }
        }
        }

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
}