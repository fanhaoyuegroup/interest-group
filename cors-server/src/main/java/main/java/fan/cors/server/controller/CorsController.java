/*
 * Copyright (C) 2009-2018 Hangzhou FanDianEr Technology Co., Ltd. All rights reserved
 */
package main.java.fan.cors.server.controller;

import org.springframework.web.bind.annotation.*;

/**
 * CorsController
 *
 * @author lanxiang
 * @since 2018-11-12
 */
@RestController
public class CorsController {
    /**
     * 测试 GET 形式的请求
     *
     * @return
     */
    @GetMapping("/get_test")
    public String getTest() {
        return "Hello Jas";
    }

    /**
     * 测试 POST 形式的请求
     *
     * @return
     */
    @PostMapping("/post_test")
    public String postTest() {
        return "Hello Jas";
    }

    /**
     * 测试携带 Cookie 信息的请求
     *
     * @param cookieValue
     * @return
     */
    @GetMapping("/cookie)_test")
    public String getCookie(@CookieValue(value = "cookie") String cookieValue) {
        return "获取到的 Cookie 值为：" + cookieValue;
    }

    /**
     * 测试携带自定义请求头的请求
     *
     * @param headerValue
     * @return
     */
    @GetMapping("/header_test")
    public String getMyHeader(@RequestHeader("my-header") String headerValue) {
        return "获取到的 Header 信息为：" + headerValue;
    }
}
