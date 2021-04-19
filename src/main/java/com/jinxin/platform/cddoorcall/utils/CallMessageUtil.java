package com.jinxin.platform.cddoorcall.utils;

import com.jinxin.platform.cddoorcall.pojo.dto.CallMessageResult;

/**
 * Date: 2021-04-19
 * Author: yangjie
 * desc:
 **/
public class CallMessageUtil {
    private static CallMessageResult callMessageResult = new CallMessageResult();

    public static CallMessageResult getBeatData() {
        if (callMessageResult == null) {
            callMessageResult = new CallMessageResult();
        }
        callMessageResult.setStatus(101);
        callMessageResult.setMessage("心跳信息");
        callMessageResult.setData(null);
        return callMessageResult;
    }
}
