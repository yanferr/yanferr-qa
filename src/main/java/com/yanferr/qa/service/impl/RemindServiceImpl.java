package com.yanferr.qa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanferr.common.constant.QAConstant;
import com.yanferr.qa.dao.RemindDao;
import com.yanferr.qa.entity.RemindEntity;
import com.yanferr.qa.service.RemindService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service("remindService")
public class RemindServiceImpl extends ServiceImpl<RemindDao, RemindEntity> implements RemindService {

    @Resource
    RemindDao remindDao;



}
