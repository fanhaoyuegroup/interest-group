package com.fan.design.proxy.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


public class DepartmentProxy implements InvocationHandler {
    private Object object;
    private Interceptor interceptor;

    public DepartmentProxy(Object object,Interceptor interceptor){
        this.object = object;
        this.interceptor = interceptor;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Invocation invocation = new Invocation(object,method,args);
        return interceptor.interceptor(invocation);
    }

    public static Object bind(Object object,Interceptor interceptor){
        return Proxy.newProxyInstance(object.getClass().getClassLoader()
                ,object.getClass().getInterfaces()
                , new DepartmentProxy(object,interceptor));
    }
}
