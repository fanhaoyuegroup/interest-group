package com.fan.design.build;

public class 房间 {
    private String 客厅;
    private String 主卧;
    private String 次卧;
    private String 卫生间;
    private String 主卧卫生间;

    public 房间(){}

    public String get客厅() {
        return 客厅;
    }

    public void set客厅(String 客厅) {
        this.客厅 = 客厅;
    }

    public String get主卧() {
        return 主卧;
    }

    public void set主卧(String 主卧) {
        this.主卧 = 主卧;
    }

    public String get次卧() {
        return 次卧;
    }

    public void set次卧(String 次卧) {
        this.次卧 = 次卧;
    }

    public String get卫生间() {
        return 卫生间;
    }

    public void set卫生间(String 卫生间) {
        this.卫生间 = 卫生间;
    }

    public String get主卧卫生间() {
        return 主卧卫生间;
    }

    public void set主卧卫生间(String 主卧卫生间) {
        this.主卧卫生间 = 主卧卫生间;
    }

    @Override
    public String toString() {
        return "房间{" +
                "客厅='" + 客厅 + '\'' +
                ", 主卧='" + 主卧 + '\'' +
                ", 次卧='" + 次卧 + '\'' +
                ", 卫生间='" + 卫生间 + '\'' +
                ", 主卧卫生间='" + 主卧卫生间 + '\'' +
                '}';
    }
}
