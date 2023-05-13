package com.yanferr.qa.controller;

import com.yanferr.common.utils.R;
import com.yanferr.qa.service.LevelRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Arrays;

@RestController
@RequestMapping("qa/levelRecord")
@Tag(name = "统计问题升到Lv9的时间过程")
public class LevelRecordController {

    @Autowired
    LevelRecordService levelRecordService;

    @Operation(summary = "新增或保存",description = "")
    @GetMapping("/sou/{quesId}")
    public R saveOrUpdate(@PathVariable("quesId") Long quesId , @PathParam("master") String master){
        boolean result  = levelRecordService.sou(quesId,master);
        return result ? R.ok():R.error();
    }
    @Operation(summary = "批量新增或保存",description = "批量查看的时候调用")
    @PostMapping("/batchSou")
    public R updateHighLightBatch(@RequestBody Long[] quesIds) {
        levelRecordService.batchSou(Arrays.asList(quesIds));
        return R.ok();
    }


}
