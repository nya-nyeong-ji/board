package com.example.board.domain.repository;

import com.example.board.domain.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByBoardId(Long BoardId);

    @Modifying(clearAutomatically = true)
    @Query("delete from CommentEntity c where c.boardId = :boardId")
    void deleteAllByBoardId(@Param("boardId") Long boardId);

    Page<CommentEntity> findAllByBoardId(Long boardId, Pageable paging);

    Long countByBoardId(Long BbardId);
}
