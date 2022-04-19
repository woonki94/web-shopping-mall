package com.webshoppingmall.entity;

import com.webshoppingmall.dto.MemberFormDto;
import com.webshoppingmall.repository.CartRepository;
import com.webshoppingmall.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class CartTest {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PersistenceContext
    EntityManager entityManager;

    public Member createMember(){
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setAddress("주소");
        memberFormDto.setName("이름");
        memberFormDto.setEmail("email@naver.com");
        memberFormDto.setPassword("1234");
        return Member.createMember(memberFormDto, passwordEncoder);
    }

    @Test
    public void 장바구니_회원_매핑_테스트(){
        Member member = createMember();
        memberRepository.save(member);

        Cart cart = new Cart();
        cart.setMember(member);
        cartRepository.save(cart);

        entityManager.flush();//영속성 컨텍스트 안의 멤버조회가 아니라 진짜 db에서 갖고오는지 확인하기 위해 flush&clear를 해준다.
        entityManager.clear();

        Cart savedCart = cartRepository.findById(cart.getId()).orElseThrow(EntityNotFoundException::new);
        Assertions.assertEquals(savedCart.getMember().getId(), member.getId());
    }

}














