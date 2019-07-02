package com.fanhaoyue.algorithm.microbench;

import com.fanhaoyue.algorithm.SnowFlake;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * Description:
 * Created by CalvinKris 机器 mbp8G
 * Since 2019/7/1 4:32 PM
 *
 * Result "com.fanhaoyue.algorithm.microbench.SnowFlakeTest.snowFlake":
 *   1731.018 ±(99.9%) 439.400 ops/s [Average]
 *   (min, avg, max) = (1548.507, 1731.018, 1843.494), stdev = 114.111
 *   CI (99.9%): [1291.617, 2170.418] (assumes normal distribution)
 *
 *
 * # Run complete. Total time: 00:01:41
 *
 * REMEMBER: The numbers below are just data. To gain reusable insights, you need to follow up on
 * why the numbers are the way they are. Use profilers (see -prof, -lprof), design factorial
 * experiments, perform baseline and negative tests that provide experimental control, make sure
 * the benchmarking environment is safe on JVM/OS/HW level, ask for reviews from the domain experts.
 * Do not assume the numbers tell you what you want them to tell.
 *
 * Benchmark                 Mode  Cnt     Score     Error  Units
 * SnowFlakeTest.snowFlake  thrpt    5  1731.018 ± 439.400  ops/s
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
public class SnowFlakeTest {
    @Benchmark
    public void snowFlake(){
        SnowFlake.getId();

    }
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(SnowFlakeTest.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
