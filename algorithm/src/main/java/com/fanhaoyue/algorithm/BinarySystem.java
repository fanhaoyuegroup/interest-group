package com.fanhaoyue.algorithm;

/**
 * Class description
 *
 *
 * @version        $version$, 18/12/01
 * @author         江蓠
 */
public class BinarySystem {

    /**
     * Method description改变指定位置二进制位为1
     *
     *
     * @param index
     * @param num
     *
     * @return
     */
    public int SetIndexIsOne(int index, int num) {
        return num = num | (1 << index - 1);
    }

    /**
     * Method description
     *
     *改变指定位置二进制位为0
     * @param index
     * @param num
     *
     * @return
     */
    public int SetIndexIsZero(int index, int num) {
        return num = num & (~(1 << (index - 1)));
    }


}


//~ Formatted by Jindent --- http://www.jindent.com
