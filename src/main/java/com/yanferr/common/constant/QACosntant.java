package com.yanferr.common.constant;

public class QACosntant {
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
