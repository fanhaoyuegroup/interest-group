package com.fan.design.chain;

import lombok.Data;

import java.util.Map;

/**
 * Date: 2019/4/3
 * Description:
 *
 * @author lihao
 */
@Data
public class RequestInfo {
    private String url;
    private String method;
    private Map<String,Object> requestData;
    private String encoding;
}
