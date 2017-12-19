package com.android.cracking;

import java.io.File;

import com.android.cracking.utils.FileUtils;
import com.android.cracking.utils.JavaUtils;
import com.android.cracking.utils.MyLog;
import com.android.cracking.utils.ShellUtils;
import com.android.cracking.utils.ShellUtils.ShellResult;

public class SmaliCmd {
	private static final String TAG = SmaliCmd.class.getSimpleName();

	private static final String WORKPATH = FileUtils.getAppPath();
	private static final String CLASSPATH = WORKPATH + File.separator + "classes";
	private static final String LIBPATH = WORKPATH + File.separator + "lib";
	private static final String SOURCEPATH = WORKPATH + File.separator + "java";
	private static final String SMALIPATH = WORKPATH + File.separator + "smali";

	String[] mArgs;
	int mNextArg;

	public void doMain(String[] args) {
		mArgs = args;
		mNextArg = 0;

		boolean dumpSmali = false;
		String javaFile = "";
		String openFile = "";

		while (true) {
			String arg = nextArg();
			if (arg == null) {
				break;
			}

			if ("dumpSmali".equals(arg)) {
				dumpSmali = true;
				MyLog.d(TAG, "dumpSmali");
			} else if ("-open".equals(arg)) {
				arg = nextArg();
				if (arg == null) {
					break;
				}
				openFile = arg;
				MyLog.d(TAG, "-open " + arg);
			} else if ("-java".equals(arg)) {
				arg = nextArg();
				if (arg == null) {
					break;
				}
				javaFile = arg;
				MyLog.d(TAG, "-java " + arg);
			}
		}

		runJava2smali(javaFile, dumpSmali, openFile);
	}

	String nextArg() {
		if (mNextArg >= mArgs.length) {
			return null;
		}
		String arg = mArgs[mNextArg];
		mNextArg++;
		return arg;
	}

	boolean runJava2class(String javaFile) {
		javaFile = FileUtils.getRealFilePath(javaFile);
		rmClassPath();
		FileUtils.mkDirs(CLASSPATH);

		String sourcePath = javaFile;
		if (sourcePath.isEmpty()) {
			sourcePath = SOURCEPATH + File.separator + "*.java";
		} else {
			if (!javaFile.endsWith(".java")) {
				if (javaFile.endsWith(File.separator)) {
					sourcePath = javaFile.substring(0, javaFile.length() - 1);
				}
				sourcePath = sourcePath + File.separator + "*.java";
			}
		}

		ShellResult result = JavaUtils.java2class(LIBPATH, CLASSPATH,
				sourcePath);
		ShellUtils.printResult(result);
		return (result.code == 0);
	}

	boolean runClass2dex() {
		ShellResult result = JavaUtils.class2dex(LIBPATH, CLASSPATH);
		ShellUtils.printResult(result);
		return (result.code == 0);
	}

	boolean runDex2smali() {
		rmSmaliPath();
		FileUtils.mkDirs(SMALIPATH);

		ShellResult result = JavaUtils.dex2smali(LIBPATH, SMALIPATH);
		ShellUtils.printResult(result);
		return (result.code == 0);
	}

	void runJava2smali(String javaFile, boolean dumpSmali, String openFile) {
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

	void printSmali() {
		FileUtils.printFiles(SMALIPATH, ".smali");
	}

	void openSmali(String openFile) {
		FileUtils.openFiles(openFile, new File(SMALIPATH), ".smali");
	}

	void rmClassPath() {
		if (FileUtils.isDir(CLASSPATH)) {
			FileUtils.rmDir(CLASSPATH);
		}
	}

	void rmSmaliPath() {
		if (FileUtils.isDir(SMALIPATH)) {
			FileUtils.rmDir(SMALIPATH);
		}
	}

	void rmDex() {
		FileUtils.rmDir(WORKPATH + File.separator + "classes.dex");
	}
}
