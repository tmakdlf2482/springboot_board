package com.example.board.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity // DB가 해당 객체(Entity)를 인식 가능!
public class Article {

    @Id
    @GeneratedValue // 자동 생성 어노테이션! (1, 2, 3, ...)
    private Long id; // 기본키

    @Column
    private String title;

    @Column
    private String content;

    // 생성자 추가
    public Article(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

}