package com.android.cracking;

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

	static void runJava2smali() {
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

				}
			}
		}
		rmClassPath();
		rmDex();
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
		runJava2smali();
	}
}
