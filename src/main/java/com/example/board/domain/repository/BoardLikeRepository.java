package com.example.board.domain.repository;

import com.example.board.domain.entity.BoardEntity;
import com.example.board.domain.entity.BoardLikeEntity;
import com.example.board.domain.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardLikeRepository extends JpaRepository<BoardLikeEntity, Long> {
    boolean existsByBoardAndMember(BoardEntity board, MemberEntity member);
    void deleteByBoardAndMember(BoardEntity board, MemberEntity member);
}
