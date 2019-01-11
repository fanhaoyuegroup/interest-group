package com.fan.design.demo;

/**
 * 抽象模板类
 */
public abstract class AbstractPresellTemplate {
    public final void execute() {
        preDoSomething();
        abstractMethod();
        hookMethod();
        afterDoSomething();
    }

    private void preDoSomething() {
        System.out.println("前置通用业务...");
        System.out.println("--------------");
    }

    private void afterDoSomething() {
        System.out.println("后置置通用业务...");
    }

    /**
     * 钩子方法，为子类模板提供额外的业务需求入口
     */
    public void hookMethod() {

    }

    /**
     * 子类重写方法
     */
    public abstract void abstractMethod();
}
