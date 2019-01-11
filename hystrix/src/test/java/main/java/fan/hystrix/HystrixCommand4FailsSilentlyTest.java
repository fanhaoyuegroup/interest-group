package main.java.fan.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


/**
 * HystrixCommand4FailsSilentlyTest
 *
 * @author luobosi@2dfire.com
 * @since 2018-10-04
 */
public class HystrixCommand4FailsSilentlyTest extends HystrixCommand<List<String>> {

    private final boolean throwException;

    public HystrixCommand4FailsSilentlyTest(boolean throwException) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.throwException = throwException;
    }

    @Override
    protected List<String> run() {
        if (throwException) {
            throw new RuntimeException("failure from HystrixCommand4FailsSilentlyTest");
        } else {
            ArrayList<String> values = new ArrayList<String>();
            values.add("success");
            return values;
        }
    }

    @Override
    protected List<String> getFallback() {
        return Collections.emptyList();
    }

    public static class UnitTest {

        @Test
        public void testSuccess() {
            assertEquals("success", new HystrixCommand4FailsSilentlyTest(false).execute().get(0));
        }

        @Test
        public void testFailure() {
            try {
                assertEquals(0, new HystrixCommand4FailsSilentlyTest(true).execute().size());
            } catch (HystrixRuntimeException e) {
                fail("we should not get an exception as we fail silently with a fallback");
            }
        }
    }
}