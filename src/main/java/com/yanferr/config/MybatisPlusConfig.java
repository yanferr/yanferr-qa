package com.yanferr.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.yanferr.qa.dao")
public class MybatisPlusConfig {  // 分页插件

    /**
     * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题(该属性会在旧插件移除后一同移除)
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作，true返回到首页，false继续请求 默认false
        paginationInterceptor.setOverflow(true);
        // 单页限制数量，-1不受限制
        paginationInterceptor.setLimit(1000);
        return paginationInterceptor;
    }
}