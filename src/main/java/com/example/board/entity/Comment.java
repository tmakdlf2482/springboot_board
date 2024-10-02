package com.example.board.entity;

import com.example.board.dto.CommentDto;
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

    // static : class method로 선언됨
    public static Comment createComment(CommentDto dto, Article article) {
        // 예외 발생
        if (dto.getId() != null) { // 댓글에 아이디가 입력 되었다면
            throw new IllegalArgumentException("댓글 생성 실패! 댓글의 id가 없어야 합니다.");
        }
        if (dto.getArticleId() != article.getId()) { // dto.getArticleId()는 게시글 URL의 게시글 번호, article.getId()는 DB에 저장된 게시글 번호
            throw new IllegalArgumentException("댓글 생성 실패! 게시글의 id가 잘못되었습니다.");
        }

        // 엔티티 생성 및 반환
        return new Comment(dto.getId(), article, dto.getNickname(), dto.getBody());
    }

    public void patch(CommentDto dto) {
        // 예외 발생
        if (this.id != dto.getId()) { // DB의 댓글 id와 사용자가 클라이언트에서 서버로 넘긴 id가 다르다면
            throw new IllegalArgumentException("댓글 수정 실패! 잘못된 id가 입력되었습니다.");
        }

        // 객체를 갱신
        // 닉네임 갱신
        if (dto.getNickname() != null) {
            this.nickname = dto.getNickname();
        }

        // 객체를 갱신
        // 댓글 갱신
        if (dto.getBody() != null) {
            this.body = dto.getBody();
        }
    }

}