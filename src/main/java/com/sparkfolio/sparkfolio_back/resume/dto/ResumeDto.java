package com.sparkfolio.sparkfolio_back.resume.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumeDto {

    private String title; // 제목
    private String category; // 카테고리
    private String memo; // 메모
    private String fileUrl; // 파일 URL
    private String authorEmail; // 작성자 이메일
}