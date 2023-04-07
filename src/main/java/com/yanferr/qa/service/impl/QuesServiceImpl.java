package com.yanferr.qa.service.impl;

import com.yanferr.qa.entity.AnswerEntity;
import com.yanferr.qa.entity.QuesLabelRelationEntity;
import com.yanferr.qa.service.AnswerService;
import com.yanferr.qa.service.LabelService;
import com.yanferr.qa.service.QuesLabelRelationService;
import com.yanferr.qa.vo.QuesAnswerVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanferr.common.utils.PageUtils;
import com.yanferr.common.utils.Query;

import com.yanferr.qa.dao.QuesDao;
import com.yanferr.qa.entity.QuesEntity;
import com.yanferr.qa.service.QuesService;
import org.springframework.transaction.annotation.Transactional;


@Service("quesService")
public class QuesServiceImpl extends ServiceImpl<QuesDao, QuesEntity> implements QuesService {

    @Autowired
    private AnswerService answerService;

    @Autowired
    private QuesLabelRelationService quesLabelRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<QuesEntity> page = this.page(
                new Query<QuesEntity>().getPage(params),
                new QueryWrapper<QuesEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void saveQuestion(QuesAnswerVo quesVo) {
        // 1.保存答案
        AnswerEntity answerEntity = new AnswerEntity();
        answerEntity.setAnswer(quesVo.getAnswer());
        answerService.save(answerEntity);
        // 2.保存问题
        QuesEntity quesEntity = new QuesEntity();
        quesEntity.setUpdateTime(new Date());
        quesEntity.setCreateTime(new Date());
        quesEntity.setAnswerId(answerEntity.getAnswerId());
        BeanUtils.copyProperties(quesVo,quesEntity);
        this.save(quesEntity);

        // 3.保存标签关联关系
        if(quesVo.getLabelIds()!=null){
            for (long labelId : quesVo.getLabelIds()) {
                QuesLabelRelationEntity entity = new QuesLabelRelationEntity();
                entity.setQuesId(quesEntity.getQuesId());
                entity.setLabelId(labelId);
                quesLabelRelationService.save(entity);
            }
        }

    }
}