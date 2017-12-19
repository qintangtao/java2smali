package com.android.cracking.utils;

import java.io.File;
import com.android.cracking.utils.ShellUtils.ShellResult;

public class JavaUtils {

	public static ShellResult java2class(String libPath, String classPath,
			String sourcePath) {
		String javaPath = sourcePath;
		sourcePath = javaPath.substring(0, javaPath.lastIndexOf(File.separator));
		return ShellUtils.exec("javac -classpath \"" + libPath + File.separator + "android.jar\";\""
				+ classPath + "\" -d \"" + classPath + "\" -sourcepath \"" + sourcePath
				+ "\" " + javaPath + "");
	}

	public static ShellResult class2dex(String libPath, String classPath) {
		return ShellUtils.exec("java -jar \"" + libPath
				+ File.separator + "dx.jar\" --dex --output=classes.dex \"" + classPath + "\"");
	}

	public static ShellResult dex2smali(String libPath, String smaliPath) {
		return ShellUtils.exec("java -jar \"" + libPath + File.separator + "baksmali.jar\" -o \""
				+ smaliPath + "\" classes.dex");
	}
}
