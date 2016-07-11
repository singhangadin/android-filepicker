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

package com.github.angads25.filechooserdialog.view;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.angads25.filechooserdialog.controller.NotifyItemChecked;
import com.github.angads25.filechooserdialog.model.DialogConfigs;
import com.github.angads25.filechooserdialog.model.adapters.FileListAdapter;
import com.github.angads25.filechooserdialog.R;
import com.github.angads25.filechooserdialog.controller.DialogSelectionListener;
import com.github.angads25.filechooserdialog.model.DialogProperties;
import com.github.angads25.filechooserdialog.model.FileListItem;
import com.github.angads25.filechooserdialog.model.MarkedItemList;
import com.github.angads25.filechooserdialog.utils.ExtensionFilter;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

/**<p>
 * Created by Angad Singh on 09-07-2016.
 * </p>
 */

public class FileChooserDialog extends Dialog implements AdapterView.OnItemClickListener
{   private static final String TAG = FileChooserDialog.class.getName();
    private Context context;
    private ListView listView;
    private TextView dname,dir_path;
    private DialogProperties properties;
    private DialogSelectionListener callbacks;
    private ArrayList<FileListItem> internalList;
    private ExtensionFilter filter;
    private FileListAdapter mFileListAdapter;
    private Button select;

    public FileChooserDialog(Context context,DialogProperties properties)
    {   super(context);
        this.context=context;
        this.properties=properties;
        filter=new ExtensionFilter(properties);
        internalList=new ArrayList<>();
    }

    public FileChooserDialog(Context context, int themeResId, DialogProperties properties) {
        super(context, themeResId);
        this.properties = properties;
        filter=new ExtensionFilter(properties);
        internalList=new ArrayList<>();
    }

