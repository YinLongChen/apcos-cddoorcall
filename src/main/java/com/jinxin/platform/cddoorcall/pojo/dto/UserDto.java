package com.jinxin.platform.cddoorcall.pojo.dto;

import lombok.Data;

/**
 * Date: 2021-03-29
 * Author: yangjie
 * desc:
 **/
@Data
public class UserDto {
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 用户姓名
     */
    private String userName;

    private String id;

    private String phone;

    private String name;

    public UserDto() {
    }

    public UserDto(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }
}
