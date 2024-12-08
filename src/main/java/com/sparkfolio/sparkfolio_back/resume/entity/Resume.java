package com.sparkfolio.sparkfolio_back.resume.entity;

import com.sparkfolio.sparkfolio_back.common.entity.BaseTimeEntity;
import com.sparkfolio.sparkfolio_back.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resume extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resume_id")
    private Long id;

    @Column(nullable = false)
    private String title; // 제목

    @Column(nullable = false)
    private String category; // 카테고리

    @Column(nullable = true, length = 300)
    private String memo; // 메모

    @Column(nullable = false)
    private String fileUrl; // 이력서 파일 URL

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member author;
}