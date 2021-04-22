package com.jinxin.platform.cddoorcall.pojo.dto;

import lombok.Data;

/**
 * Date: 2021-04-22
 * Author: yangjie
 * desc:
 **/
@Data
public class CallMessageDto {
    //ip地址
    private String ip;
    //账号
    private String account;
    //密码
    private String password;
    //用户手机号
    private String phone;
}
