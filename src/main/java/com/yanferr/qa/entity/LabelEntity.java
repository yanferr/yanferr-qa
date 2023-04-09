package com.yanferr.qa.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2023-04-06 18:02:57
 */
@Data
@TableName("tb_label")
public class LabelEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long labelId;
	/**
	 * 
	 */
	private String labelName;

	/**
	 * 是否显示[0-不显示，1显示]
	 */
	@TableLogic(value="1",delval="0")
	private Integer showStatus;

	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 父分类id
	 */
	private Long parentId;
	/**
	 * 层级
	 */
	private Integer labelLevel;

	private Integer quesNum;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)  // 不为空才返回给客户端
	@TableField(exist = false)
	private List<LabelEntity> children;



}
