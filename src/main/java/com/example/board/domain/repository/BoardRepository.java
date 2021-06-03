package com.example.board.domain.repository;

import com.example.board.domain.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    Long countByTitleContainingOrContentContaining(String keyword, String keyword1);

    Page<BoardEntity> findAllByTitleContainingOrContentContaining(String keyword1, String keyword2, Pageable paging);
}
