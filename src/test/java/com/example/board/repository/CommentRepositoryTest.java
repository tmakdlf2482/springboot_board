package com.example.board.repository;

import com.example.board.entity.Article;
import com.example.board.entity.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // JPA와 연동한 테스트!
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Test
    @DisplayName("특정 게시글의 모든 댓글 조회") // 테스트 결과에 보여줄 이름
    void findByArticleId() {
        /* Case 1: 4번 게시글의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            Long articleId = 4L;

            // 실제 수행
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 예상하기
            Article article = new Article(articleId, "당신의 인생 영화는?", "댓글 ㄱ");
            Comment a = new Comment(1L, article, "Park", "굳 윌 헌팅");
            Comment b = new Comment(2L, article, "Kim", "아이 엠 샘");
            Comment c = new Comment(3L, article, "Choi", "쇼생크의 탈출");

            List<Comment> expected = new ArrayList<Comment>(Arrays.asList(a, b, c));

            // 검증
            assertEquals(expected.toString(), comments.toString(), "4번 글의 모든 댓글을 출력!");
        }

        /* Case 2: 1번 게시글의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            Long articleId = 1L;

            // 실제 수행
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 예상하기
            List<Comment> expected = new ArrayList<Comment>(Arrays.asList()); // 비어있는 값이 만들어질 것임

            // 검증
            assertEquals(expected.toString(), comments.toString(), "1번 글은 댓글이 없음!");
        }

        /* Case 3: 9번 게시글의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            Long articleId = 9L;

            // 실제 수행
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 예상하기
            List<Comment> expected = new ArrayList<Comment>(Arrays.asList()); // 비어있는 값이 만들어질 것임

            // 검증
            assertEquals(expected.toString(), comments.toString(), "9번 글은 댓글이 없음!");
        }

        /* Case 4: 9999번 게시글의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            Long articleId = 9999L;

            // 실제 수행
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 예상하기
            List<Comment> expected = new ArrayList<Comment>(Arrays.asList()); // 비어있는 값이 만들어질 것임

            // 검증
            assertEquals(expected.toString(), comments.toString(), "9999번 글은 댓글이 없음!");
        }

        /* Case 5: -1번 게시글의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            Long articleId = -1L;

            // 실제 수행
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 예상하기
            List<Comment> expected = new ArrayList<Comment>(Arrays.asList()); // 비어있는 값이 만들어질 것임

            // 검증
            assertEquals(expected.toString(), comments.toString(), "-1번 글은 댓글이 없음!");
        }
    }

    @Test
    @DisplayName("특정 닉네임의 모든 댓글 조회")
    void findByNickname() {
        /* Case 1: "Park"의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            String nickname = "Park";

            // 실제 수행
            List<Comment> comments = commentRepository.findByNickname(nickname);

            // 예상하기
            Comment a = new Comment(1L, new Article(4L, "당신의 인생 영화는?", "댓글 ㄱ"), nickname, "굳 윌 헌팅");
            Comment b = new Comment(4L, new Article(5L, "당신의 소울 푸드는?", "댓글 ㄱㄱ"), nickname, "치킨");
            Comment c = new Comment(7L, new Article(6L, "당신의 취미는?", "댓글 ㄱㄱㄱ"), nickname, "조깅");

            List<Comment> expected = new ArrayList<Comment>(Arrays.asList(a, b, c));

            // 검증
            assertEquals(expected.toString(), comments.toString(), "Park의 모든 댓글을 출력!");
        }

        /* Case 2: "Kim"의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            String nickname = "Kim";

            // 실제 수행
            List<Comment> comments = commentRepository.findByNickname(nickname);

            // 예상하기
            Comment a = new Comment(2L, new Article(4L, "당신의 인생 영화는?", "댓글 ㄱ"), nickname, "아이 엠 샘");
            Comment b = new Comment(5L, new Article(5L, "당신의 소울 푸드는?", "댓글 ㄱㄱ"), nickname, "샤브샤브");
            Comment c = new Comment(8L, new Article(6L, "당신의 취미는?", "댓글 ㄱㄱㄱ"), nickname, "유튜브");

            List<Comment> expected = new ArrayList<Comment>(Arrays.asList(a, b, c));

            // 검증
            assertEquals(expected.toString(), comments.toString(), "Kim의 모든 댓글을 출력!");
        }

        /* Case 3: null의 모든 댓글 조회 */
        {
            // 실제 수행
            List<Comment> comments = commentRepository.findByNickname(null);

            // 예상하기
            List<Comment> expected = new ArrayList<Comment>(Arrays.asList());

            // 검증
            assertEquals(expected, comments, "null의 모든 댓글을 출력!");
        }

        /* Case 4: ""의 모든 댓글 조회 */
        {
            // 실제 수행
            List<Comment> comments = commentRepository.findByNickname("");

            // 예상하기
            List<Comment> expected = new ArrayList<Comment>(Arrays.asList());

            // 검증
            assertEquals(expected, comments, "\"\"의 모든 댓글을 출력!");
        }

        /* Case 5: "i"의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            String nickname = "i";

            // 실제 수행
            List<Comment> comments = commentRepository.findByNickname(nickname);

            // 예상하기
            List<Comment> expected = new ArrayList<Comment>(Arrays.asList());

            // 검증
            assertEquals(expected.toString(), comments.toString(), "i의 모든 댓글을 출력!");
        }
    }

}