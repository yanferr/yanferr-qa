package com.yanferr.qa.controller;

import com.yanferr.common.utils.PageUtils;
import com.yanferr.common.utils.R;
import com.yanferr.qa.entity.TaskEntity;
import com.yanferr.qa.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;



/**
 * 
 *
 * @author ${author}
 * @email ${email}
 * @date 2023-04-06 18:02:57
 */
@Tag(name = "TaskController", description = "任务记录")
@RestController
@RequestMapping("qa/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    /**
     * 列表
     */
    @Operation(summary = "获取所有答案列表",description = "")
    @GetMapping("/list")
        public R list(@RequestParam Map<String, Object> params){
        PageUtils page = taskService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @Operation(summary = "根据id查",description = "")
    @GetMapping("/info/{answerId}")
        public R info(@PathVariable("answerId") Long answerId){
		TaskEntity answer = taskService.getById(answerId);

        return R.ok().put("answer", answer);
    }
    /**
     * 获取最近7天的任务列表
     */
    @Operation(summary = "获取最近7笔任务列表",description = "")
    @GetMapping("/recent")
    public R recentTasks(){
        List<TaskEntity> tasks = taskService.recentTasks();

        return R.ok().put("tasks", tasks);
    }



    /**
     * 保存
     */
    @Operation(summary = "保存答案",description = "")
    @PostMapping("/save")
        public R save(@RequestBody TaskEntity entity){
		taskService.saveEntity(entity);

        return R.ok();
    }

    /**
     * 修改
     */
    @Operation(summary = "修改答案",description = "")
    @PostMapping("/update")
        public R update(@RequestBody TaskEntity taskEntity){
		taskService.updateById(taskEntity);

        return R.ok();
    }

    /**
     * 删除
     */
    @Operation(summary = "删除/批量删除答案",description = "")
    @PostMapping("/delete")
        public R delete(@RequestBody Long[] answerIds){
		taskService.removeByIds(Arrays.asList(answerIds));

        return R.ok();
    }

}
