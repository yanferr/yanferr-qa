package com.yanferr.qa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yanferr.common.utils.PageUtils;
import com.yanferr.qa.entity.QuesEntity;
import com.yanferr.qa.to.QuesLabelTo;
import com.yanferr.qa.vo.QuesAnswerVo;
import com.yanferr.qa.vo.Search;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author ${author}
 * @email ${email}
 * @date 2023-04-06 18:02:57
 */
public interface QuesService extends IService<QuesEntity> {

    PageUtils queryPage(Map<String, Object> params);


    void saveOrUpdateQuestion(QuesAnswerVo quesVo);

    void removeQAByIds(List<Long> quesIds);

    /**
     * 通过labelId获取所有ques
     */
    List<QuesLabelTo> listByLabelId(Long labelId);

    QuesAnswerVo queryLastedQuesAndAnswer();

    QuesAnswerVo backOrFront(int params);

    List<QuesEntity> findQuesWithin(String search);

    List<QuesEntity> findQuesLike(Search search);

    boolean calNextReview(QuesEntity quesEntity);
    // void updateHighLight(Long quesId);
    // boolean cancelHL(List<Long> asList);
    // boolean joinMemory(boolean active, Long quesId);
    // boolean top(List<Long> quesIds);
    // boolean topCancel(List<Long> asList);
    // void updateHighLightBatch(List<Long> asList);

    boolean updateLevel(Map<String, Object> params);

    List<QuesEntity> remindQues();

}

