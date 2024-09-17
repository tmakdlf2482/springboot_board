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

import java.util.List;

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

        return "redirect:/articles/" + saved.getId(); // saved.getId() 하게되면 해당 id값을 가져옴 (예를 들면, id값이 1, 2, 3, ...)
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

    @GetMapping("/articles")
    public String index(Model model) {
        // 1. 모든 Article을 가져옴
        List<Article> articleEntityList = articleRepository.findAll(); // articleRepository에 해당하는 모든 데이터를 전부 가져옴, ArrayList 상위 타입이 List임 (업캐스팅)

        // 2. 가져온 Article 묶음을 뷰로 전달함 (모델에 등록)
        model.addAttribute("articleList", articleEntityList);

        // 3. 보여줄 페이지를 설정
        return "articles/index"; // articles/index.mustache -> 브라우저로 전송!
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) { // @PathVariable Long id 라고 하면 URL의 id값을 가져옴, 예를 들어 /articles/1/edit이면 1을 가져오고, /articles/2/edit이면 2를 가져옴
        // 1. 수정할 데이터를 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null); // .orElse(null)은 만약 id를 못찾았다면 null을 반환

        // 2. 가져온 article을 모델에 데이터를 등록
        model.addAttribute("article", articleEntity);

        // 3. 뷰 페이지 설정
        return "articles/edit"; // articles/edit.mustache -> 브라우저로 전송!
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form) {
        // log.info(form.toString());

        // 1. DTO를 Entity로 변환!
        Article articleEntity = form.toEntity();
        // log.info(articleEntity.toString());

        // 2. Repository(실제 DB에 저장하게 하는 역할)에게 Entity를 DB안에 저장하게 함!
        // 2-1. DB에서 기존 데이터를 가져옴
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null); // articleRepository가 id값을 통해 데이터를 가져오는데 만약에 없다면 null을 return
        // log.info(target.toString());
        // 2-2. 기존 데이터에 값을 갱신함
        if (target != null) { // 기존 데이터가 있다면
            articleRepository.save(articleEntity); // 값을 갱신, entity가 DB로 갱신됨
        }

        // 3. 수정 결과 페이지로 redirect 함
        return "redirect:/articles/" + articleEntity.getId();
    }

}