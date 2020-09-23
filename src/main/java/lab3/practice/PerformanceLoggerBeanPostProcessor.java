package lab3.practice;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class PerformanceLoggerBeanPostProcessor implements BeanPostProcessor {
    Map<String, Class> beansWithAnnotationLogger = new HashMap<>();

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        if (clazz.isAnnotationPresent(Logger.class)) {
            beansWithAnnotationLogger.put(beanName, clazz);
        }
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (beansWithAnnotationLogger.containsKey(beanName)) {
            Class clazz = beansWithAnnotationLogger.get(beanName);
            return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), new InvocationHandler() {

                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    long start = System.currentTimeMillis();
                    Object result = method.invoke(bean, args);
                    long end = System.currentTimeMillis();
                    System.out.println("Method worked " + (end - start) + " ms.");
                    return result;
                }
            });
        }
        return bean;
    }
}
