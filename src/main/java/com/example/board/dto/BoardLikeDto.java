package com.example.board.dto;

import com.example.board.domain.entity.BoardLikeEntity;
import lombok.Builder;

public class BoardLikeDto {
    private Long id;
    private BoardDto board;
    private MemberDto member;

    public BoardLikeEntity toEntity(){
        BoardLikeEntity boardLikeEntity = BoardLikeEntity.builder()
                .id(id)
                .board(board.toEntity())
                .member(member.toEntity())
                .build();
        return boardLikeEntity;
    }

    @Builder
    public BoardLikeDto(Long id, BoardDto board, MemberDto member){
        this.id = id;
        this.board = board;
        this.member = member;
    }
}
