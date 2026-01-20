package com.yzx.crazycodingbytecommon.service;

import cn.hutool.core.util.IdUtil;
import com.yzx.crazycodingbytecommon.entity.BusinessException;
import com.yzx.crazycodingbytecommon.entity.LocalMessage;
import com.yzx.crazycodingbytecommon.mapper.LocalMessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;

import java.time.LocalDateTime;

/**
 * 通用MQ服务：兼容普通消息、事务消息、延迟队列消息，失败自动重试+本地消息表兜底
 */
@Service
@Slf4j
public class MqCommonService {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private LocalMessageMapper localMessageMapper;

    public boolean sendNormalMessage(String topic, Object msg, String businessType, String businessId, int maxRetry) {
        return sendBaseMessage(topic, msg, businessType, businessId, 0, maxRetry);
    }

    /**
     * @param delayLevel 延迟级别（RocketMQ默认：1=1s,2=5s,3=10s,4=30s,5=1m...18=2h）
     */
    public boolean sendDelayMessage(String topic, Object msg, String businessType, String businessId, int maxRetry, int delayLevel) {
        if (delayLevel < 1 || delayLevel > 18) {
            throw new BusinessException("延迟级别必须在1-18之间");
        }
        return sendBaseMessage(topic, msg, businessType, businessId, delayLevel, maxRetry);
    }

    public TransactionSendResult sendTransactionMessage(String businessId, String businessType, String topic, Message<?> msg, Object arg, int maxRetry) {
        try {
            TransactionSendResult result = rocketMQTemplate.sendMessageInTransaction(topic, msg, arg);
            if (result.getSendStatus() != SendStatus.SEND_OK) {
                log.error("事务消息发送失败，topic：{}，业务ID：{}，msgId：{}", topic, arg, result.getMsgId());
                throw new BusinessException("事务消息发送失败");
            }
            LocalMessage localMessage = buildLocalMessage(topic, msg.getPayload(), businessType, businessId, maxRetry);
            log.info("事务消息发送成功，topic：{}，业务ID：{}，msgId：{}", topic, arg, result.getMsgId());
            return result;
        } catch (Exception e) {
            log.error("事务消息发送异常，topic：{}，业务ID：{}", topic, arg, e);
            throw new BusinessException("事务消息发送异常：" + e.getMessage());
        }
    }

    private boolean sendBaseMessage(String topic, Object msg, String businessType, String businessId, int delayLevel, int maxRetry) {
        int currentRetry = 0;
        // 构建本地消息（使用实际实体类）
        LocalMessage localMessage = buildLocalMessage(topic, msg, businessType, businessId, maxRetry);
        long timeout = 3000L;

        while (currentRetry < maxRetry) {
            try {
                Message<?> rocketMessage = MessageBuilder.withPayload(msg).build();
                SendResult sendResult;

                if (delayLevel > 0) {
                    sendResult = rocketMQTemplate.syncSend(topic, rocketMessage, timeout, delayLevel);
                } else {
                    sendResult = rocketMQTemplate.syncSend(topic, rocketMessage, timeout);
                }

                if (SendStatus.SEND_OK.equals(sendResult.getSendStatus())) {
                    // 发送成功，更新本地消息状态为“已发送”
                    localMessage.setStatus(1);
                    localMessage.setUpdateTime(LocalDateTime.now());
                    localMessageMapper.updateById(localMessage);
                    return true;
                }
            } catch (Exception e) {
                currentRetry++;
                log.error("消息发送失败，第{}次重试，topic：{}，业务ID：{}", currentRetry, topic, businessId, e);
                // 更新重试次数
                localMessage.setRetryCount(currentRetry);
                localMessage.setErrorMsg(e.getMessage());
                localMessage.setUpdateTime(LocalDateTime.now());
                localMessageMapper.updateById(localMessage);
            }
        }

        // 所有重试失败，标记为“发送失败”
        localMessage.setStatus(2);
        localMessage.setUpdateTime(LocalDateTime.now());
        localMessageMapper.updateById(localMessage);
        return false;
    }

    private LocalMessage buildLocalMessage(String topic, Object msg, String businessType, String businessId, int maxRetry) {
        // 使用实际的LocalMessage实体类，并补充必要字段
        LocalMessage localMsg = new LocalMessage();
        localMsg.setMessageId(IdUtil.getSnowflakeNextIdStr()); // 生成唯一消息ID
        localMsg.setBusinessType(businessType); // 业务类型（如ORDER_CREATE）
        localMsg.setBusinessId(businessId); // 业务ID（如订单号）
        localMsg.setStatus(0); // 初始状态：待发送
        localMsg.setRetryCount(0); // 初始重试次数
        localMsg.setMaxRetry(maxRetry); // 最大重试次数
        localMsg.setPayload(JSON.toJSONString(msg)); // 消息内容JSON序列化
        localMsg.setCreateTime(LocalDateTime.now());
        localMsg.setUpdateTime(LocalDateTime.now());
        // 先保存到本地消息表
        localMessageMapper.insert(localMsg);
        return localMsg;
    }

    private void updateMessageStatus(String businessId, String businessType, Integer status, String errorMsg) {
        LocalMessage localMessage = new LocalMessage();
        localMessage.setStatus(status);
        localMessage.setErrorMsg(errorMsg);
        localMessageMapper.updateById(localMessage);
    }
}