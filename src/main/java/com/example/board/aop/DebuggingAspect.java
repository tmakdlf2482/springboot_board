package com.example.board.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect // AOP 클래스 선언: 부가 기능을 주입하는 클래스
@Component // IoC Container가 해당 객체를 생성 및 관리
@Slf4j
public class DebuggingAspect {

    // 대상 메소드 선택: CommentService#create()
    @Pointcut("execution(* com.example.board.service.CommentService.*(..))") // 주입 대상 지정
    private void cut() {

    }

    // 실행 시점 설정: 대상 실행 이전에 수행, 즉 cut()의 대상이 수행되기 이전에 수행
    @Before("cut()")
    // 입력되는 전달값이 뭔지 로깅함
    public void loggingArgs(JoinPoint joinPoint) { // cut()의 대상 메소드
        // 입력값 가져오기
        Object[] args = joinPoint.getArgs();

        // 클래스명
        String className = joinPoint.getTarget().getClass().getSimpleName();

        // 메소드명
        String methodName = joinPoint.getSignature().getName();

        // 입력값 로깅하기
        // CommentService#create()의 입력값 => 5
        // CommentService#create()의 입력값 => CommentDto(id=null, ...)
        for (Object obj : args) { // foreach 문
            log.info("{}#{}의 입력값 => {}", className, methodName, obj);
        }
    }

    // 실행 시점 설정: 대상 실행 후, 수행(정상 수행 시), 즉 cut()에 지정된 대상 호출 성공 후!
    // 참고로 returning = "returnObj"에서 returnObj와 Object returnObj에서 returnObj의 이름이 서로 같아야 함 (returnObj)
    @AfterReturning(value = "cut()", returning = "returnObj")
    public void loggingReturnValue(JoinPoint joinPoint, Object returnObj) { // joinPoint : cut()의 대상 메소드, returnObj : 리턴값
        // 클래스명
        String className = joinPoint.getTarget().getClass().getSimpleName();

        // 메소드명
        String methodName = joinPoint.getSignature().getName();

        // 반환값 로깅
        // CommentService#create()의 반환값 => CommentDto(id=10, ...)
        log.info("{}#{}의 반환값 => {}", className, methodName, returnObj);
    }

}