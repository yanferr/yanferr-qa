package com.yanferr.qa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanferr.qa.dao.RemindDao;
import com.yanferr.qa.entity.RemindEntity;
import com.yanferr.qa.service.RemindService;

import javax.annotation.Resource;

public class RemindServiceImpl  extends ServiceImpl<RemindDao, RemindEntity> implements RemindService {

    @Resource
    RemindDao remindDao;


}
