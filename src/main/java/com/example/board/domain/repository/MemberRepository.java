package com.example.board.domain.repository;

import com.example.board.domain.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, String> {
    boolean existsById(String id);
    boolean existsByNickname(String nickname);
}
