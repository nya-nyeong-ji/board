package com.example.board.dto;

import com.example.board.domain.entity.CommentLikeEntity;
import lombok.Builder;

public class CommentLikeDto {
    private Long id;
    private CommentDto comment;
    private MemberDto member;

    public CommentLikeEntity toEntity(){
        CommentLikeEntity commentLikeEntity = CommentLikeEntity.builder()
                .id(id)
                .comment(comment.toEntity())
                .member(member.toEntity())
                .build();
        return commentLikeEntity;
    }

    @Builder
    public CommentLikeDto(Long id, CommentDto comment, MemberDto member){
        this.id = id;
        this.comment = comment;
        this.member = member;
    }
}
