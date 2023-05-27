package com.yanferr.qa.dao;

import com.yanferr.qa.entity.QuesLabelRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yanferr.qa.vo.QuesLabelRelationVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2023-04-06 18:02:57
 */
@Mapper
public interface QuesLabelRelationDao extends BaseMapper<QuesLabelRelationEntity> {

    List<QuesLabelRelationVo> findLabelQuesNums();
}
