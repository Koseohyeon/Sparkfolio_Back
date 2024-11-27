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
            if (memberRepository.findByEmail(memberDto.getEmail()).isPresent()) {
                throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
            }

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

        public void updateMember(String email, MemberDto updatedInfo) {
            // 이메일을 기반으로 사용자 검색
            Member member = memberRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

            // 사용자 정보 업데이트
            member.setName(updatedInfo.getName());
            member.setInterest(updatedInfo.getInterest());

            // 저장된 데이터베이스 업데이트
            memberRepository.save(member);
        }
    }