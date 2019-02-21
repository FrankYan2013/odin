package com.huifu.odin.dal.page;

import com.github.pagehelper.PageInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class MasterPageHelper {

    @Bean(value = "masterPageInterceptor")
    public PageInterceptor pageHelper() {
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties props = new Properties();
        props.setProperty("helperDialect", "oracle");
        pageInterceptor.setProperties(props);
        return pageInterceptor;
    }
}
