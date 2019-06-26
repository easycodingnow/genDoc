package com.easycodingnow.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lihao
 * @since 2019-06-01
 */
public class FileUtils {

    public static File getJavaFileByFileName(String strPath, String findFileName) {
        File dir = new File(strPath);
        File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                String fileName = file.getName();
                if (file.isDirectory()) {
                    File findRes = getJavaFileByFileName(file.getAbsolutePath(), findFileName);
                    if (findRes != null) {
                        return findRes;
                    }
                } else if (fileName.equals(findFileName)) {
                    return file;
                }
            }
        }
        return null;
    }

    public static List<String> getJavaFileList(String filePath) {
        List<String> fileList = new ArrayList<String>();
        File file = new File(filePath);
        if(file.exists()){
            File[] childFiles = file.listFiles();
            if(childFiles != null && childFiles.length > 0) {
                for (File childFile : childFiles) {
                    if (childFile.isDirectory()) {
                        fileList.addAll(getJavaFileList(childFile.getPath()));
                    } else {
                        String childFilePath = childFile.getPath();

                        if(childFilePath.endsWith(".java")){
                            fileList.add(childFilePath);
                        }
                    }
                }
            }
        }

        return fileList;
    }

    public static List<String> getJavaSourcePath(String filePath) {
        List<String> fileList = new ArrayList<String>();
        File file = new File(filePath);
        if(file.exists()){
            File[] childFiles = file.listFiles();
            if(childFiles != null && childFiles.length > 0) {
                for (File childFile : childFiles) {
                    if (childFile.isDirectory()) {
                        String absPath = childFile.getAbsolutePath();
                        if (absPath.endsWith("src"+ File.separator +"main" + File.separator + "java")) {
                            fileList.add(absPath);
                            break;
                        } else {
                            fileList.addAll(getJavaSourcePath(childFile.getPath()));
                        }
                    }
                }
            }
        }

        return fileList;
    }


}
