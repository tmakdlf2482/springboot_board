package com.example.board.service;

import com.example.board.dto.ArticleForm;
import com.example.board.entity.Article;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // 해당 클래스는 스프링 부트와 연동한 통합 테스트 수행
class ArticleServiceTest {

    @Autowired ArticleService articleService;

    @Test
    void index() {
        // 예상 시나리오
        Article a = new Article(1L, "가가가가", "1111");
        Article b = new Article(2L, "나나나나", "2222");
        Article c = new Article(3L, "다다다다", "3333");

        List<Article> expected = new ArrayList<Article>(Arrays.asList(a, b, c));

        // 실제 결과
        List<Article> articles = articleService.index();

        // 예상 시나리오와 실제 결과를 비교
        assertEquals(expected.toString(), articles.toString());
    }

    @Test
    void show____존재하는_id_입력() {
        // 예상 시나리오
        Long id = 1L;
        Article expected = new Article(id, "가가가가", "1111");

        // 실제 결과
        Article article = articleService.show(id);

        // 예상 시나리오와 실제 결과를 비교
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    void show_실패____존재하지_않는_id_입력() {
        // 예상 시나리오
        Long id = -1L;
        Article expected = null;

        // 실제 결과
        Article article = articleService.show(id);

        // 예상 시나리오와 실제 결과를 비교
        assertEquals(expected, article);
    }

    @Test
    @Transactional // 테스트 종료 후 변경된 데이터를 롤백(처음으로 되돌림) 처리
    void create_성공____title과_content만_있는_dto_입력() {
        // 예상 시나리오
        String title = "라라라라";
        String content = "4444";
        Article expected = new Article(4L, title, content);

        // 실제 결과
        ArticleForm dto = new ArticleForm(null, title, content);
        Article article = articleService.create(dto);

        // 예상 시나리오와 실제 결과를 비교
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void create_실패____id가_포함된_dto_입력() {
        // 예상 시나리오
        Article expected = null;

        // 실제 결과
        ArticleForm dto = new ArticleForm(4L, "라라라라", "4444");
        Article article = articleService.create(dto);

        // 예상 시나리오와 실제 결과를 비교
        assertEquals(expected, article);
    }

    @Test
    @Transactional
    void update_성공____존재하는_id와_title_content가_있는_dto_입력() {
        // 예상 시나리오
        Long id = 3L;
        String title = "가나다라";
        String content = "4567";
        Article expected = new Article(id, title, content);

        // 실제 결과
        ArticleForm dto = new ArticleForm(id, title, content);
        Article article = articleService.update(id, dto);

        // 예상 시나리오와 실제 결과를 비교
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void update_성공____존재하는_id와_title만_있는_dto_입력() {
        // 예상 시나리오
        Long id = 3L;
        String title = "대한민국";
        String content = "3333";
        Article expected = new Article(id, title, content);

        // 실제 결과
        ArticleForm dto = new ArticleForm(id, title, null);
        Article article = articleService.update(id, dto);

        // 예상 시나리오와 실제 결과를 비교
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void update_실패____존재하지_않는_id의_dto_입력() {
        // 예상 시나리오
        Article expected = null;

        // 실제 결과
        Long id = 4L;
        ArticleForm dto = new ArticleForm(id, "제목수정", "내용수정");
        Article article = articleService.update(id, dto);

        // 예상 시나리오와 실제 결과를 비교
        assertEquals(expected, article);
    }

//    @Test
//    @Transactional
//    void update_실패____id만_있는_dto_입력() {
//        // 예상 시나리오
//        Article expected = null;
//
//        // 실제 결과
//        Long id = 3L;
//        ArticleForm dto = new ArticleForm(id, null, null);
//        Article article = articleService.update(id, dto);
//
//        // 예상 시나리오와 실제 결과를 비교
//        assertEquals(expected, article);
//    }

    @Test
    @Transactional
    void delete_성공____존재하는_id_입력() {
        // 예상 시나리오
        Long id = 3L;
        Article expected = new Article(id, "다다다다", "3333");

        // 실제 결과
        Article article = articleService.delete(id);

        // 예상 시나리오와 실제 결과를 비교
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void delete_실패____존재하지_않는_id_입력() {
        // 예상 시나리오
        Article expected = null;

        // 실제 결과
        Article article = articleService.delete(4L);

        // 예상 시나리오와 실제 결과를 비교
        assertEquals(expected, article);
    }

}