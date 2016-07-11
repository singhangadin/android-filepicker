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

package com.github.angads25.filechooser.utils;

import android.content.Context;
import android.content.pm.PackageManager;

import com.github.angads25.filechooser.model.FileListItem;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

/**<p>
 * Created by Angad Singh on 11-07-2016.
 * </p>
 */
public class Utility {
    public static boolean checkReadPermissions(Context context)
    {   if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            String permission = "android.permission.READ_EXTERNAL_STORAGE";
            int res = context.checkCallingOrSelfPermission(permission);
            return (res == PackageManager.PERMISSION_GRANTED);
        }
        else
        {   return true;
        }
    }

    public static ArrayList<FileListItem> prepareFileListEntries(ArrayList<FileListItem> internalList,File inter,ExtensionFilter filter)
    {   try {
            for (File name : inter.listFiles(filter)) {
                if (inter.getName().equals("mnt")) {
                    if (name.getName().toLowerCase().contains("sdcard")) {
                        FileListItem item = new FileListItem();
                        item.setFilename(name.getName());
                        item.setDirectory(name.isDirectory());
                        item.setLocation(name.getAbsolutePath());
                        item.setTime(name.lastModified());
                        internalList.add(item);
                    }
                } else {
                    FileListItem item = new FileListItem();
                    item.setFilename(name.getName());
                    item.setDirectory(name.isDirectory());
                    item.setLocation(name.getAbsolutePath());
                    item.setTime(name.lastModified());
                    internalList.add(item);
                }
            }
            Collections.sort(internalList);
        }
        catch (NullPointerException e)
        {   e.printStackTrace();
            internalList=new ArrayList<>();
        }
        return internalList;
    }

    private boolean hasSupportLibraryOnClasspath() {
        try {
            Class.forName("com.android.support:appcompat-v7");
            return true;
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
