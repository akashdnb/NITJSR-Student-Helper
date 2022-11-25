package com.example.nitjsrstudenthelper.utils;

import android.os.Environment;

import java.io.File;

public class pathUtil {
    //public static File rootDir = Environment.getExternalStoragePublicDirectory("Downloads");
    public static File rootDir = new File(Environment.getExternalStorageDirectory()+"/Documents/studentHelper");
    //public static File rootDir= Environment.getExternalStoragePublicDirectory("studentHelper");


    public static void createFleFolder(){
        if(!rootDir.exists())
            rootDir.mkdirs();
    }
}
