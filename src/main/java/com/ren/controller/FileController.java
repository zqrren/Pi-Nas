package com.ren.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
        List<String> array = JSON.parseArray(path, String.class);
        String name = System.currentTimeMillis()+".zip";
        String zipPath = System.getProperty("nas") + name;
        System.out.println(zipPath);
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath));
        for (String s : array) {
            File file = Paths.get(s).toFile();
            zip(zos,file,"");
        }
        zos.close();
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName="
                + URLEncoder.encode(name, "UTF-8"));
        File file = new File(zipPath);
        InputStream is = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(is);
        ServletOutputStream out = response.getOutputStream();
        byte[] buff = new byte[1024];
        int len = 0;
        while ((len = bis.read(buff)) != -1) {
            out.write(buff, 0, len);
            out.flush();
        }
        out.close();
        is.close();
        bis.close();
        boolean b = file.delete();
        System.out.println(b);
        return "ok";
    }
    private void zip(ZipOutputStream out,File file,String path) throws IOException {
        if (file.isDirectory()){
            File[] files = file.listFiles();
            assert files != null;
            for (File f : files) {
                zip(out,f,path+file.getName()+File.separator);
            }
        }
        else {
            out.putNextEntry(new ZipEntry(path+file.getName()));
            FileInputStream fis = new FileInputStream(file);
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = fis.read(buffer))!=-1){
                out.write(buffer,0,len);
            }
            fis.close();
        }
    }
}
