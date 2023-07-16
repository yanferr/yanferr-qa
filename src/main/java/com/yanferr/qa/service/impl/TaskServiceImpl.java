package com.yanferr.qa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanferr.common.utils.PageUtils;
import com.yanferr.common.utils.Query;
import com.yanferr.qa.dao.TaskDao;
import com.yanferr.qa.entity.TaskEntity;
import com.yanferr.qa.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;


@Service("taskService")
public class TaskServiceImpl extends ServiceImpl<TaskDao, TaskEntity> implements TaskService {

    @Autowired
    private TaskDao taskDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TaskEntity> page = this.page(
                new Query<TaskEntity>().getPage(params),
                new QueryWrapper<TaskEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<TaskEntity> recentTasks() {

        List<TaskEntity> recentTasks = taskDao.getRecentTasks();
        for (TaskEntity recentTask : recentTasks) {
            String task = recentTask.getTask();
            StringBuilder stringBuilder = new StringBuilder();
            String date = recentTask.getCreateTime().toLocalDateTime().format(DateTimeFormatter.ofPattern("MM-dd"));
            stringBuilder.append(">>>>>>>")
                    .append(date)
                    .append("\r\n")
                    .append(task);
            recentTask.setTask(stringBuilder.toString());
        }

        return recentTasks;
    }

    @Override
    public void saveEntity(TaskEntity entity) {
        // entity.setCreateTime(new );
    }

}