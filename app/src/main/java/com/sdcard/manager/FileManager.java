package com.sdcard.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by Hari on 8/2/16.
 */
public class FileManager {
    //START-SINGLETON
    private static FileManager ourInstance = new FileManager();

    public static FileManager getInstance() {
        return ourInstance;
    }

    private FileManager() {
    }
    //END-SINGLETON

    private List<File> files = new ArrayList<>();

    private Stack<String> fileBackTrack = new Stack<>();

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }


    public Stack<String> getFileBackTrack() {
        return fileBackTrack;
    }

    public void setFileBackTrack(Stack<String> fileBackTrack) {
        this.fileBackTrack = fileBackTrack;
    }

    public void pushToBackTrack(String path) {
        this.fileBackTrack.push(path);
    }

    public void popBackTrack() {
        this.fileBackTrack.pop();
    }

    public String topOfBackTrack() {
        if (fileBackTrack.size() == 0)
            return -1 + "";
        return fileBackTrack.get(fileBackTrack.size() - 1);
    }
}
