package com.fan.design.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LogHandler implements InvocationHandler {
    /**
     * delegate 真实对象
     */
    private Object delegate;
    public LogHandler(Object object){
        this.delegate = object;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        Object object = null;
        try{
            System.out.println("调用时间:"+System.currentTimeMillis()+","+"method->"+method.getName());
            object = method.invoke(delegate,args);
        }catch (Exception e){
            System.out.println("异常时间"+System.currentTimeMillis()+","+"异常地方->"+method.getName()+
                    "Exception->"+e.getCause());
        }
        return object;
    }
}
