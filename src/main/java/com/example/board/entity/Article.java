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

}