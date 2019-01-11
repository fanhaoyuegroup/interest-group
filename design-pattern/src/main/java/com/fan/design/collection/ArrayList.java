package com.fan.design.collection;

public class ArrayList<T> {
    /*
     * 默认初始化数组大小
     */
    private static int DEFAULT_CAPACITY = 10;

    private static int ZERO = 0;

    private Object[] object;

    private int size;

    private int elementNum = 0;

    public ArrayList() {
        object = new Object[DEFAULT_CAPACITY];
    }

    public ArrayList(int initCapacity) {
        if (initCapacity > 0) {
            this.size = initCapacity;
            object = new Object[size];
        } else if (initCapacity == ZERO) {
            this.size = ZERO;
            object = new Object[size];
        }else {
            throw new IllegalArgumentException("违法参数");
        }
    }

    /**
     * 尾部添加一个元素
     * @param e 元素
     */
    public void add(T e){
        //检查是否需要扩容
        resize();
        object[size++] = e;
    }

    /**
     * 指定位置添加一个元素
     * @param e
     * @param index
     */
    public void add(T e,int index){

    }

    /**
     * 删除Index下的元素
     * @param index
     */
    public void remove(int index){

    }

    private int getSize(){
        return object.length;
    }

    private void resize(){

    }
}
