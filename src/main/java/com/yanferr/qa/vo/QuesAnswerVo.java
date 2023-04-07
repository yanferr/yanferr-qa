package com.yanferr.qa.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class QuesAnswerVo {

    private String answer;

    private String scene;

    private String source;

    private String[] labels;

    private String ques;




}
