package com.sparkfolio.sparkfolio_back.resume.repository;

import com.sparkfolio.sparkfolio_back.member.entity.Member;
import com.sparkfolio.sparkfolio_back.resume.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResumeRepository extends JpaRepository<Resume,Long> {
    List<Resume> findByAuthorEmail (String email);
    Optional<Resume> findByIdAndAuthor(Long id, Member author);
}
