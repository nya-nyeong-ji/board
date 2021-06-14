package com.example.board.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "comment_likes")
public class CommentLikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private CommentEntity comment;

    @ManyToOne(fetch = FetchType.LAZY)
    private MemberEntity member;

    @Builder
    public CommentLikeEntity(Long id, CommentEntity comment, MemberEntity member){
        this.id = id;
        this.comment = comment;
        this.member = member;
    }
}
