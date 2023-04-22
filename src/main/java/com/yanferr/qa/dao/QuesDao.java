package com.yanferr.qa.dao;

import com.yanferr.qa.entity.QuesEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yanferr.qa.entity.QuesLabelRelationEntity;
import com.yanferr.qa.vo.QuesAnswerVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2023-04-06 18:02:57
 */
@Mapper
public interface QuesDao extends BaseMapper<QuesEntity> {

    List<QuesLabelRelationEntity> selectDistinctQuesByQuesId();


    QuesAnswerVo queryBackOrFront(@Param("pageIndex") int pageIndex);

    List<QuesEntity> selectQuesWithin( int day);
}
