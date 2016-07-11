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

import java.io.File;

/**<p>
 * Created by Angad Singh on 11-07-2016.
 * </p>
 */
public class DialogProperties {
    public int selection_mode,selection_type;
    public File offset;
    public String[] extensions;
    public boolean logging;

    public DialogProperties() {
        selection_mode=DialogConfigs.SINGLE_MODE;
        selection_type=DialogConfigs.DIR_SELECT;
        offset=new File(DialogConfigs.ROOT_MOUNT_DIR);
        extensions=null;
        logging=false;
    }
}
