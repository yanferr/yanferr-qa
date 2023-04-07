package com.yanferr.qa.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2023-04-06 18:02:57
 */
@Data
@TableName("tb_ques")
public class QuesEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long quesId;
	/**
	 * 
	 */
	private String scene;
	/**
	 * 
	 */
	private Long answerId;
	/**
	 * 
	 */
	private String source;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private Date updateTime;

	private String ques;

}
