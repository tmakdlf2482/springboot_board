package com.example.board.dto;

import com.example.board.entity.Comment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor // 모든 생성자 자동완성
@NoArgsConstructor // 디폴트 생성자 자동완성
@Getter
@ToString
public class CommentDto {

    private Long id; // 댓글 아이디
    @JsonProperty("article_id") // JSON에서 데이터를 받아올 때, article_id키로 JSON형식으로 클라이언트에서 데이터를 보낼때 자동으로 articleId에 매핑됨
    private Long articleId; // 댓글가 포함된 게시글의 아이디
    private String nickname; // 댓글 닉네임
    private String body; // 댓글 내용

    // static 키워드가 클래스 메소드에 선언할 때 사용
    // Entity -> DTO로 변환하는 메소드
    public static CommentDto createCommentDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getArticle().getId(), comment.getNickname(), comment.getBody());
    }

}