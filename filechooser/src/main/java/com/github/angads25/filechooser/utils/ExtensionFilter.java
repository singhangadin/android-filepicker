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

import com.github.angads25.filechooser.model.DialogConfigs;
import com.github.angads25.filechooser.model.DialogProperties;

import java.io.File;
import java.io.FileFilter;

/**<p>
 * Created by Angad Singh on 11-07-2016.
 * </p>
 */
public class ExtensionFilter implements FileFilter {
    private final String[] validExtensions;
    private DialogProperties properties;

    public ExtensionFilter(DialogProperties properties) {
        if(properties.extensions!=null) {
            this.validExtensions = properties.extensions;
        }
        else {
            this.validExtensions = new String[]{""};
        }
        this.properties=properties;
    }
    @Override
    public boolean accept(File file) {
        if (file.isDirectory()&&file.canRead())
        {   return true;
        }
        else if(properties.selection_type==DialogConfigs.DIR_SELECT)
        {   return false;
        }
        else
        {   String name = file.getName().toLowerCase();
            for (String ext : validExtensions) {
                if (name.endsWith(ext)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setProperties(DialogProperties properties) {
        this.properties = properties;
    }
}
