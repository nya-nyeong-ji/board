package com.example.board.controller;

import com.example.board.dto.CommentDto;
import com.example.board.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class CommentController {

    CommentService commentService;

    @PostMapping("/post/{no}/comment")
    public String write(CommentDto commentDto){
        commentService.saveComment(commentDto);

        return "redirect:/post/{no}";
    }

    @GetMapping("/post/{boardNo}/comment/{commentNo}")
    public String edit(@PathVariable("commentNo") Long no, Model model){
        CommentDto commentDto = commentService.getComment(no);

        model.addAttribute("commentDto", commentDto);

        return "/comment/update.html";
    }

    @PutMapping("/post/{boardNo}/comment/{commentNo}")
    public String update(CommentDto commentDto){
        commentService.saveComment(commentDto);

        return "redirect:/post/{boardNo}";
    }

    @DeleteMapping("/post/{boardNo}/comment/{commentNo}")
    public String delete(@PathVariable("commentNo") Long id){
        commentService.deleteComment(id);

        return "redirect:/post/{boardNo}";
    }
}
