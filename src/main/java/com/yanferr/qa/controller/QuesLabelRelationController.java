package com.yanferr.qa.controller;

import java.util.Arrays;
import java.util.Map;

import com.yanferr.common.utils.PageUtils;
import com.yanferr.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yanferr.qa.entity.QuesLabelRelationEntity;
import com.yanferr.qa.service.QuesLabelRelationService;



/**
 * 
 *
 * @author ${author}
 * @email ${email}
 * @date 2023-04-06 18:02:57
 */
@RestController
@RequestMapping("qa/queslabelrelation")
public class QuesLabelRelationController {
    @Autowired
    private QuesLabelRelationService quesLabelRelationService;

    /**
     * 根据quesId更新其所有labelIds关系
     * @param quesId
     * @param labelIds
     * @return
     */
    @RequestMapping("/update/{quesId}")
    public R update(@PathVariable Long quesId, @RequestBody Long[] labelIds){
        quesLabelRelationService.updateByQuesId(quesId,labelIds);
        return R.ok();
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
        public R list(@RequestParam Map<String, Object> params){
        PageUtils page = quesLabelRelationService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{labelId}")
        public R info(@PathVariable("labelId") Long labelId){
		QuesLabelRelationEntity quesLabelRelation = quesLabelRelationService.getById(labelId);

        return R.ok().put("quesLabelRelation", quesLabelRelation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
        public R save(@RequestBody QuesLabelRelationEntity quesLabelRelation){
		quesLabelRelationService.save(quesLabelRelation);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
        public R update(@RequestBody QuesLabelRelationEntity quesLabelRelation){
		quesLabelRelationService.updateById(quesLabelRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
        public R delete(@RequestBody Long[] labelIds){
		quesLabelRelationService.removeByIds(Arrays.asList(labelIds));

        return R.ok();
    }

}
