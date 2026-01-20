package com.yzx.crazycodingbyteorder.listen;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @className: PayResultListener
 * @author: yzx
 * @date: 2026/1/10 22:32
 * @Version: 1.0
 * @description:
 */
@Component
@RocketMQMessageListener(topic = "PAY_RESULT_TOPIC", consumerGroup = "PAY_RESULT_GROUP")
public class PayResultListener implements RocketMQListener<String> {
    @Override
    public void onMessage(String s) {

    }
}
