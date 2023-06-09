package com.yanferr.qa.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yanferr.qa.entity.QuesLabelRelationEntity;
import com.yanferr.qa.entity.RemindEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RemindDao extends BaseMapper<RemindEntity> {
    List<RemindEntity> selectByQuesIds(List<Long> quesIds);
}
