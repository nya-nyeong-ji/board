package com.example.board.service;

import com.example.board.domain.entity.LikeEntity;
import com.example.board.domain.repository.LikeRepository;
import com.example.board.dto.BoardDto;
import com.example.board.dto.LikeDto;
import com.example.board.dto.MemberDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@AllArgsConstructor
@Service
public class LikeService {
    LikeRepository likeRepository;

    @Transactional
    public void clickLike(BoardDto board, MemberDto member){
        LikeEntity like = LikeDto.builder()
                .board(board)
                .member(member)
                .build().toEntity();
        if (likeRepository.existsByBoardAndMember(board.toEntity(), member.toEntity())){
            likeRepository.deleteByBoardAndMember(board.toEntity(), member.toEntity());
        } else{
            likeRepository.save(like).getId();
        }
    }
}
