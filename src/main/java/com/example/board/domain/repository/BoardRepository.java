package com.example.board.domain.repository;

import com.example.board.domain.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    Long countByTitleContainingOrContentContaining(String keyword1, String keyword2);

    Long countByTitleContaining(String keyword);

    Long countByContentContaining(String keyword);

    Page<BoardEntity> findAllByTitleContainingOrContentContaining(String title, String content, Pageable paging);

    Page<BoardEntity> findAllByTitleContaining(String title, Pageable paging);

    Page<BoardEntity> findAllByContentContaining(String content, Pageable paging);
}
