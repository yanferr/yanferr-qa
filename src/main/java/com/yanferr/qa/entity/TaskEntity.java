package com.yanferr.qa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2023-04-06 18:02:57
 */
@Data
@TableName("tb_task")
public class TaskEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	@TableId(type = IdType.AUTO)
	private Long taskId;
	private String task;
	private Timestamp taskDate;

}
