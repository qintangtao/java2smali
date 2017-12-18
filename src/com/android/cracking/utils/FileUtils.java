package com.android.cracking.utils;

import java.io.File;

public class FileUtils {
	
	public static boolean isDir(String path) {
		File file = new File(path);
		return (file.exists() && file.isDirectory());
	}
	
	public static boolean isFile(String path) {
		File file = new File(path);
		return (file.exists() && file.isFile());
	}
	
	public static boolean mkDirs(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return file.mkdirs();
		}
		return true;
	}
	
	public static boolean rmDir(String path) {
		return rmDir(new File(path));
	}
	
	public static boolean rmDir(File dir) {
		if (dir.exists()) {
			if (dir.isDirectory()) {
				String[] list = dir.list();
				for (int i = 0; i < list.length; i++) {
					if (!rmDir(new File(dir, list[i]))) {
						return false;
					}
				}
			}
			return dir.delete(); 
		}
		return true;
	}
	
	public static boolean rmFile(String path) {
		File file = new File(path);
		if (file.exists()) {
			return file.delete();
		}
		return true;
	}
	
}
