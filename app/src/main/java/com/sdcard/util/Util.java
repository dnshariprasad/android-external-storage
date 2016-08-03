package com.sdcard.util;

/**
 * Created by Hari on 8/3/16.
 */
public class Util {
    public static boolean isImage(String name) {
        int index = name.lastIndexOf('.') + 1;
        String extension = name.substring(index, name.length());
        return extension.equalsIgnoreCase(Constant.fileFormat.GIF) ||
                extension.equalsIgnoreCase(Constant.fileFormat.TIF) ||
                extension.equalsIgnoreCase(Constant.fileFormat.JPEG) ||
                extension.equalsIgnoreCase(Constant.fileFormat.JPG) ||
                extension.equalsIgnoreCase(Constant.fileFormat.PNG);
    }
}
