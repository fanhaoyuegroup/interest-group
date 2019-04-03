package com.fan.design.chain;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2019/4/3
 * Description:
 *
 * @author lihao
 */
public class FilterChain {
    private List<Filter> filters = new ArrayList<>();
    private ResponseInfo responseInfo;
    private RequestInfo requestInfo;


    public FilterChain addFilter(Filter filter){
        this.filters.add(filter);
        return this;
    }

    public void doFilter(RequestInfo requestInfo,ResponseInfo responseInfo){
        this.requestInfo = requestInfo;
        this.responseInfo = responseInfo;
        if(filters.size()>0){
            for(Filter filter : filters){
                filter.doFilter(requestInfo,responseInfo);
            }
        }
    }

    public void showResult(){
        System.out.println("RequestInfo:["+ requestInfo + "]");
        System.out.println("ResponseInfo:[" + responseInfo + "]");
    }
}
