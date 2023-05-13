package com.yanferr.common.constant;

/**
 * remindTime常量
 */
public enum RTEnum {

    Lv1(1,5 * 60 * 1000L, 12 * 60 * 60 * 1000L),
    Lv2(2,30 * 60 * 1000L, 12 * 60 * 60 * 1000L),
    Lv3(3,12 * 60 * 60 * 1000L, 12 * 60 * 60 * 1000L),
    Lv4(4,24 * 60 * 60 * 1000L, 12 * 60 * 60 * 1000L),
    Lv5(5,2 * 24 * 60 * 60 * 1000L, 12 * 60 * 60 * 1000L),
    Lv6(6,4 * 24 * 60 * 60 * 1000L, 24 * 60 * 60 * 1000L),
    Lv7(7,7 * 24 * 60 * 60 * 1000L,  24 * 60 * 60 * 1000L),
    Lv8(8,15 * 24 * 60 * 60 * 1000L, 24 * 60 * 60 * 1000L),
    Lv9(9,30L * 24 * 60 * 60 * 1000,  24 * 60 * 60 * 1000L);


    private final int lv; // 记忆等级
    private final Long start; // 开始提醒的时间
    private final Long duration; // 提醒持续的时间

    RTEnum(int lv,Long start, Long duration) {
        this.start = start;
        this.duration = duration;
        this.lv = lv;
    }
    public int getLv() {
        return lv;
    }

    public Long getStart() {
        return start;
    }

    public Long getEnd() {
        return duration;
    }
}
