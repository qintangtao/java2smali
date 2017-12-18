package com.android.cracking.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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
	
	public static void printFile(File file) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String strLine;
			while ((strLine = reader.readLine()) != null) {
				System.out.println(strLine);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void printFile(String path) {
		File file = new File(path);
		if (file.exists()) {
			printFile(file);
		}
	}
	
	public static void printFiles(File dir, String endsWith) {
		if (dir.exists()) {
			if (dir.isDirectory()) {
				if (dir.isDirectory()) {
					String[] list = dir.list();
					for (int i = 0; i < list.length; i++) {
						printFiles(new File(dir, list[i]), endsWith);
					}
				}
			} else {
				if (endsWith.isEmpty() || dir.getName().endsWith(endsWith)) {
					System.out.println(dir.getPath());
					FileUtils.printFile(dir);
					System.out.println("");
				}
			}
		}
	}
	
	public static void printFiles(File dir) {
		printFiles(dir, "");
	}
	
	public static void printFiles(String path, String endsWith) {
		printFiles(new File(path), endsWith);
	}
	
	public static void printFiles(String path) {
		printFiles(new File(path), "");
	}
}
