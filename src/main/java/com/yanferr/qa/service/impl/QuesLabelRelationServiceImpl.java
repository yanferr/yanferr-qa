package com.yanferr.qa.service.impl;

import com.yanferr.common.utils.PageUtils;
import com.yanferr.common.utils.Query;
import com.yanferr.qa.entity.QuesEntity;
import com.yanferr.qa.service.QuesService;
import com.yanferr.qa.vo.QuesLabelRelationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.yanferr.qa.dao.QuesLabelRelationDao;
import com.yanferr.qa.entity.QuesLabelRelationEntity;
import com.yanferr.qa.service.QuesLabelRelationService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service("quesLabelRelationService")
public class QuesLabelRelationServiceImpl extends ServiceImpl<QuesLabelRelationDao, QuesLabelRelationEntity> implements QuesLabelRelationService {

    @Resource
    QuesLabelRelationDao quesLabelRelationDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<QuesLabelRelationEntity> page = this.page(
                new Query<QuesLabelRelationEntity>().getPage(params),
                new QueryWrapper<QuesLabelRelationEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 根据quesId更改所属的label关系
     * @param quesId
     * @param labelIds
     */
    @Override
    @Transactional
    public void updateByQuesId(Long quesId, Long[] labelIds) {
        // 先删除
        this.remove(new QueryWrapper<QuesLabelRelationEntity>().eq("ques_id", quesId));
        // 再保存
        for (Long labelId : labelIds) {
            QuesLabelRelationEntity relationEntity = new QuesLabelRelationEntity();
            relationEntity.setLabelId(labelId);
            relationEntity.setQuesId(quesId);
            this.save(relationEntity);
        }
    }

    @Override
    public List<QuesLabelRelationVo> labelQuesNums() {


        return quesLabelRelationDao.findLabelQuesNums();

    }


}