package com.example.board.controller;

import com.example.board.dto.ArticleForm;
import com.example.board.entity.Article;
import com.example.board.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j // 로깅을 위한 어노테이션
public class ArticleController {

    @Autowired // 스프링 부트가 미리 생성해놓은 객체를 가져다가 자동 연결!
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) { // 데이터를 받아오려면 매개변수로 DTO를 넣어줘야 함
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

    @GetMapping("/articles/{id}") // {id} 는 변하는 수가 들어옴. 예를 들어 /articles/1, /articles/2 이런식으로
    public String show(@PathVariable Long id, Model model) { // @PathVariable 는 URL 경로로부터 입력이 된다는 뜻
        // log.info("id = " + id);

        // 1. id로 데이터를 가져옴
        Article articleEntity = articleRepository.findById(id).orElse(null); // .orElse(null)은 만약 id를 못찾았다면 null을 반환
        // 2. 가져온 데이터를 모델에 등록
        model.addAttribute("article", articleEntity);
        // 3. 보여줄 페이지를 설정
        return "articles/show"; // articles/show.mustache -> 브라우저로 전송!
    }

}