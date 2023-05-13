package com.yanferr.qa.controller;

import com.yanferr.qa.service.RemindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("qa/remind")
public class RemindController {
    @Autowired
    private RemindService remindService;



}
