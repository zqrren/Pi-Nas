package com.ren.controller;

import com.alibaba.fastjson.JSON;
import com.ren.pojo.FilePojo;
import com.ren.service.FileService;
import com.ren.service.FileServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
public class FileController {
    private final FileService fileService = new FileServiceImpl();

    @RequestMapping("/getSEPARATOR")
    @ResponseBody
    public String getSEPARATOR () {
        return File.separator;
    }

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
    public void download (String path, HttpServletResponse response) throws IOException {
        List<String> array = JSON.parseArray(path, String.class);
        String name = System.currentTimeMillis() + ".zip";
        String zipPath = System.getProperty("nas") + name;
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath));
        for (String s : array) {
            File file = Paths.get(s).toFile();
            zip(zos, file, "");
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
    }

    private void zip (ZipOutputStream out, File file, String path) throws IOException {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            assert files != null;
            for (File f : files) {
                zip(out, f, path + file.getName() + File.separator);
            }
        } else {
            out.putNextEntry(new ZipEntry(path + file.getName()));
            FileInputStream fis = new FileInputStream(file);
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = fis.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            fis.close();
        }
    }

    @RequestMapping("/upload")
    @ResponseBody
    public String upload (@RequestParam(value = "uploadFiles", required = false) MultipartFile[] files, @RequestParam(value = "path") String path, @RequestParam(value = "isDir") boolean isDir) throws IOException {
        for (MultipartFile file : files) {
            String name = file.getOriginalFilename();
            InputStream is = file.getInputStream();
            FileOutputStream fos = new FileOutputStream(Paths.get(path + File.separator + name).toFile());
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            fos.close();
            is.close();
        }
        return "ok";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete (String path) {
        List<String> array = JSON.parseArray(path, String.class);
        for (String s : array) {
            File file = Paths.get(s).toFile();
            if (file.isDirectory()) {
                deleteDir(file);
            } else {
                file.delete();
            }
        }
        return "ok";
    }

    private void deleteDir (File dir) {
        File[] files = dir.listFiles();
        assert files != null;
        for (File file : files) {
            if (file.isDirectory()) {
                deleteDir(file);
            } else {
                boolean b = file.delete();
                System.out.println(file.getName() + " " + b);
            }
        }
        dir.delete();
    }

    @RequestMapping("/mkdir")
    @ResponseBody
    public String mkdir (String path, String name) {
        File file = Paths.get(path + File.separator + name).toFile();
        boolean b = file.mkdirs();
        return "ok";
    }
}