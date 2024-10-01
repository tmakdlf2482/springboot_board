package com.example.board.repository;

import com.example.board.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 특정 게시글의 모든 댓글 조회
    // 참고로 :articleId와 Long articleId에서 articleId는 이름이 같아야 매칭이 됨
    @Query(value = "SELECT * FROM comment WHERE article_id=:articleId", nativeQuery = true)
    List<Comment> findByArticleId(Long articleId); // articleId를 입력하면 Comment의 묶음(List)을 반환

    // 특정 닉네임의 모든 댓글 조회
    List<Comment> findByNickname(String nickname); // 입력값으로 nickname을 넘겨주면, Comment의 묶음(List)을 반환
}