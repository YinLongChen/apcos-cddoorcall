package com.jinxin.platform.cddoorcall.websocket;

import com.alibaba.fastjson.JSON;
import com.jinxin.platform.cddoorcall.pojo.dto.CallMessageDto;
import com.jinxin.platform.cddoorcall.pojo.dto.CallMessageResult;
import com.jinxin.platform.cddoorcall.utils.CallMessageUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;

/**
 * Date: 2021-04-19
 * Author: yangjie
 * desc:
 **/
@Configuration
@EnableScheduling
public class MessageScheduleTask {

    @Resource
    private MessageWebSocket webSocketServer;

    @Scheduled(cron = "0/30 * * * * ?")
    private void configureTasks() {
        webSocketServer.sendMessage(null, JSON.toJSONString(CallMessageUtil.getBeatData()), null);
        //
        CallMessageResult messageData = CallMessageUtil.getMessageData();
        CallMessageDto callMessageDto = new CallMessageDto();
        callMessageDto.setIp("192.168.60.32");
        callMessageDto.setAccount("admin");
        callMessageDto.setPassword("123456");
        callMessageDto.setPhone("110");
        messageData.setData(callMessageDto);
        webSocketServer.sendMessage(null, JSON.toJSONString(messageData), null);
    }
}
