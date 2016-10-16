package mobi.gastronomica.utils;

import android.app.Activity;
import android.content.SharedPreferences;

public class PreferenceManager {
	
    public static SharedPreferences getSharedPreferences(Activity activity) {
        return android.preference.PreferenceManager.getDefaultSharedPreferences(activity);
    }

    public static SharedPreferences.Editor getEditor(Activity activity) {
        return android.preference.PreferenceManager.getDefaultSharedPreferences(activity).edit();
    }

    public static String getString(String key, Activity activity) {
        return getSharedPreferences(activity).getString(key, null);
    }

    public static int getInt(String key, Activity activity) throws Exception {
        if(getSharedPreferences(activity).contains(key))
            return getSharedPreferences(activity).getInt(key, -1);
        else
            throw new Exception("Key not in preferences");
    }

    public static boolean getBoolean(String key, Activity activity) throws Exception {
        if(getSharedPreferences(activity).contains(key))
            return getSharedPreferences(activity).getBoolean(key, false);
        else
            throw new Exception("Key not in preferences");
    }

    public static float getFloat(String key, Activity activity) throws Exception {
        if(getSharedPreferences(activity).contains(key))
            return getSharedPreferences(activity).getFloat(key, 0.0f);
        else
            throw new Exception("Key not in preferences");
    }

    public static long getLong(String key, Activity activity) throws Exception {
        if(getSharedPreferences(activity).contains(key))
            return getSharedPreferences(activity).getLong(key, -1);
        else
            throw new Exception("Key not in preferences");
    }

    public static boolean putBoolean(String key, Boolean value, Activity activity) {
        SharedPreferences.Editor ed = getEditor(activity);
        ed.putBoolean(key,value);
        return ed.commit();
    }

    public static boolean putFloat(String key, float value, Activity activity) {
        SharedPreferences.Editor ed = getEditor(activity);
        ed.putFloat(key, value);
        return ed.commit();
    }

    public static boolean putInt(String key, int value, Activity activity) {
        SharedPreferences.Editor ed = getEditor(activity);
        ed.putInt(key, value);
        return ed.commit();
    }

    public static boolean putLong(String key, long value, Activity activity) {
        SharedPreferences.Editor ed = getEditor(activity);
        ed.putLong(key, value);
        return ed.commit();
    }

    public static boolean putString(String key, String value, Activity activity) {
        SharedPreferences.Editor ed = getEditor(activity);
        ed.putString(key, value);
        return ed.commit();
    }

    public static boolean remove(String key, Activity activity) {
        SharedPreferences.Editor ed = getEditor(activity);
        ed.remove(key);
        return ed.commit();
    }
    public static boolean removeAll(Activity activity) {
        SharedPreferences.Editor ed = getEditor(activity);
        ed.clear();
        return ed.commit();
    }
    
}//end class
