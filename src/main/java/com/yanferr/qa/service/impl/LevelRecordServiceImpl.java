package com.yanferr.qa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanferr.qa.dao.LevelRecordDao;
import com.yanferr.qa.dao.QuesDao;
import com.yanferr.qa.entity.LevelRecordEntity;
import com.yanferr.qa.entity.QuesEntity;
import com.yanferr.qa.service.LevelRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("levelRecordService")
public class LevelRecordServiceImpl extends ServiceImpl<LevelRecordDao, LevelRecordEntity> implements LevelRecordService {
    @Autowired
    LevelRecordDao levelRecordDao;

    @Autowired
    QuesDao quesDao;

    @Override
    public boolean sou(Long quesId, String master) {
        QuesEntity quesEntity = quesDao.selectById(quesId);
        LevelRecordEntity levelRecordEntity = levelRecordDao.selectOne(new QueryWrapper<LevelRecordEntity>().eq("ques_id", quesId));

        if (levelRecordEntity == null) { // 执行保存
            levelRecordEntity = new LevelRecordEntity();
            levelRecordEntity.setChangeDate(quesEntity.getLastView().getTime()+"");
            levelRecordEntity.setMaster(master);
            levelRecordEntity.setLevel(quesEntity.getMemoryLevel()+"");
            levelRecordEntity.setQuesId(quesId);
        } else { // 更新
            levelRecordEntity.setChangeDate(levelRecordEntity.getChangeDate()+";"+quesEntity.getLastView().getTime());
            levelRecordEntity.setMaster(levelRecordEntity.getMaster()+master);
            levelRecordEntity.setLevel(levelRecordEntity.getLevel()+"-"+quesEntity.getMemoryLevel());
            levelRecordEntity.setQuesId(quesId);
        }
        return this.saveOrUpdate(levelRecordEntity);
    }

    @Override
    public void batchSou(List<Long> quesIds) {
        for (Long quesId : quesIds) {
            sou(quesId,"v");
        }
    }
}
