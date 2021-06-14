package com.example.board.service;

import com.example.board.domain.entity.CommentLikeEntity;
import com.example.board.domain.repository.CommentLikeRepository;
import com.example.board.dto.CommentDto;
import com.example.board.dto.MemberDto;
import com.example.board.dto.CommentLikeDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@AllArgsConstructor
@Service
public class CommentLikeService {

    CommentLikeRepository commentLikeRepository;

    @Transactional
    public void clickRecommend(CommentDto comment, MemberDto member){
        CommentLikeEntity recommend = CommentLikeDto.builder()
                .comment(comment)
                .member(member)
                .build().toEntity();

        if (commentLikeRepository.existsByCommentAndMember(comment.toEntity(), member.toEntity())){
            commentLikeRepository.deleteByCommentAndMember(comment.toEntity(), member.toEntity());
        } else
        {
            commentLikeRepository.save(recommend);
        }
    }
}
