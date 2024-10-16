package org.cuit.echo.seon.commonweb.utils;

import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * @author SeonExlike
 * @date 2021/5/1
 */
@Component("springContextUtils")
public class SpringContextUtils implements ApplicationContextAware {

    @Getter
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtils.applicationContext = applicationContext;
    }

    /**
     * @see ApplicationContext#getBean(String)
     */
    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    /**
     * @see ApplicationContext#getBean(Class)
     */
    public static <T> T getBean(Class<T> type) {
        return applicationContext.getBean(type);
    }

    /**
     * @see ApplicationContext#getBean(String, Class)
     */
    public static <T> T getBean(String name, Class<T> type) {
        return applicationContext.getBean(name, type);
    }

    /**
     * @see ApplicationContext#getBeansWithAnnotation(Class)
     */
    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) {
        return applicationContext.getBeansWithAnnotation(annotationType);
    }

    /**
     * @see ApplicationContext#getBeansOfType(Class)
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> type) {
        return applicationContext.getBeansOfType(type);
    }
}
