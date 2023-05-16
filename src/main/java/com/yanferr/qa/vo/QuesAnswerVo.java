package com.yanferr.qa.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.yanferr.qa.entity.LabelEntity;
import com.yanferr.qa.entity.QuesEntity;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class QuesAnswerVo {

    private Long quesId;
    private Long answerId;
    /**
     * 源url
     */
    private String sourceUrl;

    /**
     * 源名称
     */
    private String source;

    /**
     * 难度
     */
    private String difficulty;

    //  答案
    @NotBlank(message = "答案必须不为空")
    @NotNull(message = "答案不能为空")
    private String answer;

    /**
     * 问题
     */
    @NotBlank(message = "问题必须不为空")
    @NotNull(message = "问题不能为空")
    private String ques;

    /**
     * 标签
     * tb_ques_label_relation
     */
    // @NotNull(message = "至少选择一个标签")
    private String[] labelNames;

}
