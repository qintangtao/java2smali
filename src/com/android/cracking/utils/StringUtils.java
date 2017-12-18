package com.android.cracking.utils;

public class StringUtils {

	public static String left(String str, int n) {
		return str.substring(0, n);
	}
	
	public static String right(String str, int n) {
		return str.substring(n);
	}
}
