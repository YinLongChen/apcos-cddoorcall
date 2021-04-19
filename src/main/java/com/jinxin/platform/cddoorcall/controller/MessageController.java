package com.jinxin.platform.cddoorcall.controller;

import com.jinxin.platform.base.common.pojo.JsonResult;
import com.jinxin.platform.cddoorcall.config.ServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping(value = "/getUrl")
    public JsonResult getUrl() {
        return JsonResult.ok(serverConfig.getUrl());
    }
}
