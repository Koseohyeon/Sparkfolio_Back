    package com.sparkfolio.sparkfolio_back.resume.controller;

    import com.sparkfolio.sparkfolio_back.resume.dto.ResumeDto;
    import com.sparkfolio.sparkfolio_back.resume.entity.Resume;
    import com.sparkfolio.sparkfolio_back.resume.service.ResumeService;
    import jakarta.servlet.http.HttpSession;
    import lombok.RequiredArgsConstructor;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.multipart.MultipartFile;

    import java.util.List;

    @RestController
    @RequiredArgsConstructor
    @RequestMapping("/api/resume")
    public class ResumeController {

        private final ResumeService resumeService;

        @PostMapping("/upload")
        public ResponseEntity<Object> uploadResume(
                @RequestParam(required = false) Long id, // id를 선택 사항으로 변경
                @RequestParam String title,
                @RequestParam(required = false) String category,
                @RequestParam(required = false) String memo,
                @RequestParam("file") MultipartFile file,
                HttpSession session) {

            String userEmail = (String) session.getAttribute("user");
            if (userEmail == null) {
                return ResponseEntity.status(401).body("로그인이 필요합니다.");
            }

            try {
                Resume resume = resumeService.uploadResume(id,userEmail, title, category, memo, file);
                return ResponseEntity.ok("이력서 업로드 성공: " + resume.getId());
            } catch (Exception e) {
                return ResponseEntity.status(500).body("이력서 업로드 실패: " + e.getMessage());
            }
        }

        @GetMapping("/list")
        public ResponseEntity<Object> getResumeList(HttpSession session) {
            String userEmail = (String) session.getAttribute("user");
            if (userEmail == null) {
                return ResponseEntity.status(401).body("로그인이 필요합니다.");
            }

            try {
                List<ResumeDto> resumes = resumeService.getResumesByUserEmail(userEmail);
                return ResponseEntity.ok(resumes);
            } catch (Exception e) {
                return ResponseEntity.status(500).body("이력서 리스트 불러오기 실패: " + e.getMessage());
            }
        }
        //이력서 업데이트
        @PutMapping("/{id}")
        public ResponseEntity<String> updateResume(
                @PathVariable Long id,
                @RequestBody ResumeDto resumeDto,
                HttpSession session) {

            String userEmail = (String) session.getAttribute("user");
            if (userEmail == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
            }

            if (id == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유효한 식별자가 필요합니다.");
            }

            try {
                resumeService.updateResume(id, resumeDto, userEmail);
                return ResponseEntity.ok("이력서가 성공적으로 업데이트되었습니다.");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이력서 업데이트 중 오류가 발생했습니다: " + e.getMessage());
            }
        }



    }
