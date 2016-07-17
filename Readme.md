# FilePicker
Android Library to select files/directories from Device Storage.


### Features

* Easy to Implement.
* No permissions required.
* Files, Directory Selection.
* Single or Multiple File selection.


### Installation

1) Paste following stub into the ``app/build.gradle`` ,
```gradle
    repositories {
        maven {
            url 'https://dl.bintray.com/angads25/maven/'
        }
    }
```
2) In ``app/build.gradle`` add the FilePicker library as a dependency:
```
    dependencies {
        compile 'com.github.angads25:filepicker:1.0.0'
    }
```

3) Sync project, clean and build. You can use the ``FilePicker`` library as part of your project now.


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
        selection_mode=DialogConfigs.SINGLE_MODE;
        selection_type=DialogConfigs.FILE_SELECT;
        root=new File(DialogConfigs.DEFAULT_DIR);
        extensions=null;
    ```
    ### Read more about `DialogConfigs` and other options [here](https://github.com/Angads25/android-filepicker/blob/master/filepicker/src/main/java/com/github/angads25/filepicker/model/DialogConfigs.java).
    
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
    
##### Best Practice
Marshmallow and further requests for the permission during runtime. I would recommend to override `onRequestPermissionsResult` of Activity/AppCompatActivity class and show the dialog only if permissions have been granted.
    
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
