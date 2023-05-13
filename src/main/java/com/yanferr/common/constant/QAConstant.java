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
