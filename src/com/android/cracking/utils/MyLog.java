package com.android.cracking.utils;

public class MyLog {

	public static final boolean DEBUG = false;
	
	public static void d(String msg) {
		if (DEBUG) {
			System.out.println(msg);
		}
	}
	
	public static void d(String tag, String msg) {
		if (DEBUG) {
			System.out.println(tag + ": " + msg);
		}
	}
	
	public static void d(String class_name, String method_name, String msg) {
		if (DEBUG) {
			System.out.println(class_name + "." + method_name + "(): " + msg);
		}
	}
	
	/*
	public static void d(Exception e) {
		if (DEBUG) {
			//System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}*/
	
	public static void e(String class_name, String method_name, Exception e) {
		if (DEBUG) {
			System.out.println(class_name + "." + method_name + "(): " + e.getMessage());
			e.printStackTrace();
		}
	}
}
