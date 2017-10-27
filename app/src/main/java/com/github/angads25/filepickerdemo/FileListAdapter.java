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
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**<p>
 * Created by Angad Singh on 11-07-2016.
 * </p>
 */

class FileListAdapter extends RecyclerView.Adapter<FileListAdapter.FileListViewHolder> {
    private ArrayList<ListItem> listItems;
    private Context context;

    FileListAdapter(ArrayList<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public FileListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.file_list_item, parent, false);
        return new FileListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FileListViewHolder holder, int position) {
        holder.name.setText(listItems.get(position).getName());
        holder.path.setText(listItems.get(position).getPath());
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    class FileListViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView path;

        FileListViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            path = itemView.findViewById(R.id.path);
        }
    }
}
