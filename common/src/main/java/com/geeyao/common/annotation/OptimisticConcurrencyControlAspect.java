package com.geeyao.common.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.IllegalTransactionStateException;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;

@Aspect
@Component
public class OptimisticConcurrencyControlAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(OptimisticConcurrencyControlAspect.class);

    public OptimisticConcurrencyControlAspect() {
    }

    @Around("@annotation(com.geeyao.common.annotation.Retry)")
    public Object retry(ProceedingJoinPoint pjp) throws Throwable {
        Retry retryAnnotation = getAnnotation(pjp, Retry.class);
        return retryAnnotation != null ? this.proceed(pjp, retryAnnotation) : this.proceed(pjp);
    }

    private Object proceed(ProceedingJoinPoint pjp) throws Throwable {
        return pjp.proceed();
    }

    private Object proceed(ProceedingJoinPoint pjp, Retry retryAnnotation) throws Throwable {
        int times = retryAnnotation.times();
        Class<? extends Throwable>[] retryOn = retryAnnotation.on();
        Assert.isTrue(times > 0, "@Retry{times} should be greater than 0!");
        Assert.isTrue(retryOn.length > 0, "@Retry{on} should have at least one Throwable!");
        if (retryAnnotation.failInTransaction() && TransactionSynchronizationManager.isActualTransactionActive()) {
            throw new IllegalTransactionStateException("You shouldn't retry an operation from withing an existing Transaction.This is because we can't retry if the current Transaction was already rollbacked!");
        } else {
            LOGGER.info("Proceed with {} retries on {}", times, Arrays.toString(retryOn));
            return this.tryProceeding(pjp, times, retryOn);
        }
    }

    private Object tryProceeding(ProceedingJoinPoint pjp, int times, Class<? extends Throwable>[] retryOn) throws Throwable {
        try {
            return this.proceed(pjp);
        } catch (Throwable var5) {
            if (this.isRetryThrowable(var5, retryOn) && times-- > 0) {
                LOGGER.info("Optimistic locking detected, {} remaining retries on {}", times, Arrays.toString(retryOn));
                return this.tryProceeding(pjp, times, retryOn);
            } else {
                throw var5;
            }
        }
    }

    private boolean isRetryThrowable(Throwable throwable, Class<? extends Throwable>[] retryOn) {
        Throwable[] causes = ExceptionUtils.getThrowables(throwable);
        Throwable[] arr = causes;
        int len = causes.length;

        for(int i = 0; i < len; ++i) {
            Throwable cause = arr[i];
            Class[] arr1 = retryOn;
            int len1 = retryOn.length;

            for(int i$ = 0; i$ < len1; ++i$) {
                Class<? extends Throwable> retryThrowable = arr1[i$];
                if (retryThrowable.isAssignableFrom(cause.getClass())) {
                    return true;
                }
            }
        }

        return false;
    }

    public static <T extends Annotation> T getAnnotation(ProceedingJoinPoint pjp, Class<T> annotationClass) throws NoSuchMethodException {
        MethodSignature signature = (MethodSignature)pjp.getSignature();
        Method method = signature.getMethod();
        T annotation = AnnotationUtils.findAnnotation(method, annotationClass);
        if (annotation != null) {
            return annotation;
        } else {
            Class[] argClasses = new Class[pjp.getArgs().length];

            for(int i = 0; i < pjp.getArgs().length; ++i) {
                argClasses[i] = pjp.getArgs()[i].getClass();
            }

            method = pjp.getTarget().getClass().getMethod(pjp.getSignature().getName(), argClasses);
            return AnnotationUtils.findAnnotation(method, annotationClass);
        }
    }

}
