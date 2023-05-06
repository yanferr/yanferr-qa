package com.yanferr.qa.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yanferr.qa.entity.QuesLabelRelationEntity;
import com.yanferr.qa.entity.RemindEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RemindDao extends BaseMapper<RemindEntity> {
}
