package com.yanferr.qa.controller;

import com.yanferr.qa.service.LevelRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("qa/levelRecord")
public class LevelRecordController {

    @Autowired
    LevelRecordService levelRecordService;


}
