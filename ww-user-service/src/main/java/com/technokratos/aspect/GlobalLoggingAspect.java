package com.technokratos.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class GlobalLoggingAspect {
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restControllerMethods() {
    }

    @Pointcut("within(@org.springframework.stereotype.Service *)")
    public void serviceMethods() {
    }

    @Pointcut("within(@org.springframework.stereotype.Repository *) || within(org.springframework.data.repository.*Repository+)")
    public void repositoryMethods() {
    }

    @Pointcut("within(@org.springframework.web.bind.annotation.RestControllerAdvice *)")
    public void restControllerAdviceMethods() {
    }

    @Around("restControllerMethods()")
    public Object logRequestAndResponseFromRestController(ProceedingJoinPoint pjp) throws Throwable {
        log.info("Controller: Entering method: {} with request arguments: {}", pjp.getSignature().getName(), pjp.getArgs());
        Object result = pjp.proceed();
        log.info("Controller: Exiting method: {} with response result: {}", pjp.getSignature().getName(), result);
        return result;
    }

    @Around("serviceMethods()")
    public Object logInputAndOutputDataFromService(ProceedingJoinPoint pjp) throws Throwable {
        log.debug("Service: Entering method: {} with arguments: {}", pjp.getSignature().getName(), pjp.getArgs());
        Object result = pjp.proceed();
        log.debug("Service: Exiting method {} with result: {}", pjp.getSignature().getName(), result);
        return result;
    }

    @Around("repositoryMethods()")
    public Object logInputAndOutputDataFromRepository(ProceedingJoinPoint pjp) throws Throwable {
        log.debug("Repository: Entering method: {} with arguments: {}", pjp.getSignature().getName(), pjp.getArgs());
        long startTime = System.currentTimeMillis();
        Object result = pjp.proceed();
        long diffTime = System.currentTimeMillis() - startTime;
        log.debug("Repository: Exiting method {} with result: {} Time: {} ms", pjp.getSignature().getName(), result, diffTime);
        return result;
    }
}
