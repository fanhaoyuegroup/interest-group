/*
 * Copyright (C) 2009-2016 Hangzhou 2Dfire Technology Co., Ltd. All rights reserved
 */
package com.fanhaoyue.algorithm;

/**
 * SnowflakeIdWorker
 *
 * @author zhutou
 * @since 2018-12-04
 */

/**
 * Twitter_Snowflake<br>
 * SnowFlake的结构如下(每部分用-分开):<br>
 * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000 <br>
 * SnowFlake的优点是，整体上按照时间自增排序，并且整个分布式系统内不会产生ID碰撞(由数据中心ID和机器ID作区分)
 * @author cmz
 */
public class SnowFlake {

    /**
     * 开始时间截 (2018-01-01)
     */
    private final long twepoch = 1514736000000L;

    private final int totalBit = 72;

    /**
     * 机器id所占的位数
     */
    private final long workerIdBits = 5L;

    /**
     * 数据标识id所占的位数
     */
    private final long datacenterIdBits = 5L;

    /**
     * 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
     */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);

    /**
     * 支持的最大数据标识id，结果是31
     */
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

    /**
     * 序列在id中占的位数
     */
    private final long sequenceBits = 12L;

    /**
     * 机器ID向左移12位
     */
    private final long workerIdShift = sequenceBits;

    /**
     * 数据标识id向左移17位(12+5)
     */
    private final long datacenterIdShift = sequenceBits + workerIdBits;

    /**
     * 时间截向左移22位(5+5+12)
     */
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    /**
     * 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)
     */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /**
     * 工作机器ID(0~31)
     */
    private long workerId;

    /**
     * 数据中心ID(0~31)
     */
    private long datacenterId;

    /**
     * 毫秒内序列(0~4095)
     */
    private long sequence = 0L;

    /**
     * 上次生成ID的时间截
     */
    private long lastTimestamp = -1L;

    /**
     * 构造函数
     *
     * @param workerId     工作ID (0~31)
     * @param datacenterId 数据中心ID (0~31)
     */
    private SnowFlake(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    /**
     * 获得下一个ID (该方法是线程安全的)
     *
     * @return SnowflakeId
     */
    private synchronized long nextId() {
        //获取当前时间
        long timestamp = timeGen();

        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            //这个时候如何补救，以及采用什么方法来解决这个问题
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        //如果是同一时间生成的，则进行毫秒内序列 同一个毫秒内最多生成 4096个 id
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            //毫秒内序列溢出
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        //时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }

        //上次生成ID的时间截
        lastTimestamp = timestamp;
        //打印二进制的数字，便于查看
        binaryDisplay((timestamp - twepoch), timestampLeftShift, datacenterId, datacenterIdShift, workerId, workerIdShift, sequence);

        //移位并通过或运算拼到一起组成64位的ID
        long result = ((timestamp - twepoch) << timestampLeftShift)
                | (datacenterId << datacenterIdShift)
                | (workerId << workerIdShift)
                | sequence;
        System.out.println();
        System.out.println("最后计算结果");
        System.out.println("ID：" + result);
        return result;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     *
     * @return 当前时间(毫秒)
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }

    /**
     * 数字转二进制
     *
     * @param num 十进制数字
     */
    private void num2Binary(long num, boolean isStamp, boolean isCenter, boolean isWorker) {
        StringBuilder builder = new StringBuilder();
        int spilt = 0;
        for (int i = 63; i >= 0; i--) {
            spilt++;
            builder.append(num >>> i & 1);
            if (spilt % 8 == 0) {
                builder.append(" ");
            }
            if (isStamp) {
                if (spilt == 1 || spilt == 42) {
                    builder.append("|");
                }
            }
            if (isCenter) {
                if (spilt == 47) {
                    builder.append("|");
                }
            }
            if (isWorker) {
                if (spilt == 52) {
                    builder.append("|");
                }
            }

        }
        System.out.println(builder);

    }

    private void binaryDisplay(long stamp, long stampShift, long centerId, long centerIdShift, long workerId, long workIdShift, long sequence) {
        //时间戳移位
        System.out.println("时间戳时间：" + stamp);
        emptyBlank((int) (stampShift + 3), "|<--左移" + stampShift + "位");
        num2Binary(stamp, true, false, false);
        num2Binary((stamp << timestampLeftShift), true, false, false);
        emptyBlank(49, "|-->后面补0");
        //datacenterId
        System.out.println("datacenterId：" + centerId);
        emptyBlank(19, "|<--左移" + centerIdShift + "位");
        num2Binary(centerId, true, true, true);
        num2Binary((centerId << centerIdShift), true, true, true);
        emptyBlank(55, "|-->后面补0");
        //workerId
        System.out.println("workerId：" + workerId);
        emptyBlank(13, "|<--左移" + workIdShift + "位");
        num2Binary(workerId, true, true, true);
        num2Binary((workerId << workIdShift), true, true, true);
        emptyBlank(62, "|-->后面补0");
        //sequence
        System.out.println("sequence：" + sequence);
        num2Binary(sequence, false, false, false);

        //求或运算
        System.out.println();
        System.out.println("由以上4个数据进行或运算");
        num2Binary((stamp << timestampLeftShift), true, true, true);
        num2Binary((centerId << centerIdShift), true, true, true);
        num2Binary((workerId << workIdShift), true, true, true);
        num2Binary(sequence, true, true, true);
        System.out.println("OR--------------------------------------------------------------------------");
        num2Binary((stamp << timestampLeftShift)
                | (datacenterId << datacenterIdShift)
                | (workerId << workerIdShift)
                | sequence, true, true, true);
    }

    private void emptyBlank(int start, String explain) {
        String blank = "                                                                        ";
        StringBuilder builder = new StringBuilder(blank);
        System.out.println(builder.replace(start, totalBit, explain));
    }

    public static void main(String[] args) {
        System.out.println();
        SnowFlake snowFlake = new SnowFlake(3, 5);


        snowFlake.nextId();

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
    }
}
