package com.example.board.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect // AOP 클래스 선언: 부가 기능을 주입하는 클래스
@Component // IoC Container가 해당 객체를 생성 및 관리
@Slf4j
public class PerformanceAspect {

    // 특정 어노테이션을 대상 지정
    @Pointcut("@annotation(com.example.board.annotation.RunningTime)") // 주입 대상 지정
    private void enableRunningTime() {

    }

    // 대상 메소드 선택: board 패키지 하위에 있는 모든 메소드 지정
    // 기본 패키지의 모든 메소드 지정
    @Pointcut("execution(* com.example.board..*.*(..))") // 주입 대상 지정
    private void cut() {

    }

    // 위의 2개의 Pointcut을 합침
    // 실행 시점 설정: 두 조건을 모두 만족하는 대상을 전후로 부가 기능을 삽입
    @Around("cut() && enableRunningTime()")
    public void loggingRunningTime(ProceedingJoinPoint joinPoint) throws Throwable { // ProceedingJoinPoint : 그 대상을 실행까지 할 수 있는 joinPoint
        // 메소드 수행 전, 측정 시작
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // 메소드를 수행
        Object returningObj = joinPoint.proceed(); // 타겟팅된 대상을 수행

        // 메소드 수행 후, 측정 종료 및 로깅
        stopWatch.stop();
        String methodName = joinPoint.getSignature().getName(); // 메소드명
        log.info("{}의 총 수행 시간 => {}sec", methodName, stopWatch.getTotalTimeSeconds());
    }

}