package com.jinxin.platform.cddoorcall.pojo.dto;

import lombok.Data;

/**
 * Date: 2021-04-19
 * Author: yangjie
 * desc:
 **/
@Data
public class CallMessageResult {
    //101-心跳包,200-门禁机呼叫，300-app挂断,301-门禁机挂断,400-错误
    private Integer status;
    //提示信息
    private String message;
    //具体的值
    private Object data;
}
