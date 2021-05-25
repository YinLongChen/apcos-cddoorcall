package com.jinxin.platform.cddoorcall.controller;

import com.jinxin.platform.base.common.pojo.JsonResult;
import com.jinxin.platform.cddoorcall.config.ServerConfig;
import com.jinxin.platform.cddoorcall.rpc.DoorCallRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Date: 2021-04-19
 * Author: yangjie
 * desc:
 **/
@RestController
@RequestMapping(value = "/doorCall/message")
public class MessageController {
    @Autowired
    private ServerConfig serverConfig;
    @Resource
    DoorCallRpcService doorCallRpcService;

    @GetMapping(value = "/getServerUrl")
    public JsonResult getServerUrl() {
        return JsonResult.ok(serverConfig.getUrl());
    }

    @GetMapping(value = "/getCurrentUser")
    public JsonResult getCurrentUser() {
        return JsonResult.ok(doorCallRpcService.getCurrentUser());
    }
}
