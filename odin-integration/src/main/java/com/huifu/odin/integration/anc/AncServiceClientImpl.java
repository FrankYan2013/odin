package com.huifu.odin.integration.anc;

import com.alibaba.fastjson.JSON;
import com.huifu.anc.facade.model.NotifySendDTO;
import com.huifu.anc.facade.response.NotifyResponseDTO;
import com.huifu.anc.facade.service.NotifyService;
import com.huifu.pyxis.client.PyxisConfig;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author frank
 */
@Service
public class AncServiceClientImpl implements AncServiceClient {

    @Autowired
    private NotifyService notifyService;

    private static Logger logger = LoggerFactory.getLogger(AncServiceClientImpl.class);

    @Override
    public void sendToDingDing(String message) {
        if (!StringUtils.isEmpty(message)) {
            String property = PyxisConfig.getInstance().getProperty("anc.target.id");
            NotifySendDTO notifySendDTO = new NotifySendDTO();
            notifySendDTO.setNotifyType("1");
            notifySendDTO.setMessage(message);
            notifySendDTO.setTargetId(property);
            try {
                NotifyResponseDTO notifyResponseDTO = this.notifyService.send(notifySendDTO);
                logger.info("发送钉钉报警成功,发送消息:" + message + ",返回:" + JSON.toJSONString(notifyResponseDTO));
            } catch (Exception e) {
                logger.error("发送钉钉报警失败,原因:" + e.getLocalizedMessage());
            }
        }
    }

    public NotifyService getNotifyService() {
        return notifyService;
    }

    public void setNotifyService(NotifyService notifyService) {
        this.notifyService = notifyService;
    }
}
