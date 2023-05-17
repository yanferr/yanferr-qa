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
        if (levelRecordEntity == null) { // 执行保存
            levelRecordEntity = new LevelRecordEntity();
            levelRecordEntity.setChangeDate((new Date().getTime()) + "");
            levelRecordEntity.setMaster(master);
            levelRecordEntity.setLevel(quesEntity.getMemoryLevel() + "");
            levelRecordEntity.setQuesId(quesId);
        } else { // 更新
            levelRecordEntity.setMaster(levelRecordEntity.getMaster() + master);
            // 最多记录13次
            if(levelRecordEntity.getMaster().length()<=13){
                levelRecordEntity.setChangeDate(levelRecordEntity.getChangeDate() + ";" + (new Date().getTime()));
            }
            // 最多记录到9级
            if(!levelRecordEntity.getLevel().endsWith("9")){
                levelRecordEntity.setLevel(levelRecordEntity.getLevel() + "-" + quesEntity.getMemoryLevel());
            }
            levelRecordEntity.setQuesId(quesId);
        }
        // 设置通过率
        QuesEntity updateQuesPassRate = new QuesEntity();
        updateQuesPassRate.setQuesId(quesEntity.getQuesId());
        updateQuesPassRate.setLastView(new Date());  // 更新最近一次查看时间
        updateQuesPassRate.setPassRate(calRate(levelRecordEntity.getMaster()));
        return quesDao.updateById(updateQuesPassRate) > 0
                && this.saveOrUpdate(levelRecordEntity);
    }

    private String calRate(String master) {
        double count = 0;
        for (int i = 0; i < master.length(); i++) {
            if (master.charAt(i) == 'v') {
                count++;
            }
        }
        String res = String.valueOf(count / master.length() * 100);
        res = res.substring(0, res.indexOf('.') + 2);
        // 0.0%,100.0%
        if("0.0".equals(res) || "100.0".equals(res)){
            return res.substring(0,res.length()-2)+"%";
        }
        // 取小数点后1位
        return res +"%";
    }

    @Override
    public void batchSou(List<Long> quesIds) {
        for (Long quesId : quesIds) {
            sou(quesId, "v");
        }
    }
}
