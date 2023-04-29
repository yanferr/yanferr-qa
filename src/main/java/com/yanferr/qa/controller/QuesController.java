package com.yanferr.qa.controller;

import java.util.*;

import com.yanferr.common.utils.PageUtils;
import com.yanferr.common.utils.R;
import com.yanferr.qa.service.LabelService;
import com.yanferr.qa.to.QuesLabelTo;
import com.yanferr.qa.vo.QuesAnswerVo;
import com.yanferr.qa.vo.Search;
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
     * 通过switch开关控制是否加入记忆
     *
     * @param active
     * @return
     */
    @GetMapping("/joinMemory")
    public R joinMemory(@PathParam("active") boolean active, @PathParam("quesId") Long quesId) {

        boolean result = quesService.joinMemory(active, quesId);

        return result ? R.ok() : R.error("更新失败");
    }

    /**
     * 模糊查询
     *
     * @param search
     * @return
     */
    @PostMapping("/search")
    public R search(@RequestBody Search search) {

        if (search.getContent() == null &&
                search.getRecentDay() == null &&
                search.getLabelName() == null) {
            return R.ok().put("data", quesService.list());
        }
        List<QuesEntity> quesList = quesService.findQuesLike(search);
        return R.ok().put("data", quesList);
    }




    /**
     * 双击查看问题时，计算记忆等级，更新lastView
     */
    @GetMapping("/updateHighLight/{quesId}")
    public R updateHighLight(@PathVariable("quesId") Long quesId) {
        quesService.updateHighLight(quesId);
        return R.ok();
    }


    /**
     * 查询最近一次提交的问题和答案
     *
     * @return
     */
    @GetMapping("/lasted")
    public R queryLastedQuesAndAnswer() {

        QuesAnswerVo data = quesService.queryLastedQuesAndAnswer();

        return R.ok().put("data", data);
    }

    /**
     * 查询上一次或下一次提交的问题和答案
     *
     * @return
     */
    @GetMapping("/backOrFront")
    public R queryBackQuesAndAnswer(@PathParam("pageIndex") int pageIndex) {

        QuesAnswerVo data = quesService.backOrFront(pageIndex);

        return R.ok().put("data", data);
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
     * 选择置顶--设置highLight=4
     */
    @RequestMapping("/top")
    public R top(@RequestBody Long[] quesIds) {
        boolean result = quesService.top(Arrays.asList(quesIds));
        return result ? R.ok() : R.error("置顶失败");
    }
    /**
     * 取消置顶--设置highLight=0
     */
    @RequestMapping("/topCancel")
    public R topCancel(@RequestBody Long[] quesIds) {
        boolean result = quesService.topCancel(Arrays.asList(quesIds));
        return result ? R.ok() : R.error("请求失败");
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
    public R listByLabelId(@PathVariable Long labelId) {
        List<QuesLabelTo> data = quesService.listByLabelId(labelId);
        return R.ok().put("data", data);
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

    /**
     * 取消高亮,就是设置highLight=-1
     */
    @RequestMapping("/cancelHL")
    public R cancelHL(@RequestBody Long[] quesIds) {
        boolean result = quesService.cancelHL(Arrays.asList(quesIds));
        return result ? R.ok() : R.error("取消高亮失败");
    }

}
