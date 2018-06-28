package com.geeyao.common;

import com.geeyao.common.log.Log;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class LogBeanPostProcessor implements BeanPostProcessor{
    @Override
    public Object postProcessBeforeInitialization(Object bean, String s) throws BeansException {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            Log log = field.getAnnotation(Log.class);
            if(log != null){
                try {
                    field.setAccessible(true);
                    field.set(bean, LoggerFactory.getLogger(bean.getClass()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }finally {
                    field.setAccessible(false);
                }
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String s) throws BeansException {
        return bean;
    }
}
