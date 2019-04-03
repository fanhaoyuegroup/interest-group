package com.fan.design.chain;

/**
 * Date: 2019/4/3
 * Description:
 *
 * @author lihao
 */
public class URLFilter implements Filter {
    @Override
    public void doFilter(RequestInfo requestInfo, ResponseInfo responseInfo) {
        if(!requestInfo.getUrl().contains("https")){
            int index = requestInfo.getUrl().indexOf(":");
            String begin = requestInfo.getUrl().substring(0,index);
            String result = requestInfo.getUrl().replace(begin,"https");
            requestInfo.setUrl(result);
        }
    }
}
