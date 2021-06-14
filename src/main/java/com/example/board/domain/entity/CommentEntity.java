package com.example.board.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "Comment")
public class CommentEntity extends TimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long boardId;

    @Column(length = 10, nullable = false)
    private String writer;

    @Column(length = 100, nullable = false)
    private String content;

    @Formula("(select count(1) from comment_likes as l where l.comment_id = id)")
    private Long totalLike;

    @Builder
    public CommentEntity(Long id, Long boardId, String writer, String content) {
        this.id = id;
        this.boardId = boardId;
        this.writer = writer;
        this.content = content;
    }
}
