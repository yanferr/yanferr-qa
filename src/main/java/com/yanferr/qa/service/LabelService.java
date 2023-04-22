package com.yanferr.qa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yanferr.common.utils.PageUtils;
import com.yanferr.qa.entity.LabelEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author ${author}
 * @email ${email}
 * @date 2023-04-06 18:02:57
 */
public interface LabelService extends IService<LabelEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<LabelEntity> listWithTree(Long parentId);

    List<Long> getChildrenLabelIdsByLabelId(Long labelId);

    List<LabelEntity> listWithQuesId(Long quesId);

}

