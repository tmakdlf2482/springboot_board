package com.example.board.service;

import com.example.board.dto.ArticleForm;
import com.example.board.entity.Article;
import com.example.board.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service // 해당 클래스를 서비스로 인식하여 스프링 부트에 객체를 생성(등록)
public class ArticleService {

    @Autowired // Spring boot로 땡겨와야 함 (Dependency Injection, 생성 객체를 가져와 연결)
    private ArticleRepository articleRepository;

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();

        if (article.getId() != null) { // 즉, 사용자가 원래 DB에 있던 id를 추가하려고 한다면 (원래 DB에 있던 id를 추가하면 그냥 수정되어 버림)
            return null;
        }

        return articleRepository.save(article);
    }

    public Article update(Long id, ArticleForm dto) {
        // 1. 수정용 엔티티 생성
        Article article = dto.toEntity();
        log.info("id: {}, article: {}", id, article.toString()); // id가 첫번째 중괄호로 들어가짐, article.toString()이 두번째 중괄호로 들어가짐

        // 2. 대상 엔티티를 조회
        Article target = articleRepository.findById(id).orElse(null);
        // log.info("target: {}", target); // target은 기존 데이터

        // 3. 잘못된 요청 처리(대상이 없거나 target == null, id가 다른 경우 id != article.getId())
        if (target == null || id != article.getId()) {
            // 400, 잘못된 요청 응답!
            log.info("잘못된 요청! id: {}, article: {}", id, article.toString());

            return null;
        }

        // 4. 잘못된 요청 처리가 아니라면 정상 업데이트
        target.patch(article); // 기존 데이터에 새롭게 추가된 데이터만 붙여줌
        Article updated = articleRepository.save(target);

        return updated;
    }

    public Article delete(Long id) {
        // 1. 지울 대상 찾기
        Article target = articleRepository.findById(id).orElse(null);

        // 2. 잘못된 요청 처리
        if (target == null) {
            return null;
        }

        // 3. 지울 대상 삭제
        articleRepository.delete(target);

        return target;
    }

    @Transactional // 해당 메소드를 트랜잭션으로 묶음 (해당 메소드를 수행하다가 실패하면 이 메소드가 수행되기 이전상태로 롤백됨, 즉 그냥 맨 처음으로 롤백됨, 이 메소드는 수행되지 않음)
    public List<Article> createArticles(List<ArticleForm> dtos) {
        // 1. dto 묶음을 entity 묶음으로 변환
        List<Article> articleList = dtos.stream()
                .map(dto -> dto.toEntity()) // 1개 1개의 dto가 올때마다 dto.toEntity()로 매핑
                .collect(Collectors.toList()); // 매핑된 것을 list로 변환

        // 위 3줄 코드를 for문으로 구현
//        List<Article> articleList = new ArrayList<>();
//        for (int i = 0; i < dtos.size(); i++) {
//            ArticleForm dto = dtos.get(i);
//            Article entity = dto.toEntity();
//            articleList.add(entity);
//        }

        // 2. entity 묶음을 DB로 저장
        articleList.stream()
                .forEach(article -> articleRepository.save(article)); // forEach로 반복하는데, 1개의 article이 올때마다 save

        // 위 2줄 코드를 for문으로 구현
//        for (int i = 0; i < articleList.size(); i++) {
//            Article article = articleList.get(i);
//            articleRepository.save(article);
//        }

        // 3. 저장된 상태에서 강제 예외 발생
        articleRepository.findById(-1L).orElseThrow(
                () -> new IllegalArgumentException("결제 실패!")
        );

        // 4. 결과값 반환
        return articleList;
    }

}