package com.huifu.odin.test.base;

import com.huifu.orion.config.ConfigConstants;
import com.huifu.pyxis.client.PyxisPlaceholderConfigurer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(value = {"com.huifu.odin.biz",
        "com.huifu.odin.application",
        "com.huifu.odin.dal"})
@MapperScan("com.huifu.odin.dal")
@Configuration
public class ServiceTestApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(com.huifu.odin.test.base.ServiceTestApplication.class);
    }

    @Bean(value = "propertyPlaceholderConfigurer")
    public PyxisPlaceholderConfigurer pyxisPlaceholderConfigurer() {
        PyxisPlaceholderConfigurer pyxisPlaceholderConfigurer = new PyxisPlaceholderConfigurer();
        pyxisPlaceholderConfigurer.setLocations("classpath:dal/dal_application.properties," +
                "classpath:application/cfca_application.properties");
        pyxisPlaceholderConfigurer.setEnvironment("test");
        pyxisPlaceholderConfigurer.setRunMode(ConfigConstants.ONLINE_MODE);
        pyxisPlaceholderConfigurer.setAddress("192.168.3.33:8182,192.168.3.34:8182,192.168.3.35:8182");
        return pyxisPlaceholderConfigurer;
    }


}
