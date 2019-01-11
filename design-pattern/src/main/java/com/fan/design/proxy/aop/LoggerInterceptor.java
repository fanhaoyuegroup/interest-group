package com.fan.design.proxy.aop;

import java.lang.reflect.InvocationTargetException;

public class LoggerInterceptor implements Interceptor {
    @Override
    public Object interceptor(Invocation invocation) throws IllegalArgumentException, InvocationTargetException,IllegalAccessException{
        System.out.println("logger开始...");
        return invocation.process();
    }

    @Override
    public Object register(Object object) {
        return DepartmentProxy.bind(object,this);
    }
}
