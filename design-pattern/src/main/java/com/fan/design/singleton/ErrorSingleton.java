package com.fan.design.singleton;

public class ErrorSingleton {
    private ErrorSingleton errorSingleton = null;
    private ErrorSingleton(){

    }
    public ErrorSingleton getInstance(){
        if(errorSingleton == null){
            errorSingleton = new ErrorSingleton();
        }
        return errorSingleton;
    }
}
