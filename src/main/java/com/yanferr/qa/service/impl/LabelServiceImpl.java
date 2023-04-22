package com.yanferr.qa.service.impl;

import com.yanferr.common.utils.PageUtils;
import com.yanferr.common.utils.Query;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.yanferr.qa.dao.LabelDao;
import com.yanferr.qa.entity.LabelEntity;
import com.yanferr.qa.service.LabelService;
import org.springframework.util.StringUtils;

import javax.swing.text.html.parser.Entity;


@Service("labelService")
public class LabelServiceImpl extends ServiceImpl<LabelDao, LabelEntity> implements LabelService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<LabelEntity> page = this.page(
                new Query<LabelEntity>().getPage(params),
                new QueryWrapper<LabelEntity>()
        );

        return new PageUtils(page);
        // String key = (String) params.get("key");
        // QueryWrapper<LabelEntity> queryWrapper = new QueryWrapper<>();
        //
        // // select * from pms_brand where brand_id = key or name like %key%
        // if(!StringUtils.isEmpty(key)){
        //     queryWrapper.eq("label_id",key).or().like("label_name",key);
        // }
        // IPage<LabelEntity> page = this.page(
        //         new Query<LabelEntity>().getPage(params),
        //         queryWrapper
        // );
        // return new PageUtils(page);
    }

    /**
     * 通过父id获取所有子id
     * @param labelId
     * @return
     */
    public List<Long> getChildrenLabelIdsByLabelId(Long labelId) {  // todo:三级分类没有查出来+优化
        ArrayList<Long> ans = new ArrayList<>();
        List<LabelEntity> labelEntities = baseMapper.selectList(null);
        // labelId的子节点children
        List<LabelEntity> children = labelEntities.stream().filter(item -> item.getParentId() == labelId).collect(Collectors.toList());
        for(LabelEntity entity:children){
            ans.add(entity.getLabelId());
            // children的子节点
            List<LabelEntity> thirdChidren = labelEntities.stream().filter(item -> item.getParentId() == entity.getLabelId()).collect(Collectors.toList());
            if(thirdChidren.size()>0){
                for (LabelEntity child : thirdChidren) {
                    ans.add(child.getLabelId());
                }
            }
        }
        return ans;
    }
    /**
     * 返回树节点
     * @param parentId
     * @return
     */
    @Override
    public List<LabelEntity> listWithTree(Long parentId) {
        // 所有pid为0的list
        List<LabelEntity> labelEntities = baseMapper.selectList(null);
        return labelEntities.stream().filter(item->item.getParentId()==parentId)
                .map(parent->{
                    parent.setChildren(getChildren(parent,labelEntities));
                    return parent;
                })
                .sorted((p1,p2)->p1.getSort()-p2.getSort())
                .collect(Collectors.toList());
    }

    private List<LabelEntity> getChildren(LabelEntity parent, List<LabelEntity> labelEntities) {

        return  labelEntities.stream().filter(entity -> entity.getParentId() == parent.getLabelId())
                .map(entity -> {
                    entity.setChildren(getChildren(entity,labelEntities));
                    return entity;
                })
                .sorted(Comparator.comparingInt(LabelEntity::getSort))
                .collect(Collectors.toList());
    }

}