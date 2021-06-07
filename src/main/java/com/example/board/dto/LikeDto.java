package com.example.board.dto;

import com.example.board.domain.entity.LikeEntity;
import lombok.Builder;

public class LikeDto {
    private Long id;
    private BoardDto board;
    private MemberDto member;

    public LikeEntity toEntity(){
        LikeEntity likeEntity = LikeEntity.builder()
                .id(id)
                .board(board.toEntity())
                .member(member.toEntity())
                .build();
        return likeEntity;
    }

    @Builder
    public LikeDto(Long id, BoardDto board, MemberDto member){
        this.id = id;
        this.board = board;
        this.member = member;
    }
}
