package com.example.board.controller;

import com.example.board.dto.MemberDto;
import com.example.board.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Map;

@Controller
@AllArgsConstructor
public class MemberController {
    private MemberService memberService;

    //로그인, 회원가입 페이지 송출
    @GetMapping("/")
    public String index(){return "/member/index";}

    //화원가입 페이지
    @GetMapping("/signup")
    public String dispSignup(){return "member/signup";}

    //회원가입 실행
    @PostMapping("/signup")
    public String execSignup(@Valid MemberDto memberDto, Errors errors, Model model){

        if (memberService.checkIdDuplicate(memberDto)){
            model.addAttribute("memberDto", memberDto);
            model.addAttribute("valid_id", "이미 존재하는 ID입니다.");

            return "member/signup";
        }

        if (memberService.checkNicknameDuplicate(memberDto)){
            model.addAttribute("memberDto", memberDto);
            model.addAttribute("valid_nickname", "이미 존재하는 닉네임입니다.");

            return "member/signup";
        }

        if(errors.hasErrors()){
            model.addAttribute("memberDto", memberDto);

            Map<String, String> validatorResult = memberService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }

            return "member/signup";
        }
        memberService.joinUser(memberDto);

        return "redirect:/login";
    }

    //로그인
    @GetMapping("/login")
    public String dispLogin(){return "member/login";}

    //접근 거부 페이지
    @GetMapping("/denied")
    public String dispDenied(){return "member/denied";}
}
