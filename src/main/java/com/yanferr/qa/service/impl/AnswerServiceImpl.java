package com.yanferr.qa.service.impl;

import com.yanferr.common.utils.PageUtils;
import com.yanferr.common.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.yanferr.qa.dao.AnswerDao;
import com.yanferr.qa.entity.AnswerEntity;
import com.yanferr.qa.service.AnswerService;


@Service("answerService")
public class AnswerServiceImpl extends ServiceImpl<AnswerDao, AnswerEntity> implements AnswerService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AnswerEntity> page = this.page(
                new Query<AnswerEntity>().getPage(params),
                new QueryWrapper<AnswerEntity>()
        );

        return new PageUtils(page);
    }

}