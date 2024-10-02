package com.example.board.service;

import com.example.board.dto.CommentDto;
import com.example.board.entity.Article;
import com.example.board.entity.Comment;
import com.example.board.repository.ArticleRepository;
import com.example.board.repository.CommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArticleRepository articleRepository;

    public List<CommentDto> comments(Long articleId) {
        // 조회: 댓글 목록
//        List<Comment> comments = commentRepository.findByArticleId(articleId);

        // 변환: 엔티티 -> DTO
//        List<CommentDto> dtos = new ArrayList<CommentDto>();
//
//        for (int i = 0; i < comments.size(); i++) {
//            Comment c = comments.get(i);
//
//            CommentDto dto = CommentDto.createCommentDto(c);
//
//            dtos.add(dto);
//        }

        // 위의 for문 말고 stream으로 코드 리팩토링

        // 반환
        return commentRepository.findByArticleId(articleId)
                .stream()
                .map(comment -> CommentDto.createCommentDto(comment)) // Entity -> DTO로 변환
                .collect(Collectors.toList());
    }

    @Transactional // 댓글 생성은 DB에 접근하여 DB를 변경하기 때문에 중간에 문제가 생기면 롤백되게 처리
    public CommentDto create(Long articleId, CommentDto dto) {
        // 게시글 조회(게시글이 있어야 댓글이 어디에 달릴 지 알수있음) 및 예외 발생
        // .orElseThrow()는 만약에 articleId 맞는 게시글이 없다면 예외를 발생
        // 만약 articleId에 맞는 게시글이 없어 예외가 발생되면 밑에줄들은 더이상 실행되지 않음
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패! 대상 게시글이 없습니다."));

        // 댓글 엔티티 생성
        Comment comment = Comment.createComment(dto, article); // 매개변수 dto에는 JSON 데이터가 담겨있음

        // 댓글 엔티티를 DB로 저장
        Comment created = commentRepository.save(comment);

        // Entity -> DTO로 변경하여 반환
        return CommentDto.createCommentDto(created);
    }

    @Transactional // 댓글 수정은 DB에 접근하여 DB를 변경하기 때문에 중간에 문제가 생기면 롤백되게 처리
    public CommentDto update(Long id, CommentDto dto) {
        // 댓글 조회 및 예외 발생(만약 댓글이 없는 경우 등등)
        Comment target = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패! 대상 댓글이 없습니다."));

        // 댓글 수정
        target.patch(dto);

        // DB로 갱신
        Comment updated = commentRepository.save(target);

        // 댓글 Entity를 DTO로 변환 및 반환
        return CommentDto.createCommentDto(updated);
    }

    @Transactional // 댓글 삭제는 DB에 접근하여 DB를 변경하기 때문에 중간에 문제가 생기면 롤백되게 처리
    public CommentDto delete(Long id) {
        // 댓글 조회 및 예외 발생(만약 댓글이 없는 경우 등등)
        Comment target = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패! 대상 댓글이 없습니다."));

        // DB에서 댓글 삭제
        commentRepository.delete(target);

        // 삭제 댓글을 DTO로 반환
        return CommentDto.createCommentDto(target);
    }

}