package com.fan.design.build;

public interface Builder {
    Builder build客厅();
    Builder build主卧();
    Builder build次卧();
    Builder build卫生间();
    Builder build主卧卫生间();
    房间 build();
}
