package com.yanferr.qa.controller;

import java.util.*;

import com.yanferr.common.utils.PageUtils;
import com.yanferr.common.utils.R;
import com.yanferr.qa.to.QuesLabelTo;
import com.yanferr.qa.vo.QuesAnswerVo;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.EAN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.yanferr.qa.entity.QuesEntity;
import com.yanferr.qa.service.QuesService;

import javax.validation.Valid;
import javax.websocket.server.PathParam;


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

    /**
     *
     * 模糊查询
     * @param search
     * @return
     */
    @GetMapping("/search")
    public R search(@PathParam("search") String search){
        if(StringUtils.isEmpty(search)){
           return R.ok().put("data",quesService.list());
        }
        search = search.trim();
        List<QuesEntity> quesList = null;
        // 如果是整数a，返回a天内的问题；
        if(StringUtils.isNumeric(search) && Integer.parseInt(search) < 370){
            quesList = quesService.findQuesWithin(search);
            return R.ok().put("data",quesList);
        }

        // 如果是.开头的，则代表日期,返回该日期对应的问题
        // if(search.startsWith(".")){
        //     search = search.substring(1);
        //     String[] dates = search.split("\\s+");
        //
        // }
        // 其他则为字符串，则模糊查询
        quesList = quesService.findQuesLike(search);
        return R.ok().put("data",quesList);

    }

    /**
     * 更新lastView
     */
    @GetMapping("/updateHighLight/{quesId}")
    public R updateHighLight(@PathVariable("quesId") Long quesId){
        quesService.updateHighLight(quesId);
        return R.ok();
    }


    /**
     * 查询最近一次提交的问题和答案
     * @return
     */
    @GetMapping("/lasted")
    public R queryLastedQuesAndAnswer(){

        QuesAnswerVo data = quesService.queryLastedQuesAndAnswer();

        return R.ok().put("data",data);
    }
    /**
     * 查询上一次或下一次提交的问题和答案
     * @return
     */
    @GetMapping("/backOrFront")
    public R queryBackQuesAndAnswer(@PathParam("pageIndex") int pageIndex){

        QuesAnswerVo data = quesService.backOrFront(pageIndex);

        return R.ok().put("data",data);
    }

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
     * 通过labelId获取所有labelId及labelId子下的ques，以及所有问题对应的标签名字
     */
    @RequestMapping("/list/{labelId}")
    public R listByLabelId(@PathVariable Long labelId){
        List<QuesLabelTo> data = quesService.listByLabelId(labelId);
        return R.ok().put("data",data);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@Valid @RequestBody QuesAnswerVo quesVo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            HashMap<String, String> map = new HashMap<>();
            bindingResult.getFieldErrors().forEach((item) -> {
                String name = item.getField(); // 得到属性名
                String msg = item.getDefaultMessage();
                map.put(name, msg);
            });
            return R.error(400, "表单提交信息不合法").put("data", map);
        } else {
            quesService.saveOrUpdateQuestion(quesVo);
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody QuesEntity ques) {
        ques.setUpdateTime(new Date());
        quesService.updateById(ques);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] quesIds) {
        // 查询answerIds 然后删除

        quesService.removeQAByIds(Arrays.asList(quesIds));

        return R.ok();
    }

}
