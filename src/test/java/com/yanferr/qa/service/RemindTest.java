package com.yanferr.qa.service;

import com.yanferr.qa.entity.QuesEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RemindTest {

    @Autowired
    private QuesService quesService;

    @Autowired
    private LevelRecordService levelRecordService;

    @Test
    public void random() {
        // 0~3 0~60
        for (int i = 0; i < 100; i++) {
            System.out.println((Math.random() * 6));
        }
    }

    @Test
    public void remindTest() {
        for (QuesEntity quesEntity : quesService.list()) {
            quesService.calNextReview(quesEntity);
        }

    }

    @Test
    public void levelRecord() {
        for (QuesEntity quesEntity : quesService.list()) {
            levelRecordService.sou(quesEntity.getQuesId(), "v");
        }
    }


}
