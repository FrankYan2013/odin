package com.huifu.odin.test.anc;

import com.alibaba.fastjson.JSON;
import com.huifu.anc.facade.model.NotifySendDTO;
import com.huifu.anc.facade.response.NotifyResponseDTO;
import com.huifu.anc.facade.service.NotifyService;
import com.huifu.odin.integration.pegasus.PegasusClientConfig;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PegasusClientConfig.class)
public class AncTest {

    @Autowired
    NotifyService notifyService;

    @Test
    @Ignore
    public void 测试钉钉交易() {
        NotifySendDTO notifySendDTO = new NotifySendDTO();
        notifySendDTO.setNotifyType("1");
        notifySendDTO.setMessage("[这是测试消息]Odin检测数据库异常，系统优雅降级。");
        notifySendDTO.setTargetId("5d4de99d40baf31bfd64efcad4a1d6a4d3260c504ba567796dc0fa5077044701");
        NotifyResponseDTO notifyResponseDTO = this.notifyService.send(notifySendDTO);
        System.out.println(JSON.toJSONString(notifyResponseDTO));
    }

}
