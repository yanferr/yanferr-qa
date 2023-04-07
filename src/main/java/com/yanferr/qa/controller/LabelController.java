package com.yanferr.qa.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.yanferr.common.utils.PageUtils;
import com.yanferr.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yanferr.qa.entity.LabelEntity;
import com.yanferr.qa.service.LabelService;


/**
 * @author ${author}
 * @email ${email}
 * @date 2023-04-06 18:02:57
 */
@RestController
@RequestMapping("qa/label")
public class LabelController {
    @Autowired
    private LabelService labelService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {

        PageUtils page = labelService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{labelId}")
    public R info(@PathVariable("labelId") Long labelId) {
        LabelEntity label = labelService.getById(labelId);

        return R.ok().put("label", label);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody LabelEntity label) {
        labelService.save(label);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody LabelEntity label) {
        labelService.updateById(label);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] labelIds) {
        labelService.removeByIds(Arrays.asList(labelIds));

        return R.ok();
    }

}
