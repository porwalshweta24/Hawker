package app.com.hawker.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Admin on 2/15/2016.
 */
public class Preferences {

    private static SharedPreferences sharedPref;
    private static final String PREF_NAME = "appname_preferences";
    public static final String DEVICE_ID = "device_id";
    public static final String IS_wifi = "is_wifi";

    public static void init(Context context) {
        sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static void saveValue(String key, String value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void saveValue(String key, int value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static void saveValue(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static String getString(String key) {
        return sharedPref.getString(key, null);
    }

    public static int getInt(String key) {
        return sharedPref.getInt(key, 0);
    }

    public static boolean getBoolean(String key) {
        return sharedPref.getBoolean(key, false);
    }

    public static void clearAll() {
        sharedPref.edit().clear().apply();
    }

    public static void clear(String key) {
        sharedPref.edit().remove(key).commit();
    }
}
