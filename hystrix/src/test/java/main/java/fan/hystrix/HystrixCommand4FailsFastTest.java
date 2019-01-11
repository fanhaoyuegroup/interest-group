/*
 * Copyright (C) 2009-2018 Hangzhou 2Dfire Technology Co., Ltd. All rights reserved
 */
package main.java.fan.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * HystrixCommand4FailsFastTest
 *
 * @author luobosi@2dfire.com
 * @since 2018-10-04
 */
public class HystrixCommand4FailsFastTest extends HystrixCommand<String> {

    private final boolean throwException;

    public HystrixCommand4FailsFastTest(boolean throwException) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.throwException = throwException;
    }

    @Override
    protected String run() {
        if (throwException) {
            throw new RuntimeException("failure from HystrixCommand4FailsFastTest");
        } else {
            return "success";
        }
    }

    public static class UnitTest {

        @Test
        public void testSuccess() {
            assertEquals("success", new HystrixCommand4FailsFastTest(false).execute());
        }

        @Test
        public void testFailure() {
            try {
                new HystrixCommand4FailsFastTest(true).execute();
            } catch (HystrixRuntimeException e) {
                assertEquals("failure from HystrixCommand4FailsFastTest:", e.getCause().getMessage());
                e.printStackTrace();
            }
        }
    }
}
