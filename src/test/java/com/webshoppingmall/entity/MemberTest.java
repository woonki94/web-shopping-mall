package com.webshoppingmall.entity;

import com.webshoppingmall.dto.MemberFormDto;
import com.webshoppingmall.repository.MemberRepository;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class MemberTest {
    
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PersistenceContext
    EntityManager em;



    @Test
    @WithMockUser(username = "gildong", roles = "USER")
    public void Auditing_테스트(){
        Member newMember = new Member();
        memberRepository.save(newMember);
        
        em.flush();
        em.clear();
        
        Member member = memberRepository.findById(newMember.getId()).orElseThrow(EntityNotFoundException::new);

        System.out.println("member.getRegTime() = " + member.getRegTime());
        System.out.println("member.getUpdateTime() = " + member.getUpdateTime());
        System.out.println("member.getCreatedBy() = " + member.getCreatedBy());
        System.out.println("member.getModifiedBy() = " + member.getModifiedBy());
    }
}
