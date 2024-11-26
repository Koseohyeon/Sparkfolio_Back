package com.sparkfolio.sparkfolio_back.member.controller;

import com.sparkfolio.sparkfolio_back.member.dto.LoginRequestDto;
import com.sparkfolio.sparkfolio_back.member.dto.MemberDto;
import com.sparkfolio.sparkfolio_back.member.entity.Member;
import com.sparkfolio.sparkfolio_back.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    // 회원가입 API
    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> signup(@RequestBody MemberDto memberDto) {
        try {
            memberService.registerMember(memberDto);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "회원가입 성공!");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "회원가입 실패: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    // 로그인 API
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequestDto loginRequestDto, HttpSession session) {
        boolean isAuthenticated = memberService.authenticate(loginRequestDto);
        if (isAuthenticated) {
            Member member = memberService.getMemberByEmail(loginRequestDto.getEmail()); // 사용자 정보 가져오기
            session.setAttribute("user", member.getEmail()); // 세션에 이메일 저장

            // 필요한 사용자 정보 반환
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "로그인 성공!");
            response.put("userData", new MemberDto(member)); // MemberDto로 변환 후 반환

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(Map.of("success", false, "message", "로그인 실패: 이메일 또는 비밀번호가 잘못되었습니다."));
        }
    }

    // 로그아웃 API
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return ResponseEntity.ok("로그아웃 성공");
    }

    @GetMapping("/profile")
    public ResponseEntity<MemberDto> getProfile(HttpSession session) {
        String userEmail = (String) session.getAttribute("user");
        if (userEmail == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        MemberDto memberDto = memberService.getMemberProfile(userEmail);
        return ResponseEntity.ok(memberDto);
    }
}
