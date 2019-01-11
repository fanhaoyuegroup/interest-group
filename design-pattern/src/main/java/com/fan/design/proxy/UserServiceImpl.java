package com.fan.design.proxy;

public class UserServiceImpl implements UserService {
    @Override
    public String login(String username, String password) {
        if("username".equals(username) && "password".equals(password)){
            System.out.println("login success");
            return "0";
        }
        System.out.println("login fail");
        return "1";
    }
}
