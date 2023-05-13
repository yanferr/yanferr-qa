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
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Service("levelRecordService")
public class LevelRecordServiceImpl extends ServiceImpl<LevelRecordDao, LevelRecordEntity> implements LevelRecordService {
    @Autowired
    LevelRecordDao levelRecordDao;

    @Autowired
    QuesDao quesDao;

    @Override
    @Transactional
    public boolean sou(Long quesId, String master) {
        QuesEntity quesEntity = quesDao.selectById(quesId);
        LevelRecordEntity levelRecordEntity = levelRecordDao.selectOne(new QueryWrapper<LevelRecordEntity>().eq("ques_id", quesId));
        QuesEntity updateQuesPassRate = new QuesEntity();
        updateQuesPassRate.setQuesId(quesEntity.getQuesId());
        if (levelRecordEntity == null) { // 执行保存
            levelRecordEntity = new LevelRecordEntity();
            levelRecordEntity.setChangeDate((new Date().getTime())+"");
            levelRecordEntity.setMaster(master);
            levelRecordEntity.setLevel(quesEntity.getMemoryLevel()+"");
            levelRecordEntity.setQuesId(quesId);
            updateQuesPassRate.setPassRate("0%");
        } else { // 更新
            levelRecordEntity.setChangeDate(levelRecordEntity.getChangeDate()+";"+(new Date().getTime()));
            levelRecordEntity.setMaster(levelRecordEntity.getMaster()+master);
            levelRecordEntity.setLevel(levelRecordEntity.getLevel()+"-"+quesEntity.getMemoryLevel());
            levelRecordEntity.setQuesId(quesId);
            updateQuesPassRate.setPassRate(calRate(levelRecordEntity.getMaster()));
        }
        return quesDao.updateById(updateQuesPassRate)>0
                && this.saveOrUpdate(levelRecordEntity);
    }

    private String calRate(String master) {
        double count= 0;
        for(int i=0;i<master.length();i++){
            if(master.charAt(i) == 'v'){
                count ++ ;
            }
        }
        return Math.round(count/master.length()*100) +"%";
    }

    @Override
    public void batchSou(List<Long> quesIds) {
        for (Long quesId : quesIds) {
            sou(quesId,"v");
        }
    }
}
