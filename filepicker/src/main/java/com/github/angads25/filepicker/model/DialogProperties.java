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

package com.github.angads25.filepicker.model;

import java.io.File;

/**<p>
 * Created by Angad Singh on 11-07-2016.
 * </p>
 */

/*  Descriptor class to define properties of the Dialog. Actions are performed upon
 *  these Attributes
 */
public class DialogProperties {
    /*  Selection Mode defines whether a single of multiple Files/Directories
     *  have to be selected.
     *
     *  SINGLE_MODE and MULTI_MODE are the two selection modes, See DialogConfigs
     *  for more info. Set to Single Mode by default.
     */
    public int selection_mode;

    /*  Selection Type defines that whether a File/Directory or both of these has
     *  has to be selected.
     *
     *  FILE_SELECT ,DIR_SELECT, FILE_AND_DIR_SELECT are the three selection modes,
     *  See DialogConfigs for more info. Set to FILE_SELECT by default.
     */
    public int selection_type;

    /*  The Parent/Root Directory. List of Files are populated from here. Can be set
     *  to any readable directory. /sdcard is the default location.
     *
     *  Eg. /sdcard
     *  Eg. /mnt
     */
    public File root;

    /*  An Array of String containing extensions, Files with only that will be shown.
     *  Others will be ignored. Set to null by default.
     *  Eg. String ext={"jpg","jpeg","png","gif"};
     */
    public String[] extensions;

    public DialogProperties() {
        selection_mode=DialogConfigs.SINGLE_MODE;
        selection_type=DialogConfigs.FILE_SELECT;
        root=new File(DialogConfigs.DEFAULT_DIR);
        extensions=null;
    }
}
