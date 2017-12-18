package com.android.cracking;

import java.io.File;

import com.android.cracking.utils.FileUtils;
import com.android.cracking.utils.JavaUtils;
import com.android.cracking.utils.MyLog;
import com.android.cracking.utils.ShellUtils;
import com.android.cracking.utils.ShellUtils.ShellResult;

public class main {

	private static final String TAG = main.class.getSimpleName();
	
	static String WORKPATH = FileUtils.getAppPath();
	static String CLASSPATH = WORKPATH + File.separator + "classes";
	static String LIBPATH = WORKPATH + File.separator + "lib";
	static String SOURCEPATH = WORKPATH + File.separator + "java";
	static String SMALIPATH = WORKPATH + File.separator + "smali";
	
	static void fixPath() {
		MyLog.d(TAG, WORKPATH);
		CLASSPATH = WORKPATH + File.separator + CLASSPATH;
		LIBPATH = WORKPATH + File.separator + LIBPATH;
		SOURCEPATH = WORKPATH + File.separator + SOURCEPATH;
		SMALIPATH = WORKPATH + File.separator + SMALIPATH;
	}

	static boolean runJava2class(String javaFile) {		
		javaFile = FileUtils.getRealFilePath(javaFile);
		rmClassPath();
		FileUtils.mkDirs(CLASSPATH);

		String sourcePath = javaFile;
		if (sourcePath.isEmpty()) {
			sourcePath = SOURCEPATH + File.separator + "*.java";
		} else {
			if (!javaFile.endsWith(".java")) {
				if (javaFile.endsWith(File.separator)) {
					sourcePath = javaFile.substring(0, javaFile.length()-1);
				}
				sourcePath = sourcePath + File.separator + "*.java";
			}
		}
		
		ShellResult result = JavaUtils.java2class(LIBPATH, CLASSPATH,
				sourcePath);
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

	static void runJava2smali(String javaFile, boolean dumpSmali, String openFile) {
		//fixPath();
		MyLog.d(TAG, WORKPATH);
		
		System.out
				.println("=======================================================");
		System.out.println("java2class");
		if (runJava2class(javaFile)) {
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
					if (!openFile.isEmpty()) {
						openSmali(openFile);
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
	
	static void openSmali(String openFile) {
		FileUtils.openFiles(openFile, new File(SMALIPATH), ".smali");
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
		FileUtils.rmDir(WORKPATH + File.separator + "classes.dex");
	}

	public static void main(String[] args) {		
		boolean dumpSmali = false;
		String javaFile = "";
		String openFile = "";
		for (int i = 0; i < args.length; i++) {
			String string = args[i];
			MyLog.d(TAG, string);
			if ("dumpSmali".equals(string)) {
				dumpSmali = true;
			} else if ("-open".equals(string)) {
				if ((i+1) < args.length) {
					openFile = args[i+1];
				}
			} else if ("-java".equals(string)) {
				if ((i+1) < args.length) {
					javaFile = args[i+1];
				}
			}
		}
		runJava2smali(javaFile, dumpSmali, openFile);
	}
}
