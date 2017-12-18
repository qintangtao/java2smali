package com.android.cracking;

import com.android.cracking.utils.FileUtils;
import com.android.cracking.utils.JavaUtils;
import com.android.cracking.utils.ShellUtils;
import com.android.cracking.utils.ShellUtils.ShellResult;

public class main {

	static final String CLASSPATH = "./classes";
	static final String LIBPATH = "./lib";
	static final String SOURCEPATH = "./java";
	static final String SMALIPATH = "./smali";

	static boolean runJava2class() {
		rmClassPath();
		FileUtils.mkDirs(CLASSPATH);

		ShellResult result = JavaUtils.java2class(LIBPATH, CLASSPATH,
				SOURCEPATH);
		ShellUtils.printResult(result);
		return (result.code == 0);
	}

	static boolean runClass2dex() {
		ShellResult result = JavaUtils.class2dex(LIBPATH, CLASSPATH);
		ShellUtils.printResult(result);
		return (result.code == 0);
	}

	static boolean runDex2smali() {
		rmSmaliPath();
		FileUtils.mkDirs(SMALIPATH);

		ShellResult result = JavaUtils.dex2smali(LIBPATH, SMALIPATH);
		ShellUtils.printResult(result);
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
		FileUtils.printFiles(SMALIPATH, ".smali");
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
			// System.out.println(string);
			if ("dumpsmali".equals(string)) {
				dumpSmali = true;
			}
		}
		runJava2smali(dumpSmali);
	}
}
