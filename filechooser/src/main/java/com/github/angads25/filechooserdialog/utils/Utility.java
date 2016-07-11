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

package com.github.angads25.filechooserdialog.utils;

import android.content.Context;
import android.content.pm.PackageManager;

import com.github.angads25.filechooserdialog.model.FileListItem;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

/**<p>
 * Created by Angad Singh on 11-07-2016.
 * </p>
 */
public class Utility {
    public static boolean checkWritePermissions(Context context)
    {   String permission1 = "android.permission.WRITE_EXTERNAL_STORAGE";
        String permission2 = "android.permission.READ_EXTERNAL_STORAGE";
        int res1 = context.checkCallingOrSelfPermission(permission1);
        int res2 = context.checkCallingOrSelfPermission(permission2);
        return (res1 == PackageManager.PERMISSION_GRANTED&&res2==PackageManager.PERMISSION_GRANTED);
    }

    public static ArrayList<FileListItem> prepareFileListEntries(ArrayList<FileListItem> internalList,File inter,ExtensionFilter filter)
    {   try {
            for (File name : inter.listFiles(filter)) {
                if (inter.getName().equals("mnt")) {
                    if (name.getName().startsWith("sdcard")) {
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
}
