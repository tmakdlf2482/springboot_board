package com.example.board.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 사용자 정의 어노테이션 만들기
@Target({ ElementType.TYPE, ElementType.METHOD }) // 어노테이션 적용 대상 지정
@Retention(RetentionPolicy.RUNTIME) // 어노테이션 유지 기간
public @interface RunningTime {

}