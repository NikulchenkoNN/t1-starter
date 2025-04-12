package ru.home_work.t1_starter.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.slf4j.spi.LoggingEventBuilder;
import org.springframework.stereotype.Component;
import ru.home_work.t1_starter.aspect.annotations.LogException;
import ru.home_work.t1_starter.aspect.annotations.LogExecutionTime;
import ru.home_work.t1_starter.aspect.annotations.LogMethodCallWithParams;
import ru.home_work.t1_starter.aspect.annotations.LogReturning;

import java.util.Arrays;

@Component
@Aspect
public class LogAspect {

    private final LoggingEventBuilder loggingEventBuilder;

    public LogAspect(String level) {
        Logger logger = LoggerFactory.getLogger(LogAspect.class);
        loggingEventBuilder = logger.atLevel(Level.valueOf(level));
    }

    @Before("@annotation(logMethodCallWithParams)")
    void logCalls(JoinPoint joinPoint, LogMethodCallWithParams logMethodCallWithParams) {
        String message = String.format("Метод %s класса %s вызван с параметрами %s",
                joinPoint.getSignature().getName(),
                joinPoint.getSignature().getDeclaringType().getName(),
                Arrays.toString(joinPoint.getArgs()));
        loggingEventBuilder.log(message);
    }

    @Around("@annotation(logExecutionTime)")
    public Object logTime(ProceedingJoinPoint joinPoint, LogExecutionTime logExecutionTime) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long stop = System.currentTimeMillis();
        String message = String.format("Метод %s класса %s выполнялся %d мс",
                joinPoint.getSignature().getName(),
                joinPoint.getSignature().getDeclaringType().getName(),
                stop - start);
        loggingEventBuilder.log(message);
        return result;
    }

    @AfterThrowing(
            pointcut = "@annotation(logException)",
            throwing = "exception"
    )
    void logException(JoinPoint joinPoint, LogException logException, Throwable exception) {
        String message = String.format("Метод %s класса %s выбросил исключение %s",
                joinPoint.getSignature().getName(),
                joinPoint.getSignature().getDeclaringType().getName(),
                exception);
        loggingEventBuilder.log(message);
    }

    @AfterReturning(
            pointcut = "@annotation(logReturning)",
            returning = "val"
    )
    void logReturning(JoinPoint joinPoint, LogReturning logReturning, Object val) {
        String message = String.format("Метод %s класса %s вернул значение %s",
                joinPoint.getSignature().getName(),
                joinPoint.getSignature().getDeclaringType().getName(),
                val
        );
        loggingEventBuilder.log(message);
    }

}
