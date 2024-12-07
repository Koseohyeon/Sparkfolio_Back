package com.sparkfolio.sparkfolio_back.resume.repository;

import com.sparkfolio.sparkfolio_back.resume.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResumeRepository extends JpaRepository<Resume,Long> {
    List<Resume> findByAuthorEmail (String email);
}
