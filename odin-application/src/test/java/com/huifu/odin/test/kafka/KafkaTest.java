package com.huifu.odin.test.kafka;

import com.huifu.odin.application.kafka.KafkaConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = KafkaConfig.class)
public class KafkaTest {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Test
    public void 测试卡夫卡发送() {
        String data = UUID.randomUUID().toString();
        System.out.println("发送消息:" + data);
        ListenableFuture kafkaOdin = kafkaTemplate.send("test_odin", data);
        kafkaOdin.addCallback(o -> System.out.println("send-消息发送成功"), throwable -> System.out.println("消息发送失败：" + throwable));

    }

}
