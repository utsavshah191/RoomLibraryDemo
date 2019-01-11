package com.demo.roomlibrarydemo.utils;

import android.os.Build;
import android.util.Log;

import java.util.Locale;



public class Utilities {


    private static final boolean DEBUGGING_BUILD = true;

    public static void debug(final String tag, final Object message) {
        if (DEBUGGING_BUILD)
            Log.d(tag, message.toString());
    }
}
