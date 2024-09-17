package com.example.board.dto;

import com.example.board.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString // 데이터가 잘 받아졌는지 확인 용도
// new.mustache에서 form 데이터를 받아올 그릇
public class ArticleForm {

    private Long id; // id 필드 추가!! (edit.mustache에 name="id"가 추가되었으므로 dto에도 id 필드 추가해줘야함)
    private String title;
    private String content;

    // DTO인 form에서 Entity객체로 변환하는 메소드
    public Article toEntity() {
        return new Article(id, title, content); // Entity(Article) 클래스의 객체를 생성해야 하므로 생성자 호출
    }

}