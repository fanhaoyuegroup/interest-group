package com.rabbit.example.data;

import java.io.Serializable;

/**
 * @author 江蓠
 * @date 2020/3/11 1:46 下午
 */
public class Data implements Serializable {

    private String className;

    private String method;

    private   Class[] argumentsType;

    private Object[] arguments;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Class[] getArgumentsType() {
        return argumentsType;
    }

    public void setArgumentsType(Class[] argumentsType) {
        this.argumentsType = argumentsType;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
    }
}
