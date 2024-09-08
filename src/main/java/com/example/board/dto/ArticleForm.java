package com.example.board.dto;

import com.example.board.entity.Article;

// new.mustache에서 form 데이터를 받아올 그릇
public class ArticleForm {

    private String title;
    private String content;

    public ArticleForm(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // 데이터가 잘 받아졌는지 확인 용도
    @Override
    public String toString() {
        return "ArticleForm{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    // DTO인 form에서 Entity객체로 변환하는 메소드
    public Article toEntity() {
        return new Article(null, title, content); // Entity(Article) 클래스의 객체를 생성해야 하므로 생성자 호출
    }

}