package com.ren.controller;

import com.alibaba.fastjson.JSON;
import com.ren.pojo.FilePojo;
import com.ren.service.FileService;
import com.ren.service.FileServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@Controller
public class FileController {
    private final FileService fileService = new FileServiceImpl();

    @RequestMapping("/file")
    @ResponseBody
    public String listFile(String path) {
        List<FilePojo> filesList = fileService.getFilesList(path);
        return JSON.toJSONString(filesList);
    }
    @RequestMapping("/files")
    public String toFilesPage(){
        return "files";
    }
    @RequestMapping("/download")
    public void download(String path, HttpServletResponse response) throws IOException {
        System.out.println(path);
    }
}
