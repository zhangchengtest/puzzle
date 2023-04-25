package com.elephant.controller;

import com.cunw.boot.controller.BaseController;
import com.cunw.framework.vo.ResultVO;
import com.elephant.api.vo.score.ScoreVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

@RestController
@Slf4j
public class UploadController extends BaseController {

    @Value("${upload.dir}")
    private String uploadDir;

    @Value("${domain.st}")
    private String domainSt;

    @PostMapping("/upload")
    public ResultVO<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("fileName") String fileName) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String fileType = getFileType(originalFilename);
        String dateDir = LocalDate.now().toString();
        String filePath = uploadDir + dateDir + "/" + fileName+fileType;
        byte[] bytes = filePath.getBytes("UTF-8");
        String utf8FilePath = new String(bytes);
        File dest = new File(utf8FilePath);
        log.info("filePath 【{}】", filePath);
        File parentDir = dest.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }
        file.transferTo(dest);
        String url = domainSt + dateDir + "/" + fileName+fileType;
        return success(url);
    }

    private String getFileType(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex != -1) {
            return fileName.substring(lastDotIndex);
        }
        return "";
    }
}
