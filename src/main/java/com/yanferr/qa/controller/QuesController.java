package com.yanferr.qa.controller;

import java.util.Arrays;
import java.util.Map;

import com.yanferr.common.utils.PageUtils;
import com.yanferr.common.utils.R;
import com.yanferr.qa.entity.AnswerEntity;
import com.yanferr.qa.service.AnswerService;
import com.yanferr.qa.service.LabelService;
import com.yanferr.qa.vo.QuesAnswerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yanferr.qa.entity.QuesEntity;
import com.yanferr.qa.service.QuesService;


/**
 * @author ${author}
 * @email ${email}
 * @date 2023-04-06 18:02:57
 */
@RestController
@RequestMapping("qa/ques")
public class QuesController {
    @Autowired
    private QuesService quesService;


    @Autowired
    private LabelService labelService;

    @Autowired
    private AnswerService answerService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = quesService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{quesId}")
    public R info(@PathVariable("quesId") Long quesId) {
        QuesEntity ques = quesService.getById(quesId);

        return R.ok().put("ques", ques);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody QuesAnswerVo quesVo) {
        AnswerEntity answerEntity = new AnswerEntity();
        answerEntity.setAnswer(quesVo.getAnswer());
        answerService.save(answerEntity);
        System.out.println(answerEntity.getAnswerId());

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody QuesEntity ques) {
        quesService.updateById(ques);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] quesIds) {
        quesService.removeByIds(Arrays.asList(quesIds));

        return R.ok();
    }

}
