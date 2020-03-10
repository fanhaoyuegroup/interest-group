package main.java.com.fan.dubbo.rpc.simple;

import java.util.Arrays;
import java.util.List;

/**
 * @author 江蓠
 * @date 2020/3/10 9:11 下午
 */
public class SimpleTest {
    public List<String> hello(String msg){
        return Arrays.asList("msg is "+msg);
    }
    public List<String> hello(String hello,String world){

        return Arrays.asList("hello"+hello,"world"+world);
    }
}
