package com.yanferr.qa.vo;

import lombok.Data;

@Data
public class QuesLabelRelationVo {

    private Long labelId;
    private String labelName;

    // 标签下的问题个数
    private Integer nums;

}
