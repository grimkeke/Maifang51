package com.djmedia.maifang51.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by rd on 14-3-21.
 */
public class Utils {
    private static final String TAG = Utils.class.getSimpleName();

    public static boolean isUserOnLine(Context context) {
        boolean userOnLine = false;
        if (context != null) {
            SharedPreferences preferences = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
            userOnLine = preferences.getBoolean(Constants.USER_ON_LINE, false);
        }
        Log.d(TAG, "is user online; " + userOnLine);
        return userOnLine;
    }

    public static void saveUserOnLine(Context context) {
        if (context != null) {
            SharedPreferences preferences = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
            preferences.edit().putBoolean(Constants.USER_ON_LINE, true).commit();
            Log.d(TAG, "save user on line succeed");
        }
    }
}
