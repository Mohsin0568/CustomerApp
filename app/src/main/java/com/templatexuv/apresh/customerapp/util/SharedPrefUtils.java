package com.templatexuv.apresh.customerapp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.templatexuv.apresh.customerapp.CustomerApplication;


public class SharedPrefUtils 
{
    // string
    public static void writePreferenceValue(String prefsKey, String prefsValue) {
        SharedPreferences.Editor editor = getPrefsEditor();
        editor.putString(prefsKey, prefsValue);
        editor.commit();
    }

    // int
    public static void writePreferenceValue(String prefsKey, int prefsValue) {
        SharedPreferences.Editor editor = getPrefsEditor();
        editor.putInt(prefsKey, prefsValue);
        editor.commit();
    }

    // long
    public static void writePreferenceValue(String prefsKey, long prefsValue) {
        SharedPreferences.Editor editor = getPrefsEditor();
        editor.putLong(prefsKey, prefsValue);
        editor.commit();
    }

    // boolean
    public static void writePreferenceValue(String prefsKey, boolean prefsValue) {
        SharedPreferences.Editor editor = getPrefsEditor();
        editor.putBoolean(prefsKey, prefsValue);
        editor.commit();
    }


    // string
    public static String readStringPreferenceValue(String prefsKey) {
        SharedPreferences prefs = getPrefs();
        return prefs.getString(prefsKey,null);
    }

    // int
    public static int readIntPreferenceValue(String prefsKey) {
        SharedPreferences prefs = getPrefs();
       return prefs.getInt(prefsKey,0);
    }

    // long
    public static long readLongPreferenceValue(String prefsKey) {
        SharedPreferences prefs = getPrefs();
        return prefs.getLong(prefsKey,0);
    }


    // boolean
    public static boolean readBooleanPreferenceValue(String prefsKey) {
        SharedPreferences prefs = getPrefs();
       return prefs.getBoolean(prefsKey,false);
    }


    private static SharedPreferences.Editor getPrefsEditor() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(CustomerApplication.context);
        return sharedPreferences.edit();
    }

    private static SharedPreferences getPrefs() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(CustomerApplication.context);
        return sharedPreferences;
    }


	/**
	 * This method Clears the SharedPreference
	 * @param prefName
	 */
	public static void clearValues(String prefName)
	{
		SharedPreferences pref = CustomerApplication.context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.clear();
		editor.commit();
	}
	
}
