package com.github.angads25.filechooserdialog;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.angads25.filechooserdialog.model.FileListAdapter;
import com.github.angads25.filechooserdialog.model.FileListItem;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

/**<p>
 * Created by Angad Singh on 09-07-2016.
 * </p>
 */

public class FileChooserDialog extends Dialog implements AdapterView.OnItemClickListener
{   private String extension;
    private ListView listView;
    private Context context;
    private TextView dname,dir_path;
    private ArrayList<FileListItem> internalList;

    public FileChooserDialog(Context context)
    {   super(context);
        this.context=context;
        this.extension="";
    }

    public FileChooserDialog(Context context, String extension)
    {   super(context);
        this.context=context;
        this.extension=extension;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {   super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_main);
        listView=(ListView)findViewById(R.id.fileList);

        // TODO : Permission Checks
        if(!checkWritePermissions())
        {   Toast.makeText(context,"Failed to access files, Do you have Permissions?",Toast.LENGTH_SHORT).show();
            dismiss();
        }
        else
        {   dname=(TextView)findViewById(R.id.dname);
            dir_path=(TextView)findViewById(R.id.dir_path);
            File inter = new File("/mnt");
            dname.setText(inter.getName());
            dir_path.setText(inter.getAbsolutePath());
            internalList = new ArrayList<>();
//            FileListItem parent = new FileListItem();
//            parent.setFilename("Parent");
//            parent.setDirectory(true);
//            parent.setLocation(inter.getAbsolutePath());
//            parent.setSize(inter.length());
//            internalList.add(parent);
            for (File name : inter.listFiles())
            {   if(name.getName().startsWith("sdcard"))
                {   FileListItem item = new FileListItem();
                    item.setFilename(name.getName());
                    item.setDirectory(name.isDirectory());
                    item.setLocation(name.getAbsolutePath());
                    item.setSize(name.length());
                    internalList.add(item);
                }
            }
            Collections.sort(internalList);
            listView.setAdapter(new FileListAdapter(internalList, context));
            listView.setOnItemClickListener(this);
        }
        Button select = (Button) findViewById(R.id.select);
        Button cancel = (Button) findViewById(R.id.cancel);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });
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
                parent.setSize(inter.length());
                if(!inter.getName().equals("mnt"))
                    internalList.add(parent);
                for (File name : inter.listFiles())
                {   if(inter.getName().equals("mnt")) {
                        if (name.getName().startsWith("sdcard")) {
                            FileListItem item = new FileListItem();
                            item.setFilename(name.getName());
                            item.setDirectory(name.isDirectory());
                            item.setLocation(name.getAbsolutePath());
                            item.setSize(name.length());
                            internalList.add(item);
                        }
                    }
                    else {
                        FileListItem item = new FileListItem();
                        item.setFilename(name.getName());
                        item.setDirectory(name.isDirectory());
                        item.setLocation(name.getAbsolutePath());
                        item.setSize(name.length());
                        internalList.add(item);
                    }
                }
                Collections.sort(internalList);
                listView.setAdapter(new FileListAdapter(internalList, context));
            }
        }
    }
}
