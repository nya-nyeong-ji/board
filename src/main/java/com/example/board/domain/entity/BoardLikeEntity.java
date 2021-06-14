package com.example.board.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "board_likes")
public class BoardLikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private BoardEntity board;

    @ManyToOne(fetch = FetchType.LAZY)
    private MemberEntity member;

    @Builder
    public BoardLikeEntity(Long id, BoardEntity board, MemberEntity member){
        this.id = id;
        this.board = board;
        this.member = member;
    }
}
