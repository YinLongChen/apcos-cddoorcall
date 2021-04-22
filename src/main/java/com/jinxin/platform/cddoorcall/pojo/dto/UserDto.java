package com.jinxin.platform.cddoorcall.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(value = "用户ID")
    private String userId;
    /**
     * 用户姓名
     */
    @ApiModelProperty(value = "用户姓名")
    private String userName;

    public UserDto() {
    }

    public UserDto(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }
}
