package com.afollestad.overhear.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Store {

	public static int i(Context context, String key, int def) {
		SharedPreferences prefs = context.getSharedPreferences("store", 0);
		return prefs.getInt(key, def);
	}
	
	public static void put(Context context, String key, int value) {
		SharedPreferences prefs = context.getSharedPreferences("store", 0);
		prefs.edit().putInt(key, value).commit();
	}
}
