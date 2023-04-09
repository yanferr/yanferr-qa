package com.yanferr.qa.service.impl;

import com.yanferr.qa.entity.AnswerEntity;
import com.yanferr.qa.entity.LabelEntity;
import com.yanferr.qa.entity.QuesLabelRelationEntity;
import com.yanferr.qa.service.AnswerService;
import com.yanferr.qa.service.LabelService;
import com.yanferr.qa.service.QuesLabelRelationService;
import com.yanferr.qa.to.QuesLabelTo;
import com.yanferr.qa.vo.QuesAnswerVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
    private LabelService labelService;

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

    @Override
    @Transactional
    public void removeQAByIds(List<Long> quesIds) {
        // 删除对应答案
        for (Long quesId : quesIds) {
            QuesEntity ques = this.getById(quesId);
            answerService.removeById(ques.getAnswerId());
        }
        this.removeByIds(quesIds);
    }

    /**
     * 通过labelId获取所有ques
     */
    @Override
    public List<QuesLabelTo> listByLabelId(Long labelId) {  // todo：循环查库

         // 根据所有子标签的id获取所有关联关系实体
        // List<QuesLabelRelationEntity> entities = quesLabelRelationService.list(new QueryWrapper<QuesLabelRelationEntity>().in("label_id", labelIds));
        // 存在quesEntity重复的问题，需要手动SQL去重
        List<QuesLabelRelationEntity> entities=null;
        if(labelId!=0){ // todo：会出现quesEntity重复，需要手动写SQL
            List<Long> labelIds = labelService.getChildrenLabelIdsByLabelId(labelId);
            labelIds.add(labelId);
            entities = quesLabelRelationService.list(new QueryWrapper<QuesLabelRelationEntity>().in("label_id", labelIds));


        }else{
             entities = this.baseMapper.selectDistinctQuesByQuesId();
        }
        if(entities.size() !=0){
            // 根据
            List<QuesLabelTo> toList = entities.stream().map(relationEntity -> {
                QuesLabelTo quesLabelTo = new QuesLabelTo();
                // 1. 通过labelid去查label，
                LabelEntity labelEntity = labelService.getById(relationEntity.getLabelId());
                // 2. 通过quesid去查ques
                QuesEntity quesEntity = this.getById(relationEntity.getQuesId());
                BeanUtils.copyProperties(quesEntity, quesLabelTo);
                BeanUtils.copyProperties(labelEntity, quesLabelTo);
                return quesLabelTo;
            }).collect(Collectors.toList());
            return toList;
        }else{
            return null;
        }

    }


}