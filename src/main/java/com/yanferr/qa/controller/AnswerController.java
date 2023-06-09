package com.yanferr.qa.controller;

import com.yanferr.common.utils.PageUtils;
import com.yanferr.common.utils.R;
import com.yanferr.qa.entity.AnswerEntity;
import com.yanferr.qa.service.AnswerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 
 *
 * @author ${author}
 * @email ${email}
 * @date 2023-04-06 18:02:57
 */
@Tag(name = "AnswerController", description = "维护问题的答案")
@RestController
@RequestMapping("qa/answer")
public class AnswerController {
    @Autowired
    private AnswerService answerService;

    /**
     * 列表
     */
    @Operation(summary = "获取所有答案列表",description = "")
    @GetMapping("/list")
        public R list(@RequestParam Map<String, Object> params){
        PageUtils page = answerService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @Operation(summary = "根据id查",description = "")
    @GetMapping("/info/{answerId}")
        public R info(@PathVariable("answerId") Long answerId){
		AnswerEntity answer = answerService.getById(answerId);

        return R.ok().put("answer", answer);
    }


    /**
     * 保存
     */
    @Operation(summary = "保存答案",description = "")
    @PostMapping("/save")
        public R save(@RequestBody AnswerEntity answer){
		answerService.save(answer);

        return R.ok();
    }

    /**
     * 修改
     */
    @Operation(summary = "修改答案",description = "")
    @PostMapping("/update")
        public R update(@RequestBody AnswerEntity answer){
		answerService.updateById(answer);

        return R.ok();
    }

    /**
     * 删除
     */
    @Operation(summary = "删除/批量删除答案",description = "")
    @PostMapping("/delete")
        public R delete(@RequestBody Long[] answerIds){
		answerService.removeByIds(Arrays.asList(answerIds));

        return R.ok();
    }

}
