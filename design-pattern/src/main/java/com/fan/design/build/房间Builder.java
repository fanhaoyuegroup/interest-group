package com.fan.design.build;

public class 房间Builder implements Builder {

    private 房间 room;

    public 房间Builder(房间 room){
        this.room = room;
    }

    @Override
    public Builder build客厅() {
        room.set客厅("豪华客厅");
        return this;
    }

    @Override
    public Builder build主卧() {
        room.set主卧("豪华主卧");
        return this;
    }

    @Override
    public Builder build次卧() {
        room.set次卧("豪华次卧");
        return this;
    }

    @Override
    public Builder build卫生间() {
        room.set卫生间("高档卫生间");
        return this;
    }

    @Override
    public Builder build主卧卫生间() {
        room.set主卧卫生间("豪华卫生间");
        return this;
    }

    @Override
    public 房间 build() {
        return room;
    }
}
