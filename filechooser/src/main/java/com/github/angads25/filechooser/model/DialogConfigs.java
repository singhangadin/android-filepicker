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

package com.github.angads25.filechooser.model;

/**<p>
 * Created by Angad Singh on 11-07-2016.
 * </p>
 */

public abstract class DialogConfigs {
    public static final String ROOT_MOUNT_DIR="/mnt";
    public static final int SINGLE_MODE=0;
    public static final int MULTI_MODE=1;
    public static final int FILE_SELECT=0;
    public static final int DIR_SELECT=1;
    public static final int FILE_AND_DIR_SELECT=2;
}
