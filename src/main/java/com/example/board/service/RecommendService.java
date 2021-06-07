package com.example.board.service;

import com.example.board.domain.entity.RecommendEntity;
import com.example.board.domain.repository.RecommendRepository;
import com.example.board.dto.CommentDto;
import com.example.board.dto.MemberDto;
import com.example.board.dto.RecommendDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@AllArgsConstructor
@Service
public class RecommendService {

    RecommendRepository recommendRepository;

    @Transactional
    public void clickRecommend(CommentDto comment, MemberDto member){
        RecommendEntity recommend = RecommendDto.builder()
                .comment(comment)
                .member(member)
                .build().toEntity();

        if (recommendRepository.existsByCommentAndMember(comment.toEntity(), member.toEntity())){
            recommendRepository.deleteByCommentAndMember(comment.toEntity(), member.toEntity());
        } else
        {
            recommendRepository.save(recommend);
        }
    }
}
