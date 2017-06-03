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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**<p>
 * Created by Angad Singh on 11-07-2016.
 * </p>
 */

public class FileListAdapter extends BaseAdapter{
    private ArrayList<ListItem> listItems;
    private Context context;

    public FileListAdapter(ArrayList<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public ListItem getItem(int i) {
        return listItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null)
        {   view=LayoutInflater.from(context).inflate(R.layout.file_list_item,viewGroup,false);
        }
        TextView name=(TextView)view.findViewById(R.id.name);
        TextView path=(TextView)view.findViewById(R.id.path);
        name.setText(listItems.get(i).getName());
        path.setText(listItems.get(i).getPath());
        return view;
    }
}
