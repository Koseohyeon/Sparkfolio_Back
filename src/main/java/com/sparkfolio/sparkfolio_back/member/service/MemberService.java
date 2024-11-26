    package com.sparkfolio.sparkfolio_back.member.service;

    import com.sparkfolio.sparkfolio_back.member.dto.LoginRequestDto;
    import com.sparkfolio.sparkfolio_back.member.dto.MemberDto;
    import com.sparkfolio.sparkfolio_back.member.entity.Member;
    import com.sparkfolio.sparkfolio_back.member.repository.MemberRepository;
    import lombok.RequiredArgsConstructor;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.stereotype.Service;

    @Service
    @RequiredArgsConstructor
    public class MemberService {

        private final MemberRepository memberRepository;
        private final BCryptPasswordEncoder passwordEncoder;

        // 회원가입 처리
        public void registerMember(MemberDto memberDto) {
            String encodedPassword = passwordEncoder.encode(memberDto.getPassword());

            Member member = Member.builder()
                    .name(memberDto.getName())
                    .email(memberDto.getEmail())
                    .password(encodedPassword)
                    .interest(memberDto.getInterest())
                    .profilePhoto(memberDto.getProfilePhoto())
                    .build();

            memberRepository.save(member);
        }
        // 로그인 인증 메서드
        public boolean authenticate(LoginRequestDto loginRequestDto) {
            Member member = memberRepository.findByEmail(loginRequestDto.getEmail())
                    .orElse(null);

            if (member == null) {
                return false; // 회원이 없으면 false 반환
            }

            // 비밀번호 검증
            return passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword());
        }

        // 이메일로 회원 정보 가져오기
        public Member getMemberByEmail(String email) {
            return memberRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        }

        // 회원 정보 조회
        public MemberDto getMemberProfile(String email) {
            Member member = memberRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

            return MemberDto.builder()
                    .name(member.getName())
                    .email(member.getEmail())
                    .interest(member.getInterest())
                    .profilePhoto(member.getProfilePhoto())
                    .build();
        }

    }