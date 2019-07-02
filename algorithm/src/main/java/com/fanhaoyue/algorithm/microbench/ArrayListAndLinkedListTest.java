package com.fanhaoyue.algorithm.microbench;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 * Created by CalvinKris 测试链表与数组的效率
 * Since 2019/7/1 5:45 PM
 * Result "com.fanhaoyue.algorithm.microbench.ArrayListAndLinkedListTest.linkedListTest":
 *   0.118 ±(99.9%) 0.004 us/op [Average]
 *   (min, avg, max) = (0.118, 0.118, 0.120), stdev = 0.001
 *   CI (99.9%): [0.114, 0.122] (assumes normal distribution)
 *
 *
 * # Run complete. Total time: 00:01:05
 *
 * REMEMBER: The numbers below are just data. To gain reusable insights, you need to follow up on
 * why the numbers are the way they are. Use profilers (see -prof, -lprof), design factorial
 * experiments, perform baseline and negative tests that provide experimental control, make sure
 * the benchmarking environment is safe on JVM/OS/HW level, ask for reviews from the domain experts.
 * Do not assume the numbers tell you what you want them to tell.
 *
 * Benchmark                                  (testNum)  Mode  Cnt  Score   Error  Units
 * ArrayListAndLinkedListTest.ArrayListTest          12  avgt    5  0.059 ± 0.007  us/op
 * ArrayListAndLinkedListTest.ArrayListTest          18  avgt    5  0.151 ± 0.300  us/op
 * ArrayListAndLinkedListTest.ArrayListTest          27  avgt    5  0.179 ± 0.026  us/op
 * ArrayListAndLinkedListTest.linkedListTest         12  avgt    5  0.066 ± 0.044  us/op
 * ArrayListAndLinkedListTest.linkedListTest         18  avgt    5  0.080 ± 0.002  us/op
 * ArrayListAndLinkedListTest.linkedListTest         27  avgt    5  0.118 ± 0.004  us/op
 * 可以看到 LinkedList的效率基本不变，而ArrayList的效率因为18 27这几个数字都伴随着扩容以及数组的复制，所以效率会变得稍微低下一点。
 * 所以初始化的时候数组类型的最好指定长度，另外当大小确定的时候由于LinkedList需要一些额外操作，指针关联，所以稍微比Arraylist耗费一点时间。
 * 但是当你的大小不确定的时候且对于中间插入移除比较频繁的时候那么LinkedList无疑是你最好的选择。
 */
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@State(Scope.Benchmark)
public class ArrayListAndLinkedListTest {

    @Param({"12", "18", "27"})
    int testNum;
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void ArrayListTest(){
        List<Integer> list=new ArrayList<>(12);
        for (int i=0;i<testNum;i++){
            list.add(i);
        }
    }
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void linkedListTest(){
       List<Integer> list=new LinkedList<>();
        for (int i=0;i<testNum;i++){
            list.add(i);
        }
    }
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(ArrayListAndLinkedListTest.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
