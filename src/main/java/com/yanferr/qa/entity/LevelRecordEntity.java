package com.yanferr.qa.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tb_level_record")
public class LevelRecordEntity {

    private static final long serialVersionUID = 1L;
    @TableId
    private Long id;

    private Long quesId;

    private String level;

    private String changeDate;
    private String master;





}
