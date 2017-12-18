package com.android.cracking.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils {
	
	public static String getRealFilePath(String path) {
		 return path.replace("/", File.separator).replace("\\", File.separator);
	}
	
	public static String getWorkSpase() {
        File dir = new File("");
        return dir.getAbsolutePath();
    }
	
	public static String getAppPath() {
		String path = System.getProperty("java.class.path");
		return path.substring(0, path.lastIndexOf(File.separator)-1);
	}
	
	public static String getAppPath(Class cls){
        if(cls==null) 
         throw new java.lang.IllegalArgumentException("参数不能为空！");
        ClassLoader loader=cls.getClassLoader();
        String clsName=cls.getName()+".class";
        Package pack=cls.getPackage();
        String path="";
        if(pack!=null){
            String packName=pack.getName();
           if(packName.startsWith("java.")||packName.startsWith("javax.")) 
              throw new java.lang.IllegalArgumentException("不要传送系统类！");
            clsName=clsName.substring(packName.length()+1);
            if(packName.indexOf(".")<0) path=packName+"/";
            else{
                int start=0,end=0;
                end=packName.indexOf(".");
                while(end!=-1){
                    path=path+packName.substring(start,end)+"/";
                    start=end+1;
                    end=packName.indexOf(".",start);
                }
                path=path+packName.substring(start)+"/";
            }
        }
        java.net.URL url =loader.getResource(path+clsName);
        String realPath=url.getPath();
        int pos=realPath.indexOf("file:");
        if(pos>-1) realPath=realPath.substring(pos+5);
        pos=realPath.indexOf(path+clsName);
        realPath=realPath.substring(0,pos-1);
        if(realPath.endsWith("!"))
            realPath=realPath.substring(0,realPath.lastIndexOf("/"));
      try{
        realPath=java.net.URLDecoder.decode(realPath,"utf-8");
       }catch(Exception e){throw new RuntimeException(e);}
       return realPath;
    }
	
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
		printFiles(path, "");
	}
}
