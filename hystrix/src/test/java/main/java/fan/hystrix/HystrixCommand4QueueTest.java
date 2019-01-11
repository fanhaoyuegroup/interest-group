package main.java.fan.hystrix;

import org.junit.Test;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * HystrixCommand4QueueTest
 *
 * @author luobosi@2dfire.com
 * @since 2018-10-04
 */
public class HystrixCommand4QueueTest {

	@Test
	public void testQueue() throws Exception {
		// queue()是异步非堵塞性执行：直接返回，同时创建一个线程运行HelloWorldHystrixCommand.run()
		// 一个对象只能queue()一次
		// queue()事实上是toObservable().toBlocking().toFuture()
		Future<String> future = new HystrixCommandDemo2("Hlx").queue();
		
		// 使用future时会堵塞，必须等待HelloWorldHystrixCommand.run()执行完返回
		String queueResult = future.get(10000, TimeUnit.MILLISECONDS);
		// String queueResult = future.get();
		System.out.println("queue异步结果：" + queueResult);
		assertEquals("Hello", queueResult.substring(0, 5));
	}


}