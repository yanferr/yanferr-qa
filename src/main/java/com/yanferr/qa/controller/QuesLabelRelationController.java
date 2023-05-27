package com.yanferr.qa.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.yanferr.common.utils.PageUtils;
import com.yanferr.common.utils.R;
import com.yanferr.qa.vo.QuesLabelRelationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/update/{quesId}")
    public R update(@PathVariable Long quesId, @RequestBody Long[] labelIds){
        quesLabelRelationService.updateByQuesId(quesId,labelIds);
        return R.ok();
    }

    /**
     * 列表
     */
    @GetMapping("/list")
        public R list(@RequestParam Map<String, Object> params){
        PageUtils page = quesLabelRelationService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 标签-问题数量
     */
    @GetMapping("/labelQuesNums")
    public R list(){
        List<QuesLabelRelationVo> data = quesLabelRelationService.labelQuesNums();

        return R.ok().put("data", data);
    }

    /**
     * 信息
     */
    @GetMapping("/info/{labelId}")
        public R info(@PathVariable("labelId") Long labelId){
		QuesLabelRelationEntity quesLabelRelation = quesLabelRelationService.getById(labelId);

        return R.ok().put("quesLabelRelation", quesLabelRelation);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
        public R save(@RequestBody QuesLabelRelationEntity quesLabelRelation){
		quesLabelRelationService.save(quesLabelRelation);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
        public R update(@RequestBody QuesLabelRelationEntity quesLabelRelation){
		quesLabelRelationService.updateById(quesLabelRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
        public R delete(@RequestBody Long[] labelIds){
		quesLabelRelationService.removeByIds(Arrays.asList(labelIds));

        return R.ok();
    }

}
