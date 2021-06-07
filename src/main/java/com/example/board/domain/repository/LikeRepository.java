package com.example.board.domain.repository;

import com.example.board.domain.entity.BoardEntity;
import com.example.board.domain.entity.LikeEntity;
import com.example.board.domain.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    boolean existsByBoardAndMember(BoardEntity board, MemberEntity member);
    void deleteByBoardAndMember(BoardEntity board, MemberEntity member);
}
