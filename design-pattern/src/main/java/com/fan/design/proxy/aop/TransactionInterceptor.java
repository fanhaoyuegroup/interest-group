package com.fan.design.proxy.aop;


public class TransactionInterceptor implements Interceptor{
    @Override
    public Object interceptor(Invocation invocation) throws IllegalArgumentException{
        try {
            System.out.println("开启事务");
            return invocation.process();
        }catch (Exception e){
            System.out.println("回滚事务");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object register(Object object) {
        return DepartmentProxy.bind(object,this);
    }
}
