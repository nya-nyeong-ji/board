package com.example.board.controller;

import com.example.board.dto.BoardDto;
import com.example.board.dto.CommentDto;
import com.example.board.service.BoardService;
import com.example.board.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class BoardController {
    private BoardService boardService;
    private CommentService commentService;

    @GetMapping("/list")
    public String list(Model model){
        List<BoardDto> boardList = boardService.getPostList();

        model.addAttribute("boardList", boardList);
        return "board/list.html";
    }

    @GetMapping("/post")
    public String write(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String id = "";

        if(principal instanceof UserDetails){
            id = ((UserDetails)principal).getUsername();
        } else{
            id = principal.toString();
        }

        model.addAttribute("id", id);
        return "board/write.html";
    }

    @PostMapping("/post")
    public String write(BoardDto boardDto){
        boardService.savePost(boardDto);

        return "redirect:/list";
    }

    @GetMapping("/post/{no}")
    public String detail(@PathVariable("no") Long no, Model model){
        BoardDto boardDto = boardService.getPost(no);
        List<CommentDto> commentList = commentService.getCommentListByBoardId(no);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String id = "";

        if(principal instanceof UserDetails){
            id = ((UserDetails)principal).getUsername();
        } else{
            id = principal.toString();
        }

        model.addAttribute("id", id);
        model.addAttribute("boardDto", boardDto);
        model.addAttribute("commentList", commentList);

        return "board/detail.html";
    }

    @GetMapping("/post/edit/{no}")
    public String edit(@PathVariable("no") Long no, Model model){
        BoardDto boardDto = boardService.getPost(no);

        model.addAttribute("boardDto", boardDto);

        return "board/update.html";
    }

    @PutMapping("/post/edit/{no}")
    public String update(BoardDto boardDto){
        boardService.savePost(boardDto);

        return "redirect:/list";
    }

    @DeleteMapping("/post/{no}")
    public String delete(@PathVariable("no") Long id){
        boardService.deletePost(id);
        commentService.deleteCommentByBoardId(id);

        return "redirect:/list";
    }
}
