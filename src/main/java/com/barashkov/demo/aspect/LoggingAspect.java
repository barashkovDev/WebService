package com.barashkov.demo.aspect;

import com.barashkov.demo.models.Post;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.barashkov.demo.controllers.*.*(..))")
    public void loggingPointCut(){}

    @AfterReturning(value = "execution(* org.springframework.data.repository.CrudRepository+.save(..))",
            returning = "post")
    public void after(JoinPoint joinPoint, Post post) {
        log.info("After save method invoked::" + post);
    }

}
