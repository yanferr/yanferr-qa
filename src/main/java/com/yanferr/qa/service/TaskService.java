package com.yanferr.qa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yanferr.common.utils.PageUtils;
import com.yanferr.qa.entity.TaskEntity;

import java.util.Map;

/**
 * 
 *
 * @author ${author}
 * @email ${email}
 * @date 2023-04-06 18:02:57
 */
public interface TaskService extends IService<TaskEntity> {

    PageUtils queryPage(Map<String, Object> params);

    TaskEntity recentTasks();

    Long saveEntity(TaskEntity entity);
}

