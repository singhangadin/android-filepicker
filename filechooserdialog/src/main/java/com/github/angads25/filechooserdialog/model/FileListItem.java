package com.github.angads25.filechooserdialog.model;

/**<p>
 * Created by Angad Singh on 09-07-2016.
 * </p>
 */

public class FileListItem implements Comparable<FileListItem>
{   private String filename,location;
    private boolean directory;
    private long size;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isDirectory() {
        return directory;
    }

    public void setDirectory(boolean directory) {
        this.directory = directory;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public int compareTo(FileListItem fileListItem) {
        return filename.toLowerCase().compareTo(fileListItem.getFilename().toLowerCase());
    }
}