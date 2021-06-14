package com.example.board.service;

import com.example.board.domain.entity.BoardLikeEntity;
import com.example.board.domain.repository.BoardLikeRepository;
import com.example.board.dto.BoardDto;
import com.example.board.dto.BoardLikeDto;
import com.example.board.dto.MemberDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@AllArgsConstructor
@Service
public class BoardLikeService {
    BoardLikeRepository boardLikeRepository;

    @Transactional
    public void clickLike(BoardDto board, MemberDto member){
        BoardLikeEntity like = BoardLikeDto.builder()
                .board(board)
                .member(member)
                .build().toEntity();
        if (boardLikeRepository.existsByBoardAndMember(board.toEntity(), member.toEntity())){
            boardLikeRepository.deleteByBoardAndMember(board.toEntity(), member.toEntity());
        } else{
            boardLikeRepository.save(like).getId();
        }
    }
}
