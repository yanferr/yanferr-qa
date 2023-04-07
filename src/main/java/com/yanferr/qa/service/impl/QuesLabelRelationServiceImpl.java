package com.yanferr.qa.service.impl;

import com.yanferr.common.utils.PageUtils;
import com.yanferr.common.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.yanferr.qa.dao.QuesLabelRelationDao;
import com.yanferr.qa.entity.QuesLabelRelationEntity;
import com.yanferr.qa.service.QuesLabelRelationService;


@Service("quesLabelRelationService")
public class QuesLabelRelationServiceImpl extends ServiceImpl<QuesLabelRelationDao, QuesLabelRelationEntity> implements QuesLabelRelationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<QuesLabelRelationEntity> page = this.page(
                new Query<QuesLabelRelationEntity>().getPage(params),
                new QueryWrapper<QuesLabelRelationEntity>()
        );

        return new PageUtils(page);
    }

}