package com.yanferr.qa.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tb_remind")
public class RemindEntity {

    private Long quesId;
    private Date reviewOn;
    private Date delayOn;

}
