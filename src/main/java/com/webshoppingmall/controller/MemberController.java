package com.webshoppingmall.controller;

import com.webshoppingmall.dto.MemberFormDto;
import com.webshoppingmall.entity.Member;
import com.webshoppingmall.repository.MemberRepository;
import com.webshoppingmall.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping(value = "/members")
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping(value = "/new")
    public String memberForm(Model model){
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "members/memberForm";
    }

//    @PostMapping(value = "/new")
//    public String memberForm(MemberFormDto memberFormDto){
//        Member member = Member.createMember(memberFormDto, passwordEncoder);
//        memberService.saveMember(member);
//        return "redirect:/";
//    }

    @PostMapping(value = "/new")
    public String newMember(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "members/memberForm";
        }
        try {
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);
        }catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "members/memberForm";
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginMember(){
        return "/members/memberLoginForm";
    }
    @GetMapping("/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg", "아이디 또느 비밀번호를 확인해주세요");
        return "/members/memberLoginForm";

    }

}
