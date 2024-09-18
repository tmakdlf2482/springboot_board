package com.example.board.api;

import com.example.board.dto.ArticleForm;
import com.example.board.entity.Article;
import com.example.board.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // RestAPI 용 컨트룰러! 데이터(JSON)를 반환!
@Slf4j // 로깅을 위한 어노테이션
public class ArticleApiController {

    @Autowired // Spring boot로 땡겨와야 함 (Dependency Injection, 생성 객체를 가져와 연결)
    private ArticleService articleService;

    // GET
    @GetMapping("/api/articles")
    public List<Article> index() {
        return articleService.index();
    }

    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id) { // @PathVariable : URL 요청을 통해서({id}) id 값을 가져오기
        return articleService.show(id);
    }

    // POST
    // 그냥 @Controller에 <form></form>에서 데이터를 전송할 때는 그냥 매개변수에 추가만하면 됐는데,
    // @RestController에서 JSON으로 데이터를 전송하면 단순히 매개변수에 추가만한다고 사용자가 전송한 데이터가 받아지지 않음
    @PostMapping("/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto) { // 사실 form을 던지는게 아니라 dto 즉 JSON을 던짐
        Article created = articleService.create(dto);

        return (created != null) ?
            ResponseEntity.status(HttpStatus.OK).body(created) :
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // PATCH
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto) {
        Article updated = articleService.update(id, dto);

        return (updated != null) ?
            ResponseEntity.status(HttpStatus.OK).body(updated) :
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // DELETE
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id) {
        Article deleted = articleService.delete(id);

        return (deleted != null) ?
            ResponseEntity.status(HttpStatus.OK).build() :
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // 트랜잭션 -> 실패 -> 롤백
    @PostMapping("/api/transaction-test")
    public ResponseEntity<List<Article>> transactionTest(@RequestBody List<ArticleForm> dtos) {
        List<Article> createdList = articleService.createArticles(dtos);

        return (createdList != null) ?
            ResponseEntity.status(HttpStatus.OK).body(createdList) :
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}