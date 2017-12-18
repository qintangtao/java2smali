package com.android.cracking;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import com.android.cracking.utils.FileUtils;
import com.android.cracking.utils.JavaUtils;
import com.android.cracking.utils.ShellUtils.ShellResult;

public class main {

	static final String CLASSPATH = "./classes";
	static final String LIBPATH = "./lib";
	static final String SOURCEPATH = "./java";
	static final String SMALIPATH = "./smali";

	static void printResult(ShellResult result) {
		System.out.println("resultCode: " + result.code);
		if (!result.success.isEmpty()) {
			System.out.println("resultSuccess: " + result.success);
		}
		if (!result.error.isEmpty()) {
			System.out.println("resultError: " + result.error);
		}
		if (!result.exception.isEmpty()) {
			System.out.println("resultException: " + result.exception);
		}
	}

	static boolean runJava2class() {
		rmClassPath();

		FileUtils.mkDirs(CLASSPATH);

		ShellResult result = JavaUtils.java2class(LIBPATH, CLASSPATH,
				SOURCEPATH);
		printResult(result);
		return (result.code == 0);
	}

	static boolean runClass2dex() {
		ShellResult result = JavaUtils.class2dex(LIBPATH, CLASSPATH);
		printResult(result);
		return (result.code == 0);
	}

	static boolean runDex2smali() {
		rmSmaliPath();
		FileUtils.mkDirs(SMALIPATH);

		ShellResult result = JavaUtils.dex2smali(LIBPATH, SMALIPATH);
		printResult(result);
		return (result.code == 0);
	}

	static void runJava2smali(boolean dumpSmali) {
		System.out
				.println("=======================================================");
		System.out.println("java2class");
		if (runJava2class()) {
			System.out
					.println("=======================================================");
			System.out.println("class2dex");
			if (runClass2dex()) {
				System.out
						.println("=======================================================");
				System.out.println("dex2smali");
				if (runDex2smali()) {
					if (dumpSmali) {
						System.out
								.println("=======================================================");
						printSmali();
					}
				}
			}
		}
		rmClassPath();
		rmDex();
	}

	static void printSmali() {
		printSmali(new File(SMALIPATH));
	}

	static void printSmali(File dir) {
		if (dir.exists()) {
			if (dir.isDirectory()) {
				if (dir.isDirectory()) {
					String[] list = dir.list();
					for (int i = 0; i < list.length; i++) {
						printSmali(new File(dir, list[i]));
					}
				}
			} else {
				if (dir.getName().endsWith(".smali")) {
					System.out.println(dir.getPath());
					dumpFile(dir);
					System.out.println("");
				}
			}
		}
	}

	static void dumpFile(File file) {
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

	static void rmClassPath() {
		if (FileUtils.isDir(CLASSPATH)) {
			FileUtils.rmDir(CLASSPATH);
		}
	}

	static void rmSmaliPath() {
		if (FileUtils.isDir(SMALIPATH)) {
			FileUtils.rmDir(SMALIPATH);
		}
	}

	static void rmDex() {
		FileUtils.rmDir("classes.dex");
	}

	public static void main(String[] args) {
		boolean dumpSmali = false;
		for (int i = 0; i < args.length; i++) {
			String string = args[i];
			//System.out.println(string);
			if ("dumpsmali".equals(string)) {
				dumpSmali = true;
			}
		}
		runJava2smali(dumpSmali);
	}
}
