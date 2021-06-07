package com.example.board.dto;

import com.example.board.domain.entity.RecommendEntity;
import lombok.Builder;

public class RecommendDto {
    private Long id;
    private CommentDto comment;
    private MemberDto member;

    public RecommendEntity toEntity(){
        RecommendEntity recommendEntity = RecommendEntity.builder()
                .id(id)
                .comment(comment.toEntity())
                .member(member.toEntity())
                .build();
        return recommendEntity;
    }

    @Builder
    public RecommendDto(Long id, CommentDto comment, MemberDto member){
        this.id = id;
        this.comment = comment;
        this.member = member;
    }
}
