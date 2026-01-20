package com.yzx.crazycodingbytepay;

import com.alibaba.fastjson.JSON;
import com.yzx.crazycodingbytepay.entity.MsgModel;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import static java.lang.System.currentTimeMillis;

@SpringBootTest
class CrazycodingBytePayApplicationTests {

    // 注入 RocketMQ 模板（用于发送消息）
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    // 测试1：发送消息到 test_top 主题
    @Test
    void sendMessageToTestTop() throws InterruptedException, MQClientException, MQBrokerException, RemotingException, UnsupportedEncodingException {
        //单项消息
        rocketMQTemplate.sendOneWay("bootOneWayTopic", "单项消息");
        // 使用RocketMQTemplate发送消息
        String topic = "delay_topic";
        String message1 = "技海拾贝的消息3bb";
        Message message = MessageBuilder
                .withPayload(message1)
                // 5.x 设置消息TTL（毫秒）：超过该时间未消费则过期
                .build();
        // 同步发送 延迟消息
        //第三个参数是消息的延迟等级，默认是4，最大支持18个等级，对应5.x的delayLevel，具体可以参考文档
        // 延迟等级：1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
        //第二个参数是超时时间 是broker响应的时间
        System.out.println("发送时间" + currentTimeMillis());
        rocketMQTemplate.syncSend(topic, message, 5000, 4);
        System.out.println("消息发送成功: " + message);
        List<MsgModel> msgModels = Arrays.asList(
                new MsgModel("qwer", 1, "下单"),
                new MsgModel("qwer", 1, "短信"),
                new MsgModel("qwer", 1, "物流"),
                new MsgModel("zxcv", 2, "下单"),
                new MsgModel("zxcv", 2, "短信"),
                new MsgModel("zxcv", 2, "物流")
        );
        //顺序消息
        msgModels.forEach(item -> {
            //一般都是以json的方式处理
            rocketMQTemplate.syncSendOrderly("bootOrdelyTopic", JSON.toJSONString(item), item.getOrderSn());
        });
    }

    @Test
    void sendMessageToTestTop2() throws InterruptedException, MQClientException, MQBrokerException, RemotingException, UnsupportedEncodingException {
        //使用RocketMQTemplate发送消息
        String topic = "test_order";
        //同步发送
//        for (int i = 0; i <= 10; i++) {
//            rocketMQTemplate.syncSend(topic, "我是第" + i + "个消息");
//        }

        List<MsgModel> msgModels = Arrays.asList(
                new MsgModel("qwer", 1, "下单"),
                new MsgModel("qwer", 1, "短信"),
                new MsgModel("qwer", 1, "物流"),
                new MsgModel("zxcv", 2, "下单"),
                new MsgModel("zxcv", 2, "短信"),
                new MsgModel("zxcv", 2, "物流")
        );
        //顺序消息
        msgModels.forEach(item -> {
            //一般都是以json的方式处理
            rocketMQTemplate.syncSendOrderly("test_order", JSON.toJSONString(item), item.getOrderSn());
        });
    }

    @Test
    void sendMessageToTestTop3() throws InterruptedException, MQClientException, MQBrokerException, RemotingException, UnsupportedEncodingException {
        //使用RocketMQTemplate发送消息
        String topic = "test_order";
        rocketMQTemplate.syncSend("test_order_topic:tagA", "我是一个带tag的消息");
    }
}
