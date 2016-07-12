package com.github.angads25.filechooser.model;

import java.util.HashMap;

/**
 * Created by Angad Singh on 12-07-2016.
 */
public class MountedDrives {
    private static HashMap<String,String> ourInstance = new HashMap<>();

    public static HashMap<String,String> getInstance() {
        return ourInstance;
    }

    private MountedDrives() {
    }
}
