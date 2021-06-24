package com.example.board.domain.repository;

import com.example.board.domain.entity.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, String> {
    boolean existsById(String id);
    boolean existsByNickname(String nickname);

    Page<MemberEntity> findAll(Pageable paging);
    long count();

    Page<MemberEntity> findAllByIdContaining(String id, Pageable paging);
    Long countByIdContaining(String id);
}
