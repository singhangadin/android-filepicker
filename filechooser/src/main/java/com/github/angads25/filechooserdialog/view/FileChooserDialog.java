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
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.angads25.filechooserdialog.R;
import com.github.angads25.filechooserdialog.controller.DialogSelectionListener;
import com.github.angads25.filechooserdialog.controller.NotifyItemChecked;
import com.github.angads25.filechooserdialog.model.DialogConfigs;
import com.github.angads25.filechooserdialog.model.DialogProperties;
import com.github.angads25.filechooserdialog.model.FileListItem;
import com.github.angads25.filechooserdialog.model.MarkedItemList;
import com.github.angads25.filechooserdialog.model.adapters.FileListAdapter;
import com.github.angads25.filechooserdialog.utils.ExtensionFilter;
import com.github.angads25.filechooserdialog.utils.Utility;

import java.io.File;
import java.util.ArrayList;

/**<p>
 * Created by Angad Singh on 09-07-2016.
 * </p>
 */

public class FileChooserDialog extends Dialog implements AdapterView.OnItemClickListener
{   private Context context;
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
        this.context=context;
        this.properties = properties;
        filter=new ExtensionFilter(properties);
        internalList=new ArrayList<>();
    }

    public FileChooserDialog(Context context, boolean cancelable, OnCancelListener cancelListener, DialogProperties properties) {
        super(context, cancelable, cancelListener);
        this.context=context;
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
        dname=(TextView)findViewById(R.id.dname);
        dir_path=(TextView)findViewById(R.id.dir_path);
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
                MarkedItemList.clearSelectionList();
                dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MarkedItemList.clearSelectionList();
                dismiss();
            }
        });
        mFileListAdapter=new FileListAdapter(internalList, context,properties);
        mFileListAdapter.setNotifyItemChecked(new NotifyItemChecked() {
            @Override
            public void notifyCheckBoxIsClicked() {
                int size=MarkedItemList.getFileCount();
                if(size==0)
                {   select.setText(context.getResources().getString(R.string.choose_button_label));
                }
                else
                {   select.setText(context.getResources().getString(R.string.choose_button_label)+"("+size+")");
                }
                if(properties.selection_mode==DialogConfigs.SINGLE_MODE)
                {   mFileListAdapter.notifyDataSetChanged();
                }
            }
        });
        listView.setAdapter(mFileListAdapter);
        Log.e("TAG","Dialog Created");
    }

    @Override
    protected void onStart() {
        super.onStart();
        select.setText(context.getResources().getString(R.string.choose_button_label));
        if(!Utility.checkWritePermissions(context))
        {   Toast.makeText(context,"Failed to access files, Do you have Permissions?", Toast.LENGTH_SHORT).show();
        }
        else
        {   File currLoc;
            if(properties.offset.exists()&&properties.offset.isDirectory())
            {   currLoc=properties.offset;
            }
            else
            {   currLoc=new File(DialogConfigs.ROOT_MOUNT_DIR);
                Toast.makeText(context,"File/Directory not found. Showing default location",Toast.LENGTH_SHORT).show();
            }
            dname.setText(currLoc.getName());
            dir_path.setText(currLoc.getAbsolutePath());
            internalList.clear();
            internalList=Utility.prepareFileListEntries(internalList,currLoc,filter);
            mFileListAdapter.notifyDataSetChanged();
            listView.setOnItemClickListener(this);
        }
        Log.e("TAG","Dialog Started");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("TAG","Dialog Stopped");
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        {   FileListItem fitem=internalList.get(i);
            if(fitem.isDirectory()&&new File(fitem.getLocation()).exists())
            {   File currLoc = new File(fitem.getLocation());
                dname.setText(currLoc.getName());
                dir_path.setText(currLoc.getAbsolutePath());
                internalList.clear();
                FileListItem parent = new FileListItem();
                parent.setFilename("...");
                parent.setDirectory(true);
                parent.setLocation(currLoc.getParentFile().getAbsolutePath());
                parent.setTime(currLoc.lastModified());
                if(!currLoc.getName().equals(properties.offset.getName())) {
                    internalList.add(parent);
                }
                internalList=Utility.prepareFileListEntries(internalList,currLoc,filter);
                mFileListAdapter.notifyDataSetChanged();
            }
        }
    }

    public DialogProperties getProperties() {
        return properties;
    }

    public void setProperties(DialogProperties properties) {
        this.properties = properties;
        filter=new ExtensionFilter(properties);
    }

    public void setDialogSelectionListener(DialogSelectionListener callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    public void show() {
        super.show();
        Log.e("TAG","Dialog Shown");
    }

    @Override
    public void onBackPressed() {
        String currentDirName=dname.getText().toString();
        if(internalList.size()>0) {
            FileListItem fitem = internalList.get(0);
            if (fitem.isDirectory()&&new File(fitem.getLocation()).exists()) {
                File currLoc = new File(fitem.getLocation());
                if (currentDirName.equals(properties.offset.getName())) {
                    super.onBackPressed();
                }
                else {
                    dname.setText(currLoc.getName());
                    dir_path.setText(currLoc.getAbsolutePath());
                    internalList.clear();
                    FileListItem parent = new FileListItem();
                    parent.setFilename("...");
                    parent.setDirectory(true);
                    parent.setLocation(currLoc.getParentFile().getAbsolutePath());
                    parent.setTime(currLoc.lastModified());
                    if (!currLoc.getName().equals("mnt")) {
                        internalList.add(parent);
                    }
                    internalList = Utility.prepareFileListEntries(internalList, currLoc, filter);
                    mFileListAdapter.notifyDataSetChanged();
                }
            }
        }
        else
        {   super.onBackPressed();
        }
    }

    @Override
    public void hide() {
        super.hide();
        Log.e("TAG","Dialog Hid");
    }

    @Override
    public void cancel() {
        super.cancel();
        Log.e("TAG","Dialog Cancelled");
    }

    @Override
    public void dismiss() {
        MarkedItemList.clearSelectionList();
        internalList.clear();
        super.dismiss();
        Log.e("TAG","Dialog Dismissed");
    }
}
