package com.sparkfolio.sparkfolio_back.resume.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumeDto {
    private String author;
    private String title;
    private String memo;
    private List<String> categories; // 여러 카테고리 지원
    private MultipartFile resumeFile; // 업로드할 파일
}
