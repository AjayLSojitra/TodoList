package com.vaapglkns.todolist.utils;

import android.os.Environment;

import java.io.File;

public class Constant {
    public static final String FOLDER_NAME = ".vaapglkns_java";
    public static final String CACHE_DIR = ".vaapglkns_java/Cache";

    public static final String TMP_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + FOLDER_NAME + "/tmp";
    public static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "" + FOLDER_NAME;

    public static final String FOLDER_RIDEINN_PATH = Environment
            .getExternalStorageDirectory().getAbsolutePath()
            + File.separator
            + "vaapglkns";

    public static final String LOCATION_UPDATED = "location_updated";
    public static final int TIMEOUT = 5 * 60 * 1000;


    //FOR SHARED PREFERENCES
    public static final String sUSER_LATITUDE = "sUSER_LATITUDE";
    public static final String sUSER_LONGITUDE = "sUSER_LONGITUDE";
    public static final String sTO_DO_LIST_INFO = "sTO_DO_LIST_INFO";
    public static final String sUSER_ID = "sUSER_ID";
    public static final String sUSER_TOKEN = "sUSER_TOKEN";
    public static final String sSESSION_ID = "sSESSION_ID";


    //FOR ON-ACTIVITY RESULT
    public static final int oarREQ_CODE_SETTING = 555;


    //FOR PERMISSION REQUEST CODE
    public static final int orprREQUEST_PERMISSION = 8810;
    public static final int orprREQUEST_GPS = 8811;


    //FOR BROAD-CAST RECEIVER
    public static final String brFINISH_ACTIVITY = "brFINISH_ACTIVITY";
}
