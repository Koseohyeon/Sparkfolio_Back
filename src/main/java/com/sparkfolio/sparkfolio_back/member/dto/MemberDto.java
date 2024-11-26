package com.sparkfolio.sparkfolio_back.member.dto;

import com.sparkfolio.sparkfolio_back.member.entity.Member;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {

    private String name;
    private String email;
    private String password;
    private String interest;
    private String profilePhoto; // 프로필 사진 URL

    // Member 엔티티를 DTO로 변환하는 생성자
    public MemberDto(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
        this.interest = member.getInterest();
        this.profilePhoto = member.getProfilePhoto();
        this.password = null; // 비밀번호는 포함하지 않음
    }
}
