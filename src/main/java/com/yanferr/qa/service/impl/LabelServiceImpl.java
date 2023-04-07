package com.yanferr.qa.service.impl;

import com.yanferr.common.utils.PageUtils;
import com.yanferr.common.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.yanferr.qa.dao.LabelDao;
import com.yanferr.qa.entity.LabelEntity;
import com.yanferr.qa.service.LabelService;
import org.springframework.util.StringUtils;


@Service("labelService")
public class LabelServiceImpl extends ServiceImpl<LabelDao, LabelEntity> implements LabelService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        // IPage<LabelEntity> page = this.page(
        //         new Query<LabelEntity>().getPage(params),
        //         new QueryWrapper<LabelEntity>()
        // );
        //
        // return new PageUtils(page);
        String key = (String) params.get("key");
        QueryWrapper<LabelEntity> queryWrapper = new QueryWrapper<>();

        // select * from pms_brand where brand_id = key or name like %key%
        if(!StringUtils.isEmpty(key)){
            queryWrapper.eq("label_id",key).or().like("label_name",key);
        }
        IPage<LabelEntity> page = this.page(
                new Query<LabelEntity>().getPage(params),
                queryWrapper
        );
        return new PageUtils(page);
    }

}