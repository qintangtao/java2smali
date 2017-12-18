package com.android.cracking.utils;

import com.android.cracking.utils.ShellUtils.ShellResult;

public class JavaUtils {

	//private static final String TAG = JavaUtils.class.getSimpleName();

	public static ShellResult java2class(String libPath, String classPath,
			String sourcePath) {
		return ShellUtils.exec("javac -classpath " + libPath + "/android.jar;"
				+ classPath + " -d " + classPath + " -sourcepath " + sourcePath
				+ " " + sourcePath + "/*.java");
	}

	public static ShellResult class2dex(String libPath, String classPath) {
		return ShellUtils.exec("java -jar " + libPath
				+ "/dx.jar --dex --output=classes.dex " + classPath);
	}

	public static ShellResult dex2smali(String libPath, String smaliPath) {
		return ShellUtils.exec("java -jar " + libPath + "/baksmali.jar -o "
				+ smaliPath + " classes.dex");
	}
}
