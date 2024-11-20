package com.sparkfolio.sparkfolio_back.member.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private String profile_photo;
}
