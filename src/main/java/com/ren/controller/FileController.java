package com.ren.controller;

import com.alibaba.fastjson.JSON;
import com.ren.pojo.FilePojo;
import com.ren.service.FileService;
import com.ren.service.FileServiceImpl;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class FileController {
    private final FileService fileService = new FileServiceImpl();

    @RequestMapping("/file")
    @ResponseBody
    public String listFile (String path) {
        List<FilePojo> filesList = fileService.getFilesList(path);
        return JSON.toJSONString(filesList);
    }

    @RequestMapping("/files")
    public String toFilesPage () {
        return "files";
    }

    @RequestMapping("/download")
    public String download (String path, HttpServletResponse response) throws IOException {
        System.out.println(path);
        String name = Paths.get(path).getFileName().toString();
        path = Paths.get(path).getParent().toString();
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName="
                + URLEncoder.encode(name, "UTF-8"));
        File file = new File(path, name);
        FileInputStream fis = new FileInputStream(file);
        ServletOutputStream out = response.getOutputStream();
        byte[] buff = new byte[1024];
        int len = 0;
        while ((len = fis.read(buff)) != -1) {
            out.write(buff, 0, len);
            out.flush();
        }
        out.close();
        fis.close();
        return "ok";
    }
}
