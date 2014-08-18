package com.example.trlab.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil{
    public static int copy(String fromFile, String toFile){
        File[] currentFiles;
        File root = new File(fromFile);
        if (!root.exists()) {
            return -1;
        }
        currentFiles = root.listFiles();

        File targetDir = new File(toFile);
        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }
        
        for (int i = 0; i < currentFiles.length; i++) {
            if (currentFiles[i].isDirectory()){
                copy(currentFiles[i].getPath() + "/", toFile + currentFiles[i].getName() + "/");
            } else{
                CopySdcardFile(currentFiles[i].getPath(), toFile + currentFiles[i].getName());
            }
        }
        return 0;
    }
    
    private static int CopySdcardFile(String fromFile, String toFile) {
        if(StringUtils.isNullOrEmpty(fromFile) || StringUtils.isNullOrEmpty(toFile)){
            return -1;
        }
        File desFile =new File(toFile);
        if (desFile != null && !desFile.exists()) {
            InputStream fosfrom = null;
            OutputStream fosto = null;
            try {
                fosfrom = new FileInputStream(fromFile);
                fosto = new FileOutputStream(toFile);
                byte[] buff = new byte[1024];
                int c;
                while ((c = fosfrom.read(buff)) > 0) {
                    fosto.write(buff, 0, c);
                }
                fosfrom.close();
                fosto.close();
                return 0;
            } catch (Exception ex) {
                return -1;
            } finally {
                try {
                    if (fosfrom != null) {
                        fosfrom.close();
                    }
                    if (fosto != null) {
                        fosto.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return -1;
                }
            }
        }
        return 0;
    }
}