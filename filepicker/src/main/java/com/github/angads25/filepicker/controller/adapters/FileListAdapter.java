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

package com.github.angads25.filepicker.controller.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.angads25.filepicker.R;
import com.github.angads25.filepicker.controller.NotifyItemChecked;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.model.FileListItem;
import com.github.angads25.filepicker.model.MarkedItemList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**<p>
 * Created by Angad Singh on 09-07-2016.
 * </p>
 */

public class FileListAdapter extends BaseAdapter{
    private ArrayList<FileListItem> listItem;
    private Context context;
    private DialogProperties properties;
    private NotifyItemChecked notifyItemChecked;

    public FileListAdapter(ArrayList<FileListItem> listItem, Context context, DialogProperties properties) {
        this.listItem = listItem;
        this.context = context;
        this.properties=properties;
    }

    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public FileListItem getItem(int i) {
        return listItem.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.dialog_file_list_item, viewGroup, false);
        }
        final ViewHolder holder = new ViewHolder(view);
        final FileListItem item = listItem.get(i);
        if (item.isDirectory()) {
            holder.type_icon.setImageResource(R.mipmap.ic_type_folder);
            if(properties.selection_type== DialogConfigs.FILE_SELECT)
            {   holder.fmark.setVisibility(View.INVISIBLE);
            }
            else
            {   holder.fmark.setVisibility(View.VISIBLE);
            }
        }
        else {
            holder.type_icon.setImageResource(R.mipmap.ic_type_file);
            if(properties.selection_type==DialogConfigs.DIR_SELECT)
            {   holder.fmark.setVisibility(View.INVISIBLE);
            }
            else
            {   holder.fmark.setVisibility(View.VISIBLE);
            }
        }
        holder.type_icon.setContentDescription(item.getFilename());
        holder.name.setText(item.getFilename());
        SimpleDateFormat sdate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat stime = new SimpleDateFormat("hh:mm aa", Locale.getDefault());
        Date date = new Date(item.getTime());
        if(i==0&&item.getFilename().startsWith("...")) {
            holder.type.setText("Parent Directory");
        }
        else {
            holder.type.setText("Last edited: " + sdate.format(date) + ", " + stime.format(date));
        }
        if(holder.fmark.getVisibility()==View.VISIBLE) {
            if(i==0&&item.getFilename().startsWith("..."))
            {   holder.fmark.setVisibility(View.INVISIBLE);
            }
            if (MarkedItemList.hasItem(item.getLocation())) {
                holder.fmark.setChecked(true);
            }
            else {
                holder.fmark.setChecked(false);
            }
        }
        holder.fmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(properties.selection_mode==DialogConfigs.MULTI_MODE) {
                    item.setMarked(!item.isMarked());
                    if (item.isMarked()) {
                        MarkedItemList.addSelectedItem(item);
                    } else {
                        MarkedItemList.removeSelectedItem(item.getLocation());
                    }
                }
                else {
                    item.setMarked(!item.isMarked());
                    if (item.isMarked()) {
                        MarkedItemList.addSingleFile(item);
                    }
                    else {
                        MarkedItemList.removeSelectedItem(item.getLocation());
                    }
                }
                notifyItemChecked.notifyCheckBoxIsClicked();
            }
        });
        return view;
    }

    private class ViewHolder
    {   public ImageView type_icon;
        public TextView name,type;
        public CheckBox fmark;

        public ViewHolder(View itemView) {
            name=(TextView)itemView.findViewById(R.id.fname);
            type=(TextView)itemView.findViewById(R.id.ftype);
            type_icon=(ImageView)itemView.findViewById(R.id.image_type);
            fmark=(CheckBox) itemView.findViewById(R.id.file_mark);
        }
    }

    public void setNotifyItemCheckedListener(NotifyItemChecked notifyItemChecked) {
        this.notifyItemChecked = notifyItemChecked;
    }
}
