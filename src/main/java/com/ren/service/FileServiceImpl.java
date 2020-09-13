package com.ren.service;

import com.ren.pojo.FilePojo;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileServiceImpl implements FileService{
    @Override
    public List<FilePojo> getFilesList (String strPath) {
        Path path = cd(strPath);
        File file = path.toFile();
        if (!file.isFile()){
            List<FilePojo> list = new ArrayList<>();
            list.add(new FilePojo(true,getParent(strPath),"..",0,new Date(0)));
            if (file.exists()){
                File[] files = file.listFiles();
                assert files != null;
                for (File each : files) {
                    boolean isDir = each.isDirectory();
                    String filePath = each.getPath();
                    String name = each.getName();
                    long size = each.length();
                    Date date = new Date(each.lastModified());
                    list.add(new FilePojo(isDir,filePath,name,size,date));
                }
            }
            return list;
        }
        return null;
    }

    @Override
    public Path cd (String path) {
        return Paths.get(path);
    }

    @Override
    public String getParent (String path) {
        return Paths.get(path).getParent().toString();
    }
}
