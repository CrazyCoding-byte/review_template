package com.yzx.crazycodingbytecommon.schedule;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yzx.crazycodingbytecommon.entity.LocalMessage;
import com.yzx.crazycodingbytecommon.mapper.LocalMessageMapper;
import com.yzx.crazycodingbytecommon.service.MqCommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @className: LocalMessageSchedule
 * @author: yzx
 * @date: 2026/1/14 15:33
 * @Version: 1.0
 * @description:
 */

@Component
@RequiredArgsConstructor
public class LocalMessageSchedule {
    private final LocalMessageMapper localMessageMapper;
    private final MqCommonService commonService;

    @Scheduled()
    public void localMessageSchedule() {
        //TODO 定时检查本地消息表，将未消费的消息进行重发
        //查询出所有状态未被修改的
        List<LocalMessage> localMessages = localMessageMapper.selectList(new LambdaQueryWrapper<LocalMessage>().eq(LocalMessage::getStatus, 0));
        for (LocalMessage localMessage : localMessages) {
            //TODO 重发
            commonService.sendNormalMessage(localMessage.getMessageId(), localMessage.getPayload(), localMessage.getBusinessType(), localMessage.getBusinessId(), localMessage.getMaxRetry());
        }
    }
}
