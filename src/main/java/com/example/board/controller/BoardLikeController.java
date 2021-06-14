package com.example.board.controller;

import com.example.board.service.BoardService;
import com.example.board.service.BoardLikeService;
import com.example.board.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class BoardLikeController {

    private BoardLikeService boardLikeService;
    private BoardService boardService;
    private MemberService memberService;

    @PostMapping("/post/{postId}/like")
    public String clickLike(@PathVariable("postId") Long boardId){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String id = "";

        if(principal instanceof UserDetails){
            id = ((UserDetails)principal).getUsername();
        } else{
            id = principal.toString();
        }

        boardLikeService.clickLike(boardService.getPost(boardId), memberService.getMember(id));

        return "redirect:/post/" + boardId;
    }
}
