package com.example.board.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB가 id를 자동 생성 어노테이션!, 겹치지 않게 자동으로 넣어줌
    private Long id;

    @ManyToOne // 해당 댓글 엔티티 여러개가 1개의 Article에 연관된다! (다대일 관계 설정)
    @JoinColumn(name = "article_id") // 테이블에서 연결된 대상의 정보를 가져야 하는데 그 대상 정보의 컬럼의 이름 (FK이름)
    private Article article; // 댓글의 부모 게시글, 여러개의 댓글이 1개의 게시글에 달림

    @Column
    private String nickname;

    @Column
    private String body; // 댓글 내용

}