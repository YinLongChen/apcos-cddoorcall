package com.jinxin.platform.cddoorcall.rpc;

import com.alibaba.fastjson.JSONObject;
import com.jinxin.platform.base.common.pojo.InvokeCfg;
import com.jinxin.platform.base.common.pojo.JsonResult;
import com.jinxin.platform.base.common.support.BusinessDelegate;
import com.jinxin.platform.cddoorcall.exception.ParameterRuntimeException;
import com.jinxin.platform.cddoorcall.pojo.dto.UserDto;
import com.jinxin.platform.cddoorcall.utils.FakeData;
import com.jinxin.platform.cddoorcall.utils.MockUserSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * Date: 2021-04-22
 * Author: yangjie
 * desc:
 **/
@Slf4j
@Service
public class DoorCallRpcService {
    @Autowired
    private BusinessDelegate businessDelegate;
    private final boolean fake = FakeData.fake;
    @Resource
    MockUserSupport mockUserSupport;
    /**
     * 获取当前用户信息
     */
    public UserDto getCurrentUser() {
        InvokeCfg invokeCfg = new InvokeCfg();
        invokeCfg.setModuleKey("DoorCallRpcService");
        invokeCfg.setClassName("user");
        invokeCfg.setFunName("getCurrentUser");
        HashMap<String, Object> resultMap = new HashMap<>(12);
        invokeCfg.setArgs(resultMap);
        if (fake) {
            return mockUserSupport.getCurrentUser();
        }
        JsonResult result = businessDelegate.invoke(invokeCfg);
        log.info("获取当前用户信息,返回数据为{}", result);
        if (result.getSuccess()) {
            if (result.getData() == null) {
                return null;
            }
            try {
                return JSONObject.parseObject(result.getData().toString(), UserDto.class);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ParameterRuntimeException(400, "获取当前用户信息错误," + result.getMsg());
            }
        }
        return null;
    }
}
