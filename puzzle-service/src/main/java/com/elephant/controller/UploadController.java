package com.elephant.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

@RestController
public class UploadController {

    @Value("${upload.dir}")
    private String uploadDir;

    @Value("${domain.st}")
    private String domainSt;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("fileName") String fileName) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String fileType = getFileType(originalFilename);
        String dateDir = LocalDate.now().toString();
        String filePath = uploadDir + dateDir + "/" + fileName+"."+fileType;
        File dest = new File(filePath);
        File parentDir = dest.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }
        file.transferTo(dest);
        String url = domainSt + dateDir + "/" + fileName;
        return url;
    }

    private String getFileType(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex != -1) {
            return fileName.substring(lastDotIndex);
        }
        return "";
    }
}
