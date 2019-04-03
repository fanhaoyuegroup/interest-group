package com.fan.design.chain;

/**
 * Date: 2019/4/3
 * Description:
 *
 * @author lihao
 */
public interface Filter {
    void doFilter(RequestInfo requestInfo,ResponseInfo responseInfo);
}
