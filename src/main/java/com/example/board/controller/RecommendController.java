package com.example.board.controller;

import com.example.board.dto.CommentDto;
import com.example.board.service.CommentService;
import com.example.board.service.MemberService;
import com.example.board.service.RecommendService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class RecommendController {
    private RecommendService recommendService;
    private CommentService commentService;
    private MemberService memberService;

    @PostMapping("/post/{postId}/recommend/{commentId}")
    public String clickRecommend(@PathVariable("postId") Long boardId, @PathVariable("commentId") Long commentId){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String id = "";

        if(principal instanceof UserDetails){
            id = ((UserDetails)principal).getUsername();
        } else{
            id = principal.toString();
        }

        CommentDto comment = commentService.getComment(commentId);
        recommendService.clickRecommend(commentService.getComment(commentId), memberService.getMember(id));

        return "redirect:/post/" + boardId;
    }

}
