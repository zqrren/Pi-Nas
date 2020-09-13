package com.ren.service;

import com.ren.pojo.FilePojo;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public interface FileService {

    List<FilePojo> getFilesList(String strPath);
    Path cd(String path);
    String getParent(String path);
}
