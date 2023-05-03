package com.yanferr.qa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanferr.qa.dao.LevelRecordDao;
import com.yanferr.qa.entity.LevelRecordEntity;
import com.yanferr.qa.service.LevelRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("levelRecordService")
public class LevelRecordServiceImpl extends ServiceImpl<LevelRecordDao, LevelRecordEntity> implements LevelRecordService {
    @Autowired
    LevelRecordDao levelRecordDao;
}
