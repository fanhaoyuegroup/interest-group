package com.fan.design.proxy.aop;

import java.lang.reflect.InvocationTargetException;

public interface Interceptor {
    Object interceptor(Invocation invocation)throws IllegalArgumentException, InvocationTargetException,IllegalAccessException;
    Object register(Object object);
}
