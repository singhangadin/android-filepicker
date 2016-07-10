package com.github.angads25.filechooserdialog.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.angads25.filechooserdialog.R;

import java.util.ArrayList;

/**<p>
 * Created by Angad Singh on 09-07-2016.
 * </p>
 */

public class FileListAdapter extends BaseAdapter{
    private ArrayList<FileListItem> listItem;
    private Context context;

    public FileListAdapter(ArrayList<FileListItem> listItem, Context context) {
        this.listItem = listItem;
        this.context = context;
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
        ViewHolder holder = new ViewHolder(view);
        {   FileListItem item = listItem.get(i);
            if (item.isDirectory()) {
                holder.type_icon.setImageResource(R.drawable.ic_type_folder);
            } else {
                holder.type_icon.setImageResource(R.drawable.ic_type_file);
            }
            holder.name.setText(item.getFilename());
            holder.finfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
        }
        return view;
    }

    private class ViewHolder
    {   public ImageView type_icon;
        public TextView name,type;
        public ImageButton finfo;

        public ViewHolder(View itemView) {
            name=(TextView)itemView.findViewById(R.id.fname);
            type=(TextView)itemView.findViewById(R.id.ftype);
            type_icon=(ImageView)itemView.findViewById(R.id.image_type);
            finfo=(ImageButton)itemView.findViewById(R.id.file_info);
        }
    }
}
