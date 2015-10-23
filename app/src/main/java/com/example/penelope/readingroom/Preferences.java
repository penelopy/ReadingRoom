package com.example.penelope.readingroom;

import android.content.Context;
import android.text.TextUtils;

public class Preferences {

    private static final String USERNAME = "username";
    private static final String SETTINGS = "settings";

    public static String getUsername(Context context) {
        return context.getSharedPreferences(SETTINGS, 0).getString(USERNAME, null);
    }

    public static boolean hasUsername(Context context) {
        return context.getSharedPreferences(SETTINGS, 0).contains(USERNAME);
    }

    public static void setUsername(Context context, String username) {
        if (TextUtils.isEmpty(username)) {
            throw new IllegalArgumentException("Cannot store null or empty username.");
        }
        context.getSharedPreferences(SETTINGS, 0).edit().putString(USERNAME, username).apply();
    }

    public static void removeUsername(Context context) {
        context.getSharedPreferences(SETTINGS, 0).edit().remove(USERNAME).apply();
    }
}
