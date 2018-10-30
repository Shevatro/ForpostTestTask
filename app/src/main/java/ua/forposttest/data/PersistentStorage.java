package ua.forposttest.data;

import android.content.Context;
import android.content.SharedPreferences;

import ua.forposttest.App;

public class PersistentStorage {
    private static final String STORAGE_NAME = "ua.forposttest";
    public static final String FIELD_FIGHTER_RECORD = "field_fighter_record";

    private static SharedPreferences settings = null;
    private static SharedPreferences.Editor editor = null;

    private static void init() {
        settings = App.getAppContext().getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
    }

    private static void checkInit() {
        if (settings == null) {
            init();
        }
    }

    public static void addProperty(String name, String value) {
        checkInit();
        editor.putString(name, value);
        editor.apply();
    }

    public static String getProperty(String name, String defaultName) {
        checkInit();
        return settings.getString(name, defaultName);
    }

    public static String getProperty(String name) {
        return getProperty(name, null);
    }

    public static void clean() {
        checkInit();
        editor.clear();
        editor.apply();
    }
}

