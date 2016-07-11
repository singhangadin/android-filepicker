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

package com.github.angads25.filechooserdialog.model;

import java.util.HashMap;
import java.util.Set;

/**<p>
 * Created by Angad Singh on 11-07-2016.
 * </p>
 */
public class MarkedItemList {
    private static HashMap<String,FileListItem> ourInstance = new HashMap<>();

//    public static HashMap<String, FileListItem> getSelectedItems() {
//        return ourInstance;
//    }

    public static void addSelectedItem(FileListItem item)
    {   ourInstance.put(item.getLocation(),item);
    }

    public static void removeSelectedItem(String key)
    {   ourInstance.remove(key);
    }

    public static boolean hasItem(String key)
    {   return ourInstance.containsKey(key);
    }

    public static void clearSelectionList()
    {   ourInstance.clear();
    }

    public static FileListItem[] addSingleFile(FileListItem item)
    {   FileListItem[] oldfiles=new FileListItem[ourInstance.size()];
        int i=0;
        for(String key:ourInstance.keySet())
        {   oldfiles[i]=ourInstance.get(key);
        }
        ourInstance.clear();
        ourInstance.put(item.getLocation(),item);
        return oldfiles;
    }

    public static String[] getSelectedPaths()
    {   Set<String> paths=ourInstance.keySet();
        String fpaths[]=new String[paths.size()];
        int i=0;
        for(String path:paths)
        {   fpaths[i++]=path;
        }
        return fpaths;
    }

    public static int getFileCount()
    {   return ourInstance.size();
    }

    private MarkedItemList() {
    }
}
