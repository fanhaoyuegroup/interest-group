package com.fan.design.chain;


/**
 * Date: 2019/4/3
 * Description:
 *
 * @author lihao
 */
public class EncodingFilter implements Filter {
    @Override
    public void doFilter(RequestInfo requestInfo, ResponseInfo responseInfo) {
        if(!requestInfo.getEncoding().equals("UTF-8") || !requestInfo.getEncoding().equals("utf-8")){
            requestInfo.setEncoding("utf-8");
        }
    }
}
