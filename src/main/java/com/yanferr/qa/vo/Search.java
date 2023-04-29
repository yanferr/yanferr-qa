package com.yanferr.qa.vo;

import lombok.Data;

/**
 * 前端可以搜索的内容
 */
@Data
public class Search {
    private String labelName;
    private Integer recentDay; // 最近recentDay创建的问题
    private String content;
}
