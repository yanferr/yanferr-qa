package com.yanferr.qa.controller;

import com.yanferr.common.utils.PageUtils;
import com.yanferr.common.utils.R;
import com.yanferr.qa.entity.QuesEntity;
import com.yanferr.qa.service.QuesService;
import com.yanferr.qa.service.RemindService;
import com.yanferr.qa.to.QuesLabelTo;
import com.yanferr.qa.vo.QuesAnswerVo;
import com.yanferr.qa.vo.Search;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.*;


/**
 * @author ${author}
 * @email ${email}
 * @date 2023-04-06 18:02:57
 */
@RestController
@RequestMapping("qa/ques")
public class QuesController {
    @Resource
    private QuesService quesService;

    @Resource
    private RemindService remindService;


    /**
     * 通过switch开关控制是否加入记忆
     *
     * @param active
     * @return
     */
    @GetMapping("/joinMemory")
    public R joinMemory(@PathParam("active") boolean active, @PathParam("quesId") Long quesId) {

        // boolean result = quesService.joinMemory(active, quesId);

        return R.ok();
    }

    /**
     * 查找所有正在提醒的问题
     * @return
     */
    @GetMapping("/remindQues")
    public R remindQues(){
        List<QuesEntity> data = quesService.remindQues();
        return R.ok().put("data",data);
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
     * 点击通过率下的√或×触发更新memoryLevel和 LevelRecord
     * status=2则memoryLeve+1 ;status=3则memoryLeve-1；重新计算提醒时间
     * quesId
     * flag=true 则master后面加v；=false加x；重新计算通过率
     */
    @GetMapping("/updateLevel")
    public R updateLevel(@RequestParam Map<String,Object> params) {
        quesService.updateLevel(params);
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
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = quesService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @GetMapping("/info/{quesId}")
    public R info(@PathVariable("quesId") Long quesId) {
        QuesEntity ques = quesService.getById(quesId);

        return R.ok().put("ques", ques);
    }

    /**
     * 通过labelId获取所有labelId及labelId子下的ques，以及所有问题对应的标签名字
     */
    @GetMapping("/list/{labelId}")
    public R listByLabelId(@PathVariable Long labelId) {
        List<QuesLabelTo> data = quesService.listByLabelId(labelId);
        return R.ok().put("data", data);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
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
    @PostMapping("/update")
    public R update(@RequestBody QuesEntity ques) {
        ques.setUpdateTime(new Date());
        quesService.updateById(ques);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] quesIds) {
        // 查询answerIds 然后删除
        quesService.removeQAByIds(Arrays.asList(quesIds));
        return R.ok();
    }



}
