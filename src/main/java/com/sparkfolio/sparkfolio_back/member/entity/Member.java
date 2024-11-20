package com.sparkfolio.sparkfolio_back.member.entity;

import com.sparkfolio.sparkfolio_back.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private  Long id;
    @Column(length = 30,nullable = false)
    private String name;
    @Column(unique = true,nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(length = 30,nullable = false)
    private String interest;
    @Column(nullable = true)
    private String profile_photo;


}
