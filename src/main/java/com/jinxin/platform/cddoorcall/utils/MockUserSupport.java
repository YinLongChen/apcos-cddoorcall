package com.jinxin.platform.cddoorcall.utils;

import com.jinxin.platform.cddoorcall.pojo.dto.UserDto;
import org.springframework.stereotype.Component;

/**
 * Date: 2021-03-29
 * Author: yangjie
 * desc:
 **/
@Component
public class MockUserSupport {

    public UserDto getCurrentUser() {
        return new UserDto("user123", "测试用户");
    }

}
