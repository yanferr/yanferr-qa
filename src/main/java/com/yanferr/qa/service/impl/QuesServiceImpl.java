package com.yanferr.qa.service.impl;

import com.yanferr.qa.dao.LabelDao;
import com.yanferr.qa.dao.QuesLabelRelationDao;
import com.yanferr.qa.entity.AnswerEntity;
import com.yanferr.qa.entity.LabelEntity;
import com.yanferr.qa.entity.QuesLabelRelationEntity;
import com.yanferr.qa.service.AnswerService;
import com.yanferr.qa.service.LabelService;
import com.yanferr.qa.service.QuesLabelRelationService;
import com.yanferr.qa.to.QuesLabelTo;
import com.yanferr.qa.vo.QuesAnswerVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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

    @Autowired
    private QuesDao quesDao;

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private QuesLabelRelationDao quesLabelRelationDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        // 计算highLight;
        setHighLight();
        IPage<QuesEntity> page = this.page(
                new Query<QuesEntity>().getPage(params),
                new QueryWrapper<QuesEntity>().orderByDesc("high_light").orderByDesc("create_time")
        );
        return new PageUtils(page);
    }

    private void setHighLight() {
        List<QuesEntity> list = this.list();
        long now = new Date().getTime();

        for (QuesEntity quesEntity : list) {
            if (quesEntity.getHighLight() != null && quesEntity.getHighLight() == -1) { // 永久不高亮
                continue;
            }
            if (quesEntity.getMemoryLevel() == 0 || quesEntity.getMemoryLevel() == 9) { // 不加入记忆计划或记忆等级达到最高
                continue;
            }
            long lastView = quesEntity.getLastView().getTime();
            long diff = now - lastView;
            if (quesEntity.getMemoryLevel() == 1) {
                if (diff > 5 * 60 * 1000) {
                    if (diff < 5 * 60 * 1000 + 12 * 60 * 60 * 1000) {
                        quesEntity.setHighLight(1);
                    } else {
                        quesEntity.setHighLight(2);
                    }
                }
            }
            if (quesEntity.getMemoryLevel() == 2) {
                if (diff > 30 * 60 * 1000) {
                    if (diff < 30 * 60 * 1000 + 12 * 60 * 60 * 1000) {
                        quesEntity.setHighLight(1);
                    } else {
                        quesEntity.setHighLight(2);
                    }
                }
            }
            if (quesEntity.getMemoryLevel() == 3) {
                if (diff > 12 * 60 * 60 * 1000) {
                    if (diff < 12 * 60 * 60 * 1000 + 12 * 60 * 60 * 1000) {
                        quesEntity.setHighLight(1);
                    } else {
                        quesEntity.setHighLight(2);
                    }
                }
            }
            if (quesEntity.getMemoryLevel() == 4) {
                if (diff > 24 * 60 * 60 * 1000) {
                    if (diff < 24 * 60 * 60 * 1000 + 12 * 60 * 60 * 1000) {
                        quesEntity.setHighLight(1);
                    } else {
                        quesEntity.setHighLight(2);
                    }
                }
            }
            if (quesEntity.getMemoryLevel() == 5) {
                if (diff > 2 * 24 * 60 * 60 * 1000) {
                    if (diff < 2 * 24 * 60 * 60 * 1000 + 12 * 60 * 60 * 1000) {
                        quesEntity.setHighLight(1);
                    } else {
                        quesEntity.setHighLight(2);
                    }
                }
            }
            if (quesEntity.getMemoryLevel() == 6) {
                if (diff > 4 * 24 * 60 * 60 * 1000) {
                    if (diff < 4 * 24 * 60 * 60 * 1000 + 12 * 60 * 60 * 1000) {
                        quesEntity.setHighLight(1);
                    } else {
                        quesEntity.setHighLight(2);
                    }
                }
            }
            if (quesEntity.getMemoryLevel() == 7) {
                if (diff > 7 * 24 * 60 * 60 * 1000) {
                    if (diff < 7 * 24 * 60 * 60 * 1000 + 12 * 60 * 60 * 1000) {
                        quesEntity.setHighLight(1);
                    } else {
                        quesEntity.setHighLight(2);
                    }
                }
            }
            if (quesEntity.getMemoryLevel() == 8) {
                if (diff > 15 * 24 * 60 * 60 * 1000) {
                    if (diff < 15 * 24 * 60 * 60 * 1000 + 12 * 60 * 60 * 1000) {
                        quesEntity.setHighLight(1);
                    } else {
                        quesEntity.setHighLight(2);
                    }
                }
            }
        }
        List<QuesEntity> collect = list.stream().map(item -> {
            QuesEntity quesEntity = new QuesEntity();
            quesEntity.setQuesId(item.getQuesId());
            quesEntity.setHighLight(item.getHighLight());
            return quesEntity;
        }).collect(Collectors.toList());
        this.updateBatchById(collect);
    }

    @Override
    @Transactional
    public void saveOrUpdateQuestion(QuesAnswerVo quesVo) {
        AnswerEntity answerEntity = new AnswerEntity();
        BeanUtils.copyProperties(quesVo, answerEntity);
        QuesEntity quesEntity = new QuesEntity();
        BeanUtils.copyProperties(quesVo, quesEntity);
        quesEntity.setUpdateTime(new Date());
        if (quesVo.getQuesId() != null && quesVo.getAnswerId() != null) {
            // 更新操作
            answerService.updateById(answerEntity);
            this.updateById(quesEntity);

        } else {
            // 保存操作
            quesEntity.setCreateTime(new Date());
            quesEntity.setLastView(new Date());
            quesEntity.setMemoryLevel(1);
            answerService.save(answerEntity);
            quesEntity.setAnswerId(answerEntity.getAnswerId());
            this.save(quesEntity);
        }

        // 先删除关联关系
        quesLabelRelationDao.delete(new QueryWrapper<QuesLabelRelationEntity>()
                .eq("ques_id", quesEntity.getQuesId()));
        // 3.保存标签和关联关系
        if (quesVo.getLabelNames() != null && quesVo.getLabelNames().length != 0) {
            for (String labelName : quesVo.getLabelNames()) {

                LabelEntity labelEntity = labelDao.selectOne(new QueryWrapper<LabelEntity>().
                        eq("label_name", labelName));
                // 找不到就新增一个标签
                if (labelEntity == null) {

                    labelEntity = new LabelEntity();
                    labelEntity.setLabelName(labelName);
                    labelDao.insert(labelEntity);
                }
                // 保存关联关系
                QuesLabelRelationEntity relation = new QuesLabelRelationEntity();
                relation.setQuesId(quesEntity.getQuesId());
                relation.setLabelId(labelEntity.getLabelId());
                quesLabelRelationDao.insert(relation);
            }
        }

    }

    @Override
    @Transactional
    public void removeQAByIds(List<Long> quesIds) {
        // 删除对应答案和对应的标签关系
        for (Long quesId : quesIds) {
            QuesEntity ques = this.getById(quesId);
            answerService.removeById(ques.getAnswerId());
            quesLabelRelationDao.delete(new QueryWrapper<QuesLabelRelationEntity>()
                    .eq("ques_id", quesId));
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
        List<QuesLabelRelationEntity> entities = null;
        if (labelId != 0) { // todo：会出现quesEntity重复，需要手动写SQL
            List<Long> labelIds = labelService.getChildrenLabelIdsByLabelId(labelId);
            labelIds.add(labelId);
            entities = quesLabelRelationService.list(new QueryWrapper<QuesLabelRelationEntity>().in("label_id", labelIds));


        } else {
            entities = this.baseMapper.selectDistinctQuesByQuesId();
        }
        if (entities.size() != 0) {
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
        } else {
            return null;
        }

    }

    @Override
    public QuesAnswerVo queryLastedQuesAndAnswer() {
        QuesEntity quesEntity = this.baseMapper.selectOne(
                new QueryWrapper<QuesEntity>()
                        .orderByDesc("create_time").last("LIMIT 1"));
        AnswerEntity answerEntity = answerService.getBaseMapper()
                .selectOne(new QueryWrapper<AnswerEntity>().eq("answer_id", quesEntity.getAnswerId()));
        QuesAnswerVo quesAnswerVo = new QuesAnswerVo();
        BeanUtils.copyProperties(answerEntity, quesAnswerVo);
        BeanUtils.copyProperties(quesEntity, quesAnswerVo);
        return quesAnswerVo;
    }

    @Override
    public QuesAnswerVo backOrFront(int pageIndex) {
        return this.baseMapper.queryBackOrFront(pageIndex);
    }

    @Override
    public List<QuesEntity> findQuesWithin(String search) {
        QueryWrapper<QuesEntity> queryWrapper = new QueryWrapper<>();

        return this.baseMapper.selectQuesWithin(Integer.parseInt(search));
    }

    @Override
    public List<QuesEntity> findQuesLike(String search) {
        return this.baseMapper.selectList(new QueryWrapper<QuesEntity>().like("ques", search));
    }


    @Override
    @Transactional
    public void updateHighLight(Long quesId) {
        // 如果高亮等级为2则点击后降级，如果为1则点击后升级，如果为0则不更新lastView
        QuesEntity quesEntity = this.getById(quesId);
        QuesEntity updateEntity = new QuesEntity();
        updateEntity.setQuesId(quesEntity.getQuesId());
        Integer highLight = quesEntity.getHighLight();
        if (highLight == 0 || highLight == -1) return;
        updateEntity.setLastView(new Date());
        if (highLight == 1) {
            if (quesEntity.getMemoryLevel() == 8) {
                updateEntity.setHighLight(-1); // 永久不高亮
                updateEntity.setMemoryLevel(9);
                this.updateById(updateEntity);
                return;
            }
            updateEntity.setMemoryLevel(quesEntity.getMemoryLevel() + 1);
            updateEntity.setHighLight(0);
        } else if (highLight == 2) {
            updateEntity.setMemoryLevel(quesEntity.getMemoryLevel() == 1 ? quesEntity.getMemoryLevel() : quesEntity.getMemoryLevel() - 1);
            updateEntity.setHighLight(0);
        }
        this.updateById(updateEntity);
    }

    /**
     * 取消高亮
     */
    @Override
    public boolean cancelHL(List<Long> quesIds) {
        ArrayList<QuesEntity> quesEntities = new ArrayList<>();
        for (Long quesId : quesIds) {
            QuesEntity quesEntity = new QuesEntity();
            quesEntity.setQuesId(quesId);
            quesEntity.setHighLight(-1);
            quesEntities.add(quesEntity);
        }

        return this.updateBatchById(quesEntities);
    }
}