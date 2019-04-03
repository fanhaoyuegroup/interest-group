package com.fan.design.chain;

import java.util.HashMap;
import java.util.Map;

/**
 * Date: 2019/4/3
 * Description:
 *
 * @author lihao
 */
public class Main {
    public static void main(String[] args) {
        FilterChain filterChain = new FilterChain();
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setMethod("post");
        requestInfo.setEncoding("ISO-9088");
        requestInfo.setUrl("http://localhost:8080/index");

        Map data = new HashMap<>();
        data.put("key","value");
        requestInfo.setRequestData(data);

        ResponseInfo responseInfo = new ResponseInfo();
        Filter filter1 = new EncodingFilter();
        Filter filter2 = new URLFilter();
        filterChain.addFilter(filter1).addFilter(filter2);

        filterChain.doFilter(requestInfo,responseInfo);

        filterChain.showResult();
    }
}
