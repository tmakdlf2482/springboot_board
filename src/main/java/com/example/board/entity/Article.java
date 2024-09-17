package com.example.board.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity // DB가 해당 객체(Entity)를 인식 가능!
@AllArgsConstructor
@NoArgsConstructor // 디폴트 생성자를 추가해줌!
@ToString
@Getter
public class Article {

    @Id
    @GeneratedValue // 자동 생성 어노테이션! (1, 2, 3, ...)
    private Long id; // 기본키

    @Column
    private String title;

    @Column
    private String content;

}