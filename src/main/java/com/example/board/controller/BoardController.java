package com.example.board.controller;

import com.example.board.dto.BoardDto;
import com.example.board.dto.CommentDto;
import com.example.board.dto.MemberDto;
import com.example.board.service.BoardService;
import com.example.board.service.CommentService;
import com.example.board.service.MemberService;
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
    private MemberService memberService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page",defaultValue = "1") Integer pageNum, @RequestParam(value = "keyword", defaultValue = "") String keyword, @RequestParam(value = "search_type", defaultValue = "both") String searchType, @RequestParam(value = "type", defaultValue = "date") String type){
        List<BoardDto> boardList;
        Integer[] pageList;

        if (keyword.equals("")){
            boardList = boardService.getPostList(pageNum, type);
            pageList = boardService.getPageList(pageNum);
        }

        else{
            boardList = boardService.getPostlist(pageNum, searchType, keyword, type);
            pageList = boardService.getPageList(pageNum, searchType, keyword);
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";

        if(principal instanceof UserDetails){
            username = ((UserDetails)principal).getUsername();
        } else{
            username = principal.toString();
        }

        model.addAttribute("boardList", boardList);
        model.addAttribute("pageList", pageList);
        model.addAttribute("username", username);
        model.addAttribute("keyword", keyword);

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
    public String detail(@PathVariable("no") Long no, @RequestParam(value="page", defaultValue = "1") Integer pageNum, @RequestParam(value = "type", defaultValue = "date") String type, Model model){
        BoardDto boardDto = boardService.getPost(no);
        List<CommentDto> commentList = commentService.getCommentListByBoardId(no, pageNum, type);
        Integer[] pageList = commentService.getPageList(no, pageNum);

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
        model.addAttribute("pageList", pageList);

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
