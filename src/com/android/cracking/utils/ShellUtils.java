package com.android.cracking.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShellUtils {

	private static final String TAG = ShellUtils.class.getSimpleName();

	public static class ShellResult {
		public int code;
		public String success;
		public String error;
		public String exception;

		public ShellResult(int code, String success, String error,
				String exception) {
			this.code = code;
			this.success = success;
			this.error = error;
			this.exception = exception;
		}
	}

	public static ShellResult exec(String command) {
		return exec(command, true);
	}
	
	public static ShellResult exec(String command, boolean wait) {
		int code = 0;
		StringBuilder sbSuccess = new StringBuilder();
		StringBuilder sbError = new StringBuilder();
		StringBuilder sbException = new StringBuilder();

		BufferedReader bufferedReaderError = null;
		BufferedReader bufferedReaderInput = null;
		try {
			MyLog.d(TAG, command);
			Process process = Runtime.getRuntime().exec(command);
			if (wait) {
				code = process.waitFor();
				
				bufferedReaderError = new BufferedReader(new InputStreamReader(
						process.getErrorStream()));
				bufferedReaderInput = new BufferedReader(new InputStreamReader(
						process.getInputStream()));

				String strInput;
				while ((strInput = bufferedReaderInput.readLine()) != null) {
					sbSuccess.append(strInput);
				}

				String strError;
				while ((strError = bufferedReaderError.readLine()) != null) {
					sbError.append(strError);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			code = -1;
			sbException.append(e.getMessage());
			MyLog.d(TAG, e.getMessage());
		}

		try {
			if (bufferedReaderInput != null) {
				bufferedReaderInput.close();
			}
			if (bufferedReaderError != null) {
				bufferedReaderError.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			sbException.append(e.getMessage());
			MyLog.d(TAG, e.getMessage());
		}

		return new ShellResult(code, sbSuccess.toString(), sbError.toString(),
				sbException.toString());
	}
}
