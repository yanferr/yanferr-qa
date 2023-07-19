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

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
    public TaskEntity recentTasks() {

        List<TaskEntity> recentTasks = taskDao.getRecentTasks();

        // taskId为今天日期的taskId
        // task为最近7天的task拼接
        TaskEntity res = new TaskEntity();
        if (recentTasks.size() == 0) {
            return null;
        }

        StringBuilder allTaskStr = new StringBuilder();
        String nowDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM-dd"));

        for (TaskEntity recentTask : recentTasks) {
            String task = recentTask.getTask();

            String date = recentTask.getTaskDate().toLocalDateTime().format(DateTimeFormatter.ofPattern("MM-dd"));
            // 和当前时间相等，不需要加>>>>date
            if (nowDate.equals(date)) {
                allTaskStr.append(task)
                        .append("\r\n");
                res.setTaskId(recentTask.getTaskId());
            } else {
                allTaskStr.append("\r\n").
                        append(">>>>>>>")
                        .append(date)
                        .append("\r\n")
                        .append(task);
            }
            res.setTask(allTaskStr.toString());
        }

        return res;
    }


    @Override
    public Long saveEntity(TaskEntity entity) {

        String task = entity.getTask();
        if (task.contains(">>>>>>>")) {
            int idx = task.indexOf(">>>>>>>");
            task = task.substring(0, idx);
        }
        entity.setTask(task.trim());

        // 执行新增
        if(entity.getTaskId() == null){
            entity.setTaskDate(new Timestamp(System.currentTimeMillis()));
        }

        this.saveOrUpdate(entity);

        return entity.getTaskId();

    }

}