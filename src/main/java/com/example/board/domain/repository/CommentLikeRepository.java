package com.example.board.domain.repository;

import com.example.board.domain.entity.CommentEntity;
import com.example.board.domain.entity.MemberEntity;
import com.example.board.domain.entity.CommentLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLikeEntity, Long> {
    boolean existsByCommentAndMember(CommentEntity comment, MemberEntity member);
    void deleteByCommentAndMember(CommentEntity comment, MemberEntity member);
}
