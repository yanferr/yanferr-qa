package com.yanferr.qa.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;

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
	private String sourceUrl;
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
	private Date lastView;
	private Integer memoryLevel;
	private Integer highLight;

	private Date reviewOn;
	private Date delayOn;



	// 通过率
	private String passRate;

	// 状态
	@TableField(exist = false)
	private Integer status;  //-1不需要显示状态图；0-未开始；1-大于一天后才提醒的问题；2-到提醒时间；3-超过提醒时间



}
