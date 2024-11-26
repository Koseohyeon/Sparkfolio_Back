package com.sparkfolio.sparkfolio_back.member.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberRequest {
    private String name;
    private String interest;
}