    public FileChooserDialog(Context context, boolean cancelable, OnCancelListener cancelListener, DialogProperties properties) {
        super(context, cancelable, cancelListener);
        this.properties = properties;
        filter=new ExtensionFilter(properties);
        internalList=new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {   super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_main);
        listView=(ListView)findViewById(R.id.fileList);
        select = (Button) findViewById(R.id.select);
        Button cancel = (Button) findViewById(R.id.cancel);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String paths[]=MarkedItemList.getSelectedPaths();
                File[] files=new File[paths.length];
                for (int i=0;i<paths.length;i++)
                {   files[i]=new File(paths[i]);
                }
                callbacks.onFilesSelected(files);
                dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });
        mFileListAdapter=new FileListAdapter(internalList, context,properties);
        mFileListAdapter.setNotifyItemChecked(new NotifyItemChecked() {
            @Override
            public void notifyCheckBoxIsClicked() {
                int size=MarkedItemList.getFileCount();
                if(size==0)
                {   select.setText("Select");
                }
                else
                {   select.setText("Select("+size+")");
                }
                if(properties.selection_mode==DialogConfigs.SINGLE_MODE)
                {   mFileListAdapter.notifyDataSetChanged();
                }
            }
        });
        listView.setAdapter(mFileListAdapter);
        Log.e(TAG,"Dialog Created");
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!checkWritePermissions())
        {   Toast.makeText(context,"Failed to access files, Do you have Permissions?", Toast.LENGTH_SHORT).show();
        }
        else
        {   dname=(TextView)findViewById(R.id.dname);
            dir_path=(TextView)findViewById(R.id.dir_path);
            File inter = properties.offset;
            dname.setText(inter.getName());
            dir_path.setText(inter.getAbsolutePath());
            for (File name : inter.listFiles(filter))
            {   if(inter.getName().equals("mnt")) {
                    if (name.getName().startsWith("sdcard")) {
                        FileListItem item = new FileListItem();
                        item.setFilename(name.getName());
                        item.setDirectory(name.isDirectory());
                        item.setLocation(name.getAbsolutePath());
                        item.setTime(name.lastModified());
                        internalList.add(item);
                    }
                }
                else{
                    FileListItem item = new FileListItem();
                    item.setFilename(name.getName());
                    item.setDirectory(name.isDirectory());
                    item.setLocation(name.getAbsolutePath());
                    item.setTime(name.lastModified());
                    internalList.add(item);
                }
            }
            Collections.sort(internalList);
            mFileListAdapter.notifyDataSetChanged();
            listView.setOnItemClickListener(this);
        }
        Log.e(TAG,"Dialog Started");
    }

    @Override
    protected void onStop() {
        Log.e(TAG,"Dialog Stopped");
        super.onStop();
    }

    private boolean checkWritePermissions()
    {   String permission1 = "android.permission.WRITE_EXTERNAL_STORAGE";
        String permission2 = "android.permission.READ_EXTERNAL_STORAGE";
        int res1 = getContext().checkCallingOrSelfPermission(permission1);
        int res2 = getContext().checkCallingOrSelfPermission(permission2);
        return (res1 == PackageManager.PERMISSION_GRANTED&&res2==PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        {   FileListItem fitem=internalList.get(i);
            if(fitem.isDirectory())
            {   File inter = new File(fitem.getLocation());
                dname.setText(inter.getName());
                dir_path.setText(inter.getAbsolutePath());
                internalList.clear();
                FileListItem parent = new FileListItem();
                parent.setFilename("...");
                parent.setDirectory(true);
                parent.setLocation(inter.getParentFile().getAbsolutePath());
                parent.setTime(inter.lastModified());
                if(!inter.getName().equals(properties.offset.getName())) {
                    internalList.add(parent);
                }
                for (File name : inter.listFiles(filter))
                {   if(inter.getName().equals("mnt")) {
                        if (name.getName().startsWith("sdcard")) {
                            FileListItem item = new FileListItem();
                            item.setFilename(name.getName());
                            item.setDirectory(name.isDirectory());
                            item.setLocation(name.getAbsolutePath());
                            item.setTime(name.lastModified());
                            internalList.add(item);
                        }
                    }
                    else{
                        FileListItem item = new FileListItem();
                        item.setFilename(name.getName());
                        item.setDirectory(name.isDirectory());
                        item.setLocation(name.getAbsolutePath());
                        item.setTime(name.lastModified());
                        internalList.add(item);
                    }
                }
                Collections.sort(internalList);
                mFileListAdapter.notifyDataSetChanged();
            }
        }
    }

    public DialogProperties getProperties() {
        return properties;
    }

    public void setProperties(DialogProperties properties) {
        this.properties = properties;
    }

    public void setDialogSelectionListener(DialogSelectionListener callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    public void show() {
        super.show();
        Log.e(TAG,"Dialog Shown");
    }
//
//    @Override
//    public void onBackPressed() {
//        FileListItem fitem = internalList.get(0);
//        if (fitem.isDirectory()) {
//            File inter = new File(fitem.getLocation());
//            if(inter.getParentFile().getName().equals(properties.offset.getName()))
//            {   super.onBackPressed();
//            }
//            dname.setText(inter.getName());
//            dir_path.setText(inter.getAbsolutePath());
//            internalList.clear();
//            FileListItem parent = new FileListItem();
//            parent.setFilename("...");
//            parent.setDirectory(true);
//            parent.setLocation(inter.getParentFile().getAbsolutePath());
//            parent.setSize(inter.length());
//            if (!inter.getName().equals("mnt")) {
//                internalList.add(parent);
//            }
//            for (File name : inter.listFiles()) {
//                if (inter.getName().equals("mnt")) {
//                    if (name.getName().startsWith("sdcard")) {
//                        FileListItem item = new FileListItem();
//                        item.setFilename(name.getName());
//                        item.setDirectory(name.isDirectory());
//                        item.setLocation(name.getAbsolutePath());
//                        item.setSize(name.length());
//                        internalList.add(item);
//                    }
//                } else {
//                    FileListItem item = new FileListItem();
//                    item.setFilename(name.getName());
//                    item.setDirectory(name.isDirectory());
//                    item.setLocation(name.getAbsolutePath());
//                    item.setSize(name.length());
//                    internalList.add(item);
//                }
//            }
//            Collections.sort(internalList);
//            listView.setAdapter(new FileListAdapter(internalList, context,properties));
//        }
//    }
    @Override
    public void dismiss() {
        Log.e(TAG,"Dialog Dismissed");
        internalList.clear();
        MarkedItemList.clearSelectionList();
        super.dismiss();
    }
}
