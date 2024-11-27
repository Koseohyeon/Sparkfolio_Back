package com.sparkfolio.sparkfolio_back.member.repository;

import com.sparkfolio.sparkfolio_back.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    //이메일이 있을수도 있고 없을수도 있기 떄문에 Optional로 해줌
    Optional<Member> findByEmail(String email);


}
