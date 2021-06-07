package com.example.board.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "Recommend")
public class RecommendEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private CommentEntity comment;

    @ManyToOne(fetch = FetchType.LAZY)
    private MemberEntity member;

    @Builder
    public RecommendEntity(Long id, CommentEntity comment, MemberEntity member){
        this.id = id;
        this.comment = comment;
        this.member = member;
    }
}
