package com.yanferr.qa.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yanferr.qa.entity.LabelEntity;
import com.yanferr.qa.entity.LevelRecordEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LevelRecordDao extends BaseMapper<LevelRecordEntity> {
}
