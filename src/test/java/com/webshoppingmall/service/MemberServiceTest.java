package com.webshoppingmall.service;

import com.webshoppingmall.constant.Role;
import com.webshoppingmall.dto.MemberFormDto;
import com.webshoppingmall.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    PasswordEncoder passwordEncoder;

    public Member createMember(){
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setAddress("주소");
        memberFormDto.setName("이름");
        memberFormDto.setEmail("email@naver.com");
        memberFormDto.setPassword("1234");
        return Member.createMember(memberFormDto, passwordEncoder);
    }

    @Test
    void 회원_가입_테스트() {
        Member member = createMember();
        Member savedMember = memberService.saveMember(member);
        Assertions.assertThat(savedMember.getAddress()).isEqualTo(member.getAddress());
        Assertions.assertThat(savedMember.getEmail()).isEqualTo(member.getEmail());
        Assertions.assertThat(savedMember.getName()).isEqualTo(member.getName());
        Assertions.assertThat(savedMember.getRole()).isEqualTo(member.getRole());
        Assertions.assertThat(savedMember.getPassword()).isEqualTo(member.getPassword());
    }

    @Test
    void 중복_회원_테스트(){
        Member member1 = createMember();
        Member member2 = createMember();
        memberService.saveMember(member1);

        Throwable e = assertThrows(IllegalStateException.class, ()-> {memberService.saveMember(member2);});

        assertEquals("이미 존재하는 이메일 입니다.", e.getMessage());
    }
}