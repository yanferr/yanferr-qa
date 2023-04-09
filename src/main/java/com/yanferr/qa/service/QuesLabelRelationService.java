package com.yanferr.qa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yanferr.common.utils.PageUtils;
import com.yanferr.qa.entity.QuesEntity;
import com.yanferr.qa.entity.QuesLabelRelationEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author ${author}
 * @email ${email}
 * @date 2023-04-06 18:02:57
 */
public interface QuesLabelRelationService extends IService<QuesLabelRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);


    void updateByQuesId(Long quesId, Long[] labelIds);
}

