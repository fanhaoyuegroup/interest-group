package main.java.fan.hystrix;

import com.netflix.hystrix.*;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

/**
 * 测试信号量隔离
 * 默认执行run()用的是主线程，为了模拟并行执行command，这里我们自己创建多个线程来执行command
 * 设置executionisolationsemaphoremaxconcurrentrequests为3，意味着信号量最多允许执行run的并发数为3，超过则触发降级，即不执行run而执行getfallback
 * 设置fallbackisolationsemaphoremaxconcurrentrequests为1，意味着信号量最多允许执行fallback的并发数为1，超过则抛异常fallback execution rejected
 *
 * @author luobosi@2dfire.com
 * @since 2018-10-05
 */
public class HystrixCommand4SemaphoreTest extends HystrixCommand<String> {

    private final String name;

    public HystrixCommand4SemaphoreTest(String name) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("SemaphoreTestGroup"))
                        .andCommandKey(HystrixCommandKey.Factory.asKey("SemaphoreTestKey"))
                        .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("SemaphoreTestThreadPoolKey"))
                        .andCommandPropertiesDefaults(    // 配置信号量隔离
                                HystrixCommandProperties.Setter()
                                        .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)    // 信号量隔离
                                        .withExecutionIsolationSemaphoreMaxConcurrentRequests(3)
                                        .withFallbackIsolationSemaphoreMaxConcurrentRequests(1)
                        )
                // 设置了信号量隔离后，线程池配置将变无效
//                .andThreadPoolPropertiesDefaults(	
//                		HystrixThreadPoolProperties.Setter()
//                		.withCoreSize(13)	// 配置线程池里的线程数
//                )
        );
        this.name = name;
    }

    @Override
    protected String run() {
        return "run(): name=" + name + "，线程名是" + Thread.currentThread().getName();
    }

    @Override
    protected String getFallback() {
        return "getFallback(): name=" + name + "，线程名是" + Thread.currentThread().getName();
    }

    public static class UnitTest {

        @Test
        public void testSynchronous() throws IOException {

            try {
                Thread.sleep(2000);
                for (int i = 0; i < 5; i++) {
                    final int j = i;
                    // 自主创建线程来执行command，创造并发场景
                    //	                    @Override
                    Thread thread = new Thread(() -> {
                        // 这里执行两类command：HystrixCommand4SemaphoreTest设置了信号量隔离、HystrixCommandDemo2未设置信号量
                        System.out.println("-----------" + new HystrixCommandDemo2("HLX" + j).execute());
                        System.out.println("===========" + new HystrixCommand4SemaphoreTest("HLX" + j).execute());    // 被信号量拒绝的线程从这里抛出异常
                        System.out.println("-----------" + new HystrixCommandDemo2("HLX" + j).execute());    // 被信号量拒绝的线程不能执行到这里

                    });
                    thread.start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
//        	try {
//        		TimeUnit.MILLISECONDS.sleep(2000);
//        	}catch(Exception e) {}
            System.out.println("------开始打印现有线程---------");
            Map<Thread, StackTraceElement[]> map = Thread.getAllStackTraces();
            for (Thread thread : map.keySet()) {
                System.out.println(thread.getName());
            }
            System.out.println("thread num: " + map.size());
            System.in.read();
        }
    }

}
