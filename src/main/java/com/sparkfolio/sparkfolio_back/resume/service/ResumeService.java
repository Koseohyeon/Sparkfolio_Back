    package com.sparkfolio.sparkfolio_back.resume.service;

    import com.sparkfolio.sparkfolio_back.member.entity.Member;
    import com.sparkfolio.sparkfolio_back.member.repository.MemberRepository;
    import com.sparkfolio.sparkfolio_back.resume.dto.ResumeDto;
    import com.sparkfolio.sparkfolio_back.resume.entity.Resume;
    import com.sparkfolio.sparkfolio_back.resume.repository.ResumeRepository;
    import lombok.RequiredArgsConstructor;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.stereotype.Service;
    import org.springframework.web.multipart.MultipartFile;

    import java.io.File;
    import java.io.IOException;
    import java.time.format.DateTimeFormatter;
    import java.util.List;
    import java.util.UUID;

    @Service
    @RequiredArgsConstructor
    public class ResumeService {

        private final ResumeRepository resumeRepository;
        private final MemberRepository memberRepository;

        @Value("${spring.servlet.file.saveDir}")
        private String saveDir; // 파일 저장 경로

        public Resume uploadResume(Long id, String email, String title, String category, String memo, MultipartFile file) throws IOException {
            Member author = memberRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

            validateFile(file); // 파일 검증

            // 파일 이름 생성: UUID를 사용하여 고유한 파일 이름 생성
            String fileExtension = getFileExtension(file.getOriginalFilename());
            String fileName = UUID.randomUUID().toString() + (fileExtension != null ? "." + fileExtension : "");
            File targetFile = new File(saveDir, fileName);

            // 디렉토리 생성
            File saveDirFile = new File(saveDir);
            if (!saveDirFile.exists()) {
                saveDirFile.mkdirs();
            }

            // 파일 저장
            file.transferTo(targetFile);

            // 새로운 이력서를 생성하고 id를 설정하지 않음
            Resume resume = Resume.builder()
                    .id(id)
                    .title(title)
                    .category(category)
                    .memo(memo)
                    .fileUrl("/files/" + fileName) // 파일 접근 URL 제공
                    .author(author)
                    .build();

            return resumeRepository.save(resume);
        }


        private void validateFile(MultipartFile file) {
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || !originalFilename.matches(".*\\.(pdf|doc|docx|txt)$")) {
                throw new IllegalArgumentException("지원하지 않는 파일 형식입니다.");
            }

            String contentType = file.getContentType();
            if (contentType == null || !List.of("application/pdf", "application/msword", "text/plain").contains(contentType)) {
                throw new IllegalArgumentException("잘못된 파일 형식입니다.");
            }
        }

        private String getFileExtension(String filename) {
            if (filename != null && filename.contains(".")) {
                return filename.substring(filename.lastIndexOf('.') + 1);
            }
            return null;
        }

        public List<ResumeDto> getResumesByUserEmail(String userEmail) {
            List<Resume> resumes = resumeRepository.findByAuthorEmail(userEmail);
            return resumes.stream().map(this::toDto).toList();
        }

        public ResumeDto toDto(Resume resume) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDate = resume.getRegTime() != null ? resume.getRegTime().format(formatter) : null;

            return ResumeDto.builder()
                    .id(resume.getId())
                    .title(resume.getTitle())
                    .category(resume.getCategory())
                    .memo(resume.getMemo())
                    .fileUrl(resume.getFileUrl())
                    .author(resume.getAuthor().getEmail())
                    .date(formattedDate) // 포맷된 날짜 문자열
                    .build();
        }
        //이력력서 업데이트
        public void updateResume(Long id, ResumeDto resumeDto, String userEmail) {
            // 사용자를 이메일을 통해 가져오기
            Member author = memberRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

            // id를 통해 기존 이력서를 가져오기
            Resume resume = resumeRepository.findByIdAndAuthor(id, author)
                    .orElseThrow(() -> new IllegalArgumentException("해당 이력서를 찾을 수 없습니다."));

            // 엔티티를 업데이트
            resume.setTitle(resumeDto.getTitle());
            resume.setCategory(resumeDto.getCategory());
            resume.setMemo(resumeDto.getMemo());
            resume.setFileUrl(resumeDto.getFileUrl());

            // 엔티티를 저장하여 업데이트 적용
            resumeRepository.save(resume);
        }


    }
