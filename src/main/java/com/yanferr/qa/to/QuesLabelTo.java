package com.yanferr.qa.to;

import com.yanferr.qa.entity.QuesEntity;
import lombok.Data;

@Data
public class QuesLabelTo extends QuesEntity {
    private Long labelId;
    private String labelName;
}
