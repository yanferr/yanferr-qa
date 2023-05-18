package com.yanferr.qa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanferr.common.constant.QAConstant;
import com.yanferr.common.utils.PageUtils;
import com.yanferr.common.utils.Query;
import com.yanferr.qa.dao.*;
import com.yanferr.qa.entity.AnswerEntity;
import com.yanferr.qa.entity.LabelEntity;
import com.yanferr.qa.entity.QuesEntity;
import com.yanferr.qa.entity.QuesLabelRelationEntity;
import com.yanferr.qa.service.*;
import com.yanferr.qa.to.QuesLabelTo;
import com.yanferr.qa.vo.QuesAnswerVo;
import com.yanferr.qa.vo.Search;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


@Service("quesService")
public class QuesServiceImpl extends ServiceImpl<QuesDao, QuesEntity> implements QuesService {

    @Resource
    private AnswerService answerService;

    @Resource
    private LabelService labelService;

    @Resource
    private QuesLabelRelationService quesLabelRelationService;

    @Resource
    private LevelRecordService levelRecordService;

    @Resource
    private LabelDao labelDao;

    @Resource
    LevelRecordDao levelRecordDao;

    @Resource
    private QuesLabelRelationDao quesLabelRelationDao;

    @Resource
    private QuesDao quesDao;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<QuesEntity> queryWrapper = new QueryWrapper<>();
        // 搜索参数
        if (!"".equals(params.get("labelId"))) {
            List<Long> quesIds = quesLabelRelationService.list(
                            new QueryWrapper<QuesLabelRelationEntity>().eq("label_id", params.get("labelId")))
                    .stream().map(QuesLabelRelationEntity::getQuesId).collect(Collectors.toList());
            if(quesIds.size()!=0){
                queryWrapper.in("ques_id", quesIds);
            }
            // todo:else返回空数据
        }
        if (!"".equals(params.get("content"))) {
            queryWrapper.like("ques", params.get("content"));
        }
        if (!"".equals(params.get("timeInterval"))) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date now = new Date();
            String date = "";
            if ("1".equals(params.get("timeInterval"))) {
                date = df.format(new Date(now.getTime() - 24 * 60 * 60 * 1000L));
            } else if ("7".equals(params.get("timeInterval"))) {
                date = df.format(new Date(now.getTime() - 7 * 24 * 60 * 60 * 1000L));
            } else if ("30".equals(params.get("timeInterval"))) {
                date = df.format(new Date(now.getTime() - 30 * 24 * 60 * 60 * 1000L));
            }
            queryWrapper.ge("create_time", date);

        }
        if (!"".equals(params.get("difficulty"))) {
            queryWrapper.eq("difficulty", params.get("difficulty"));
        }

        // 显示正在提醒的问题
        if (params.get("remind") != null && "true".equals(params.get("remind"))) {
            queryWrapper.le("review_on", new Date());
        }

        // 默认按创建时间降序
        queryWrapper.orderByDesc("create_time");

        // todo:改成消息队列，到提醒时间的时候做标记


        IPage<QuesEntity> page = this.page(
                new Query<QuesEntity>().getPage(params),
                queryWrapper
        );

        // 计算status
        page.setRecords(calStatus(page.getRecords()));
        return new PageUtils(page);
    }

    private List<QuesEntity> calStatus(List<QuesEntity> records) {
        long now = new Date().getTime();
        for (QuesEntity record : records) {
            if (record.getStatus() != null && record.getStatus() == 0) {
                continue;
            }
            long start = record.getReviewOn().getTime();
            long end = record.getDelayOn().getTime();

            if (record.getLastView()!=null && now - record.getLastView().getTime()<= 12*60*60*1000L) { // 12h内点击是否通过的的问题设置为状态1
                record.setStatus(1);
            }
            if (now >= start) { // 到提醒时间
                record.setStatus(2);
            }
            if (now >= end) { // 超过提醒时间
                record.setStatus(3);
            }
        }
        return records;
    }

    /**
     * 获取ques列表的时候调用
     * 根据记忆等级计算下次复习时间和持续提醒时间
     *
     * @return
     */
    @Override
    public boolean calNextReview(QuesEntity quesEntity) {
        Integer memoryLevel = quesEntity.getMemoryLevel();
        long now = new Date().getTime();
        long reviewOn = now + QAConstant.RTMAP.get(memoryLevel)[0];// todo:改成从配置文件获取
        long during = QAConstant.RTMAP.get(memoryLevel)[1];

        reviewOn += randomTime(memoryLevel);
        long delayHour = restHour(reviewOn);
        quesEntity.setReviewOn(new Date(reviewOn + delayHour));
        quesEntity.setDelayOn(new Date(reviewOn + during + delayHour));

        return this.saveOrUpdate(quesEntity, new QueryWrapper<QuesEntity>().eq("ques_id", quesEntity.getQuesId()));
    }

    /**
     * 随机时间
     *
     * @return
     */
    private long randomTime(int memoryLevel) { // todo:根据通过的权重计算
        long res = 0L;
        if (memoryLevel <= 5) {
            res = (long) (Math.random() * 6 * 60 * 60 * 1000);
        } else {
            res = (long) (Math.random() * 12 * 60 * 60 * 1000);
        }
        return res;
    }

    /**
     * @param reviewOn 预计提醒时间
     * @return 需要延迟的小时数
     */
    private Long restHour(long reviewOn) { // todo：结束提醒时间适配作息时间
        // 如果22~7是休息时间，则21~7这个时间段提醒的问题要后延
        int reviewHour = Integer.parseInt(new SimpleDateFormat("HH").format(reviewOn));
        int res = 0;
        int start = QAConstant.RestEnum.REST_START.getHour(); // todo：从配置文件获取
        int end = QAConstant.RestEnum.REST_END.getHour();
        if (reviewHour >= start - 1
                || reviewHour < end) {
            if (reviewHour >= start - 1) res += 24 - reviewHour + end;
            if (reviewHour < end) res += end - reviewHour;
        }
        return res * 60 * 60 * 1000L;
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
            // quesEntity.setLastView(new Date()); // 将来会被消息队列取代
            quesEntity.setHighLight(0);
            quesEntity.setMemoryLevel(1);
            quesEntity.setPassRate("--");
            answerService.save(answerEntity);
            quesEntity.setAnswerId(answerEntity.getAnswerId());
            this.save(quesEntity);
            calNextReview(quesEntity); // 设置提醒时间、提醒到期时间
            // todo:设置延迟高亮--消息队列
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

        return this.baseMapper.selectQuesWithin(Integer.parseInt(search));
    }

    @Override
    public List<QuesEntity> findQuesLike(Search search) {
        if (!StringUtils.isEmpty(search.getContent())) {
            search.setContent(search.getContent().trim());
        }
        return this.baseMapper.findQuesLike(search);
    }

    @Override
    @Transactional
    public boolean updateLevel(Map<String, Object> params) { // todo: 不要频繁更新QuesEntity，整合相关业务一次更新
        // todo:消息队列
        String status = (String) params.get("status");
        long quesId = Long.parseLong((String) params.get("quesId"));
        boolean flag = Boolean.parseBoolean((String) params.get("flag"));
        QuesEntity quesEntity = this.getById(quesId);
        QuesEntity updateEntity = new QuesEntity();
        updateEntity.setQuesId(quesId);
        // 更新记忆等级 如果已经9级了，不需要更新等级
        Integer memoryLevel = quesEntity.getMemoryLevel();
        if (memoryLevel < 9) {
            // 如果status=2则点击后降级，如果为1则点击后升级
            if ("2".equals(status)) { // 没超时
                updateEntity.setMemoryLevel(memoryLevel + 1);
            } else if ("3".equals(status)) {
                updateEntity.setMemoryLevel(memoryLevel == 1 ? memoryLevel : memoryLevel - 1);
            }
        }
        // 更新提醒时间
        calNextReview(updateEntity);
        // 更新LevelRecord & 计算通过率
        String master = flag ? "v" : "x";
        levelRecordService.sou(quesId, master);
        return true;
    }

    @Override
    public List<QuesEntity> remindQues() {

        return quesDao.remindQues();

    }

    @Override
    public boolean lastedReviewOn() {

        return quesDao.lastedReviewOn();
    }

    /**
     * 计算随机时间的系数，对应等级的开始提醒时间的1/4
     *
     * @param memoryLevel
     * @return
     */
    public int function(int memoryLevel) {
        return (int) (0.0018973214558984364 * Math.pow(memoryLevel, 8)
                - 0.06999008042309449 * Math.pow(memoryLevel, 7)
                + 1.0796875170926572 * Math.pow(memoryLevel, 6)
                - 9.088889038603504 * Math.pow(memoryLevel, 5)
                + 45.582552856892136 * Math.pow(memoryLevel, 4)
                - 138.71076628757302 * Math.pow(memoryLevel, 3)
                + 248.33586741732861 * Math.pow(memoryLevel, 2)
                - 235.13036122998398 * memoryLevel
                + 88.50000152381455);
    }


}