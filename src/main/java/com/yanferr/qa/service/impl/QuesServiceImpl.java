package com.yanferr.qa.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanferr.common.utils.PageUtils;
import com.yanferr.common.utils.Query;

import com.yanferr.qa.dao.QuesDao;
import com.yanferr.qa.entity.QuesEntity;
import com.yanferr.qa.service.QuesService;


@Service("quesService")
public class QuesServiceImpl extends ServiceImpl<QuesDao, QuesEntity> implements QuesService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<QuesEntity> page = this.page(
                new Query<QuesEntity>().getPage(params),
                new QueryWrapper<QuesEntity>()
        );

        return new PageUtils(page);
    }

}