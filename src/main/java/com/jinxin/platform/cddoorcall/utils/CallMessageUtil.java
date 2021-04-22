package com.jinxin.platform.cddoorcall.utils;

import com.jinxin.platform.cddoorcall.pojo.dto.CallMessageResult;

/**
 * Date: 2021-04-19
 * Author: yangjie
 * desc:
 **/
public class CallMessageUtil {
    private static CallMessageResult beatMessage = new CallMessageResult();
    private static CallMessageResult messageResult = new CallMessageResult();

    public static CallMessageResult getBeatData() {
        if (beatMessage == null) {
            beatMessage = new CallMessageResult();
        }
        beatMessage.setStatus(101);
        beatMessage.setMessage("心跳信息");
        beatMessage.setData(null);
        return beatMessage;
    }

    public static CallMessageResult getMessageData() {
        if (messageResult == null) {
            messageResult = new CallMessageResult();
        }
        messageResult.setStatus(200);
        messageResult.setMessage("呼叫信息");
        messageResult.setData(null);
        return messageResult;
    }
}
