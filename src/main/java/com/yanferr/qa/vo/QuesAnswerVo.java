package com.yanferr.qa.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class QuesAnswerVo {
    //  tb_answer
    private String answer;

    // tb_ques
    private String scene;

    private String source;

    private String ques;

    // tb_ques_label_relation
    private long[] labelIds;





}
