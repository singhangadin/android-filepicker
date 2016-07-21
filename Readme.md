# FilePicker
Android Library to select files/directories from Device Storage.

### Developed by
[Angad Singh](https://www.github.com/angads25) ([@angads25](https://www.twitter.com/angads25))

### Benchmark:
[![API](https://img.shields.io/badge/API-9%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=9) <a href="http://www.methodscount.com/?lib=com.github.angads25%3Afilepicker%3A1.0.2"><img src="https://img.shields.io/badge/Size-28 KB-e91e63.svg"/></a>

### Where to Find:
[ ![Download](https://api.bintray.com/packages/angads25/maven/filepicker/images/download.svg) ](https://bintray.com/angads25/maven/filepicker/_latestVersion) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.angads25/filepicker/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.angads25/filepicker) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-FilePicker-blue.svg?style=flat)](http://android-arsenal.com/details/1/3950)

### Features

* Easy to Implement.
* No permissions required.
* Files, Directory Selection.
* Single or Multiple File selection.


### Installation

* Library is also Available in MavenCentral, So just put this in your app dependencies:
```gradle
    compile 'com.github.angads25:filepicker:1.0.2'
```

### Usage
1. Start by creating an instance of `DialogProperties`. 

    ```java
        DialogProperties properties=new DialogProperties();
    ```

    Now 'DialogProperties' has certain parameters. 

|Parameter|Type|Function|
|---|---|---|
|`selection_mode`|int|Selection Mode defines whether a single of multiple Files/Directories have to be selected.|
|`selection_type`|int|Selection Type defines whether a File/Directory or both of these has to be selected.|
|`root`|File|The Parent/Root Directory. List of Files are populated from here. Can be set to any readable directory.|
|`extensions`|String[]|An Array of String containing extensions, Files with only that will be shown. Others will be ignored.|

2. Assign values to each Dialog Property using `DialogConfig` class.
    
    ```java
        properties.selection_mode=DialogConfigs.SINGLE_MODE;
        properties.selection_type=DialogConfigs.FILE_SELECT;
        properties.root=new File(DialogConfigs.DEFAULT_DIR);
        properties.extensions=null;
    ```
    ### Read more about `DialogConfigs` and other options from [here](https://github.com/Angads25/android-filepicker/blob/master/filepicker/src/main/java/com/github/angads25/filepicker/model/DialogConfigs.java).
    
3. Next create an instance of `FilePickerDialog`, and pass `Context` and `DialogProperties` references as parameters.

    ```java
        FilePickerDialog dialog = new FilePickerDialog(MainActivity.this,properties);
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

    That's It. You are good to go further.
    
### NOTE:
Marshmallow and further requests for the permission on runtime. You should override `onRequestPermissionsResult` of Activity/AppCompatActivity class and show the dialog only if permissions have been granted.
    
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


### Screenshots
![Screenshot 1](https://raw.githubusercontent.com/Angads25/android-filepicker/master/screenshot1.png)

![Screenshot 2](https://raw.githubusercontent.com/Angads25/android-filepicker/master/screenshot2.png)

![Screenshot 3](https://raw.githubusercontent.com/Angads25/android-filepicker/master/screenshot3.png)

![Screenshot 4](https://raw.githubusercontent.com/Angads25/android-filepicker/master/screenshot4.png)

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