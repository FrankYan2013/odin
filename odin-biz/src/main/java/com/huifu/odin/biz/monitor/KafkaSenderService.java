package com.huifu.odin.biz.monitor;

import com.alibaba.fastjson.JSON;
import com.huifu.odin.biz.base.BaseBiz;
import com.huifu.odin.facade.service.trans.AcctTransResultPeg;
import com.huifu.odin.integration.anc.AncServiceClient;
import com.huifu.pyxis.client.PyxisConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * @author frank
 */
@Service
public class KafkaSenderService extends BaseBiz {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private AncServiceClient ancServiceClient;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public boolean sendToMimirByKafka(AcctTransResultPeg acctTransResultPeg) {
        try {
            String topic = PyxisConfig.getInstance().getProperty("kafka.monitor.topic", "odin_trans_monitor");
            boolean kafkaMonitorSwitch = PyxisConfig.getInstance().getBooleanProperty("kafka.monitor.switch");
            if (kafkaMonitorSwitch) {
                super.bizLogDebug("发送mimir监控", acctTransResultPeg, logger);
                super.bizLogDebug("发送mimir队列名:", topic, logger);
                String data = JSON.toJSONString(acctTransResultPeg);
                super.bizLogDebug("发送mimir监控数据:", data, logger);
                ListenableFuture<SendResult<String, String>> send = kafkaTemplate.send(topic, data);
                send.addCallback(o -> super.bizLogDebug("发送mimir监控数据成功", null, logger), throwable ->
                        ancServiceClient.sendToDingDing("Odin报警[Kafka上报交易信息失败,交易信息:" + acctTransResultPeg.toString() + ",异常信息:" + throwable.getLocalizedMessage() + "]")
                );
            } else {
                super.bizLogDebug("kafka monitor switch off!", null, logger);
            }
        } catch (Exception e) {
            logger.error("kafka报送交易信息失败:" + e);
            ancServiceClient.sendToDingDing("Kafka上报交易信息失败,交易信息:" + acctTransResultPeg.toString() + "异常信息:" + e.getLocalizedMessage());
        }
        return true;
    }


}
