package com.example.board.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity // DB가 해당 객체(Entity)를 인식 가능! (실제로 해당 클래스로 테이블을 만들어 줌)
@AllArgsConstructor
@NoArgsConstructor // 디폴트 생성자를 추가해줌!
@ToString
@Getter
public class Article {

    @Id // 대표값(PK)을 지정! (주민등록번호 같은거)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB가 id를 자동 생성 어노테이션!, 겹치지 않게 자동으로 넣어줌
    private Long id; // 기본키

    @Column
    private String title;

    @Column
    private String content;

    public void patch(Article article) { // article은 사용자가 수정하려는 데이터
        if (article.getTitle() != null) // 사용자가 수정하려는 제목 데이터가 있다면
            this.title = article.title; // 수정하려는 제목 데이터(article.title)를 this.title, 즉 기존 title 컬럼에 넣어줌
        if (article.getContent() != null) // 사용자가 수정하려는 내용 데이터가 있다면
            this.content = article.content; // 수정하려는 내용 데이터(article.content)를 this.content, 즉 기존 content 컬럼에 넣어줌
    }

}