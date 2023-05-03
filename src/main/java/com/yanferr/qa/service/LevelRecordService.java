package com.yanferr.qa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yanferr.qa.entity.LevelRecordEntity;

import java.util.List;

public interface LevelRecordService extends IService<LevelRecordEntity> {
    boolean sou(Long quesId, String master);

    void batchSou(List<Long> asList);

}
