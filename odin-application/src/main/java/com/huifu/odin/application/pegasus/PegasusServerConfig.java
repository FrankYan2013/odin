package com.huifu.odin.application.pegasus;

import com.huifu.odin.facade.service.acct.AcctFacade;
import com.huifu.odin.facade.service.trans.TransFacade;
import com.huifu.pegasus.server.rpc.registry.ServiceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author frank
 */
@Configuration
public class PegasusServerConfig {

    @Autowired
    TransFacade transFacade;

    @Autowired
    AcctFacade acctFacade;

    @Value("${pegasus.port:8911}")
    int pegasusuPort;

    @Bean(initMethod = "init")
    public ServiceRegistry initServiceRegistry() {
        Map<String, Object> services = new HashMap<>();
        ServiceRegistry serviceRegistry = new ServiceRegistry();
        services.put("http://odin.huifu.com/odin/transService_1.0.0", transFacade);
        services.put("http://odin.huifu.com/odin/acctService_1.0.0", acctFacade);
        serviceRegistry.setPort(pegasusuPort);
        serviceRegistry.setServices(services);
        return serviceRegistry;
    }
}
