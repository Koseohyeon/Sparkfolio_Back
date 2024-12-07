package com.sparkfolio.sparkfolio_back.resume.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Paths;

@RestController
public class FileController {

    private String   saveDir; // 파일 저장 경로

    @GetMapping("/files/{filename}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) throws MalformedURLException {
        File file = Paths.get(saveDir, filename).toFile();
        Resource resource = new UrlResource(file.toURI());

        if (!file.exists() || !resource.exists()) {
            throw new RuntimeException("파일을 찾을 수 없습니다: " + filename);
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(resource);
    }
}