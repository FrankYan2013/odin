package com.huifu.odin.integration.pegasus;

import com.huifu.pegasus.client.rpc.proxy.ProxySpringBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * pegasus client配置，示例代码，可直接删除
 *
 * @author jianfei.chen
 * @date 2016/12/13
 */
@Configuration
public class PegasusClientConfig {


    @Bean(name = "custService")
    public ProxySpringBeanFactory custService() {
        ProxySpringBeanFactory proxySpringBeanFactory = new ProxySpringBeanFactory();
        proxySpringBeanFactory.setServiceName("http://service.huifu.com/custmag/CustServicePegasus_1.0");
        proxySpringBeanFactory.setServiceInterface("com.huifu.custmag.facade.service.CustService");
        proxySpringBeanFactory.setTimeout(5000);
        proxySpringBeanFactory.setSubscribe(true);
        proxySpringBeanFactory.setCallMethod("sync");
        return proxySpringBeanFactory;
    }

    @Bean(name = "notifyService")
    public ProxySpringBeanFactory notifyService() {
        ProxySpringBeanFactory proxySpringBeanFactory = new ProxySpringBeanFactory();
        proxySpringBeanFactory.setServiceName("http://service.huifu.com/anc/NotifyServicePegasus_1.0");
        proxySpringBeanFactory.setServiceInterface("com.huifu.anc.facade.service.NotifyService");
        proxySpringBeanFactory.setTimeout(1000);
        proxySpringBeanFactory.setSubscribe(true);
        proxySpringBeanFactory.setCallMethod("sync");
        return proxySpringBeanFactory;
    }
}
