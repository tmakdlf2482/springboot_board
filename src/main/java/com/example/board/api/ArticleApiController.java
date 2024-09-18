package com.example.board.api;

import com.example.board.dto.ArticleForm;
import com.example.board.entity.Article;
import com.example.board.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // RestAPI 용 컨트룰러! 데이터(JSON)를 반환!
@Slf4j // 로깅을 위한 어노테이션
public class ArticleApiController {

    @Autowired // Spring boot로 땡겨와야 함 (Dependency Injection, 외부에서 가져옴)
    private ArticleRepository articleRepository;

    // GET
    @GetMapping("/api/articles")
    public List<Article> index() {
        return articleRepository.findAll();
    }

    @GetMapping("/api/articles/{id}")
    public Article index(@PathVariable Long id) { // @PathVariable : URL 요청을 통해서({id}) id 값을 가져오기
        return articleRepository.findById(id).orElse(null);
    }

    // POST
    // 그냥 @Controller에 <form></form>에서 데이터를 전송할 때는 그냥 매개변수에 추가만하면 됐는데,
    // @RestController에서 JSON으로 데이터를 전송하면 단순히 매개변수에 추가만한다고 사용자가 전송한 데이터가 받아지지 않음
    @PostMapping("/api/articles")
    public Article create(@RequestBody ArticleForm dto) { // 사실 form을 던지는게 아니라 dto 즉 JSON을 던짐
        Article article = dto.toEntity();

        return articleRepository.save(article); // 클라이언트가 전송한 article을 save
    }

    // PATCH
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto) {
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

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Bad Request 400번 상태코드를 클라이언트에게 보냄, body에는 null을 싣어서 보냄
        }

        // 4. 잘못된 요청 처리가 아니라면 정상 업데이트 및 정상 응답(200)
        target.patch(article); // 기존 데이터에 새롭게 추가된 데이터만 붙여줌
        Article updated = articleRepository.save(target); // article 대신에 target을 넣으면 안됨! 왜냐하면 target은 수정하기 전 데이터임

        return ResponseEntity.status(HttpStatus.OK).body(updated); // ok 200번 상태코드를 클라이언트에게 보냄, body에는 updated을 싣어서 보냄
    }

    // DELETE
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id) {
        // 지울 대상 찾기
        Article target = articleRepository.findById(id).orElse(null);

        // 잘못된 요청 처리
        if (target == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // 지울 대상 삭제
        articleRepository.delete(target);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}