package com.example.board.controller;

import com.example.board.dto.ArticleForm;
import com.example.board.entity.Article;
import com.example.board.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j // 로깅을 위한 어노테이션
public class ArticleController {

    private static final Logger log = LoggerFactory.getLogger(ArticleController.class);
    @Autowired // 스프링 부트가 미리 생성해놓은 객체를 가져다가 자동 연결!
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) { // 데이터를 받아오려면 매개변수로 dto를 넣어줘야 함
        // System.out.println(form.toString()); -> 로깅 기능으로 대체
        log.info(form.toString());

        // 1. DTO를 Entity로 변환!
        // Article은 Entity 클래스
        Article article = form.toEntity(); // form DTO 데이터에 toEntity를 호출해서 Article 타입의 엔티티를 반환
        // System.out.println(article.toString()); -> 로깅 기능으로 대체
        log.info(article.toString());

        // 2. Repository(실제 DB에 저장하게 하는 역할)에게 Entity를 DB안에 저장하게 함!
        Article saved = articleRepository.save(article);
        // System.out.println(saved.toString()); -> 로깅 기능으로 대체
        log.info(saved.toString());

        return "";
    }

}