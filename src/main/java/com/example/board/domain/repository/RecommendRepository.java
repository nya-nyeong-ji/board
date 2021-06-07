package com.example.board.domain.repository;

import com.example.board.domain.entity.CommentEntity;
import com.example.board.domain.entity.MemberEntity;
import com.example.board.domain.entity.RecommendEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendRepository extends JpaRepository<RecommendEntity, Long> {
    boolean existsByCommentAndMember(CommentEntity comment, MemberEntity member);
    void deleteByCommentAndMember(CommentEntity comment, MemberEntity member);
}
