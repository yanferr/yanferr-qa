package com.yanferr.qa.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yanferr.qa.entity.TaskEntity;
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
public interface TaskDao extends BaseMapper<TaskEntity> {
	public List<TaskEntity> getRecentTasks();
}
