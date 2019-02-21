package com.huifu.odin.application.pyxis;

import com.huifu.pyxis.client.PyxisConfigurationPropertiesBinding;
import com.huifu.pyxis.client.PyxisPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author frank.yan
 * @date 2018/3/15
 */
@Configuration
public class PyxisPlaceholderConfig {

    @Bean
    public static PyxisPlaceholderConfigurer pyxisPlaceholderConfigurer() {
        PyxisPlaceholderConfigurer pyxisPlaceholderConfigurer = new PyxisPlaceholderConfigurer();
        return pyxisPlaceholderConfigurer;
    }

    @Bean
    public static PyxisConfigurationPropertiesBinding pyxisConfigurationPropertiesBinding() {
        return new PyxisConfigurationPropertiesBinding();
    }
}
