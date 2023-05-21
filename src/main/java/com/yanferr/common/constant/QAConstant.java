package com.yanferr.common.constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class QAConstant {
    public final static HashMap<Integer,Long[]> RTMAP = new HashMap<Integer,Long[]>(){{
        put(1, new Long[]{5 * 60 * 1000L, 12 * 60 * 60 * 1000L});
        put(2, new Long[]{30 * 60 * 1000L, 12 * 60 * 60 * 1000L});
        put(3, new Long[]{12 * 60 * 60 * 1000L, 12 * 60 * 60 * 1000L});
        put(4, new Long[]{24 * 60 * 60 * 1000L, 12 * 60 * 60 * 1000L});
        put(5, new Long[]{2 * 24 * 60 * 60 * 1000L, 12 * 60 * 60 * 1000L});
        put(6, new Long[]{4 * 24 * 60 * 60 * 1000L, 24 * 60 * 60 * 1000L});
        put(7, new Long[]{7 * 24 * 60 * 60 * 1000L,  24 * 60 * 60 * 1000L});
        put(8, new Long[]{15 * 24 * 60 * 60 * 1000L, 24 * 60 * 60 * 1000L});
        put(9, new Long[]{30L * 24 * 60 * 60 * 1000,  24 * 60 * 60 * 1000L});
        put(10, new Long[]{30L * 24 * 60 * 60 * 1000,  24 * 60 * 60 * 1000L});
        put(11, new Long[]{30L * 24 * 60 * 60 * 1000,  24 * 60 * 60 * 1000L});
        put(12, new Long[]{30L * 24 * 60 * 60 * 1000,  24 * 60 * 60 * 1000L});
        put(13, new Long[]{30L * 24 * 60 * 60 * 1000,  24 * 60 * 60 * 1000L});
    }};

    public enum IntervalEnum{

        DAY("1",24*60*60*1000L),
        WEEK("7",7*24*60*60*1000L),
        MONTH("30",30*24*60*60*1000L);

        final private String interval;
        final private Long timeStamp;

        IntervalEnum(String interval,Long timeStamp) {
            this.interval = interval;
            this.timeStamp=timeStamp;
        }

        public String getInterval() {
            return interval;
        }

        public Long getTimeStamp() {
            return timeStamp;
        }
    }

    public enum StatusEnum{

        STATUS_FINISH(1,"12h内点击是否通过的的问题"),
        STATUS_START(2,"到提醒时间"),
        STATUS_DELAY(3,"超过提醒时间");

        final private int status;
        final private String msg;

        StatusEnum(int status,String msg) {
            this.status = status;
            this.msg=msg;
        }

        public int getStatus() {
            return status;
        }

        public String getMsg() {
            return msg;
        }
    }
    public enum RestEnum{

        REST_START(22,"开始休息的时间"),
        REST_END(7,"结束休息的时间");

        final private int hour;
        final private String msg;

        RestEnum(int hour, String msg) {
            this.hour = hour;
            this.msg = msg;
        }

        public int getHour() {
            return hour;
        }

        public String getMsg() {
            return msg;
        }
    }
    public enum SceneEnum{

        SCENE_TYPE_INTERVIEW(0,"interview"),SCENE_TYPE_DAILY(1,"daily"),SCENE_TYPE_DEBUG(2,"debug"),SCENE_TYPE_OTHER(3,"其他");

        private int value;
        private String msg;

        SceneEnum(int value, String msg) {
            this.value = value;
            this.msg = msg;
        }

        public int getValue() {
            return value;
        }

        public String getMsg() {
            return msg;
        }
    }

}
