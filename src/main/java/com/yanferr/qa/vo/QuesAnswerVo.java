package com.yanferr.qa.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.yanferr.qa.entity.LabelEntity;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class QuesAnswerVo {
    private Long quesId;
    private Long answerId;
    //  tb_answer
    @NotBlank(message = "答案必须不为空")
    @NotNull(message = "答案不能为空")
    private String answer;

    // tb_ques
    private String scene;

    private String source;


    @NotBlank(message = "问题必须不为空")
    @NotNull(message = "问题不能为空")
    private String ques;

    // tb_ques_label_relation
    // @NotNull(message = "至少选择一个标签")
    private String[] labelNames;

}
