## The project is no longer being maintained.

# FilePicker
Super Lite Android Library to select files/directories from Device Storage.

### Developed by
[Angad Singh](https://www.github.com/angads25) ([@angads25](https://www.twitter.com/angads25))

### Benchmark:
[![API](https://img.shields.io/badge/API-9%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=9) <a href="http://www.methodscount.com/?lib=com.github.angads25%3Afilepicker%3A1.1.1"><img src="https://img.shields.io/badge/Methods and size-271 | 43 KB-e91e63.svg"/></a>

### Where to Find:
[ ![Download](https://api.bintray.com/packages/angads25/maven/filepicker/images/download.svg) ](https://bintray.com/angads25/maven/filepicker/_latestVersion) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.angads25/filepicker/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.angads25/filepicker) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-FilePicker-blue.svg?style=flat)](http://android-arsenal.com/details/1/3950)

### Read all about internal classes and functions in the [wiki](https://github.com/Angads25/android-filepicker/wiki).

### Features

* Easy to Implement.
* No permissions required.
* Files, Directory Selection.
* Single or Multiple File selection.

### Installation

* Library is also Available in MavenCentral, So just put this in your app dependencies to use it:
```gradle
    compile 'com.github.angads25:filepicker:1.1.1'
```

### Usage
## FilePickerDialog
1. Start by creating an instance of `DialogProperties`.

    ```java
        DialogProperties properties = new DialogProperties();
    ```

    Now 'DialogProperties' has certain parameters.

2. Assign values to each Dialog Property using `DialogConfig` class.

    ```java
        properties.selection_mode = DialogConfigs.SINGLE_MODE;
        properties.selection_type = DialogConfigs.FILE_SELECT;
        properties.root = new File(DialogConfigs.DEFAULT_DIR);
        properties.error_dir = new File(DialogConfigs.DEFAULT_DIR);
        properties.offset = new File(DialogConfigs.DEFAULT_DIR);
        properties.extensions = null;
    ```

3. Next create an instance of `FilePickerDialog`, and pass `Context` and `DialogProperties` references as parameters. Optional: You can change the title of dialog. Default is current directory name. Set the positive button string. Default is Select. Set the negative button string. Defalut is Cancel.

    ```java
        FilePickerDialog dialog = new FilePickerDialog(MainActivity.this,properties);
        dialog.setTitle("Select a File");
    ```

4.  Next, Attach `DialogSelectionListener` to `FilePickerDialog` as below,
    ```java
        dialog.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(String[] files) {
                //files is the array of the paths of files selected by the Application User.
            }
        });
    ```
    An array of paths is returned whenever user press the `select` button`.

5. Use ```dialog.show()``` method to show dialog.

### NOTE:
Marshmallow and above requests for the permission on runtime. You should override `onRequestPermissionsResult` in Activity/AppCompatActivity class and show the dialog only if permissions have been granted.

```java
        //Add this method to show Dialog when the required permission has been granted to the app.
        @Override
        public void onRequestPermissionsResult(int requestCode,@NonNull String permissions[],@NonNull int[] grantResults) {
            switch (requestCode) {
                case FilePickerDialog.EXTERNAL_READ_PERMISSION_GRANT: {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        if(dialog!=null)
                        {   //Show dialog if the read permission has been granted.
                            dialog.show();
                        }
                    }
                    else {
                        //Permission has not been granted. Notify the user.
                        Toast.makeText(MainActivity.this,"Permission is Required for getting list of files",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
```

    That's It. You are good to proceed further.

### FilePickerPreference

1. Start by declaring [FilePickerPreference](https://github.com/angads25/android-filepicker/wiki/filepicker-preference) in your settings xml file as:

    ```xml
       <com.github.angads25.filepicker.view.FilePickerPreference
           xmlns:app="http://schemas.android.com/apk/res-auto"
           android:key="your_preference_key"
           android:title="Pick a Directory"
           android:summary="Just a Summary"
           android:defaultValue="/sdcard:/mnt"
           app:titleText="Select Directories"
           app:error_dir="/mnt"
           app:root_dir="/sdcard"
           app:selection_mode="multi_mode"
           app:selection_type="dir_select"
           app:extensions="txt:pdf:"/>
    ```

2. Implement [Preference.OnPreferenceChangeListener](https://developer.android.com/reference/android/preference/Preference.OnPreferenceChangeListener.html) to class requiring selected values and `Override` `onPreferenceChange(Preference, Object)` method. Check for preference key using [Preference](https://developer.android.com/reference/android/preference/Preference.html) reference.

    ```java
        @Override
        public boolean onPreferenceChange(Preference preference, Object o)
        {   if(preference.getKey().equals("your_preference_key"))
            {   ...
            }
            return false;
        }
    ```
3. Typecast `Object o` into `String` Object and use `split(String)` function in `String` class to get array of selected files.

    ```java
        @Override
        public boolean onPreferenceChange(Preference preference, Object o)
        {   if(preference.getKey().equals("your_preference_key"))
            {   String value=(String)o;
                String arr[]=value.split(":");
                ...
                ...
            }
            return false;
        }
    ```

    That's It. You are good to move further.

### Important:
* `defaultValue`, `error_dir`, `root_dir`, `offset_dir` must have valid directory/file paths.
* `defaultValue` paths should end with ':'.
* `defaultValue` can have multiple paths, there should be a ':' between two paths.
* `extensions` must not have '.'.
* `extensions` should end with ':' , also have ':' between two extensions.
eg. /sdcard:/mnt:

### Note:
[FilePickerPreference](https://github.com/angads25/android-filepicker/wiki/filepicker-preference) stores selected directories/files as a `String`. It delimits multiple files/directories using ':' `char`.

## Read more on implementation [here](https://github.com/Angads25/android-filepicker/wiki/Implementation).

### Screenshot

#### Theme.Black

![Screenshot 1](https://raw.githubusercontent.com/Angads25/android-filepicker/release/screenshots/theme_black.png)

#### Theme.Holo

![Screenshot 2](https://raw.githubusercontent.com/Angads25/android-filepicker/release/screenshots/theme_holo.png)

#### Theme.Holo.Light

![Screenshot 3](https://raw.githubusercontent.com/Angads25/android-filepicker/release/screenshots/theme_holo_light.png)

#### Theme.Material

![Screenshot 4](https://raw.githubusercontent.com/Angads25/android-filepicker/release/screenshots/theme_material.png)

#### Theme.DeviceDefault

![Screenshot 5](https://raw.githubusercontent.com/Angads25/android-filepicker/release/screenshots/theme_device_default.png)

### Performance

#### GPU Overdraw

![Performance 1](https://raw.githubusercontent.com/Angads25/android-filepicker/release/screenshots/performance_overdraw.png)

#### GPU Rendering

![Performance 2](https://raw.githubusercontent.com/Angads25/android-filepicker/release/screenshots/profile_gpu_rendering.png)

### License
    Copyright (C) 2016 Angad Singh

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
