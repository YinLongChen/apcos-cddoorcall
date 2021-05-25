package com.jinxin.platform.cddoorcall.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jinxin.platform.cddoorcall.pojo.domains.CddoorcallCallInfo;
import com.jinxin.platform.cddoorcall.pojo.dto.CallMessageResult;
import com.jinxin.platform.cddoorcall.rpc.DoorCallRpcService;
import com.jinxin.platform.cddoorcall.service.CddoorcallCallInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.jinxin.platform.cddoorcall.utils.URLEncoder.encodeURIComponent;

/**
 * Date: 2021-04-19
 * Author: yangjie
 * desc:
 **/
@Slf4j
@ServerEndpoint(value = "/doorCall/message/{userId}")
@Component
public class MessageWebSocket {
    /**
     * 存放所有在线的客户端
     */
    private static final Map<String, Session> clients = new ConcurrentHashMap<>();

    @Autowired
    private CddoorcallCallInfoService cddoorcallCallInfoService;

    @Autowired
    private DoorCallRpcService doorCallRpcService;

    @OnOpen
    public void onOpen(@PathParam("userId") String userId, Session session) {
        clients.put(userId, session);
        /*
         * 存放所有在线的客户端
         */
        log.info("有新连接加入：{}", userId);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(@PathParam("userId") String userId, Session session) {
        //更新通话记录
        updateCallInfo(userId,"3");
        clients.remove(userId);
        log.info("onClose,{}", userId);
    }

    public void updateCallInfo(String userId,String stauts){
        CddoorcallCallInfo cddoorcallCallInfo = cddoorcallCallInfoService.queryByUserId(userId);
        if (cddoorcallCallInfo != null){
            if (cddoorcallCallInfo.getStatus().equals("1")){
                cddoorcallCallInfo.setHangUpTime(LocalDateTime.now());
                cddoorcallCallInfo.setStatus(stauts);
                cddoorcallCallInfoService.update(cddoorcallCallInfo);
            }
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(@PathParam("userId") String userId, String message, Session session) {
        //挂断
        CallMessageResult callMessageResult = JSON.toJavaObject(JSONObject.parseObject(message),CallMessageResult.class);
        if (callMessageResult.getStatus()==300){
            JSONObject params = new JSONObject();
            JSONObject accountInfo = new JSONObject();
            CddoorcallCallInfo cddoorcallCallInfo = cddoorcallCallInfoService.queryByUserId(userId);
            accountInfo.put("mac",cddoorcallCallInfo.getMac());
            accountInfo.put("productCode",cddoorcallCallInfo.getProductCode());
            accountInfo.put("account",cddoorcallCallInfo.getAccount());
            params.put("p1","0");
            String operation = "CallState";
            String controlParams = encodeURIComponent(params.toJSONString());
            doorCallRpcService.controlDevice(accountInfo,controlParams,operation);
            updateCallInfo(userId,"2");

        }
        log.info("{}发送信息,内容为{}", userId, message);
    }

    @OnError
    public void onError(@PathParam("userId") String userId, Session session, Throwable error) {
        //更新通话记录
        updateCallInfo(userId,"3");
        log.error("发生错误,{}", userId);
        error.printStackTrace();
    }

    /**
     * 群发消息
     *
     * @param message 消息内容
     */
    public void sendMessage(String userId, String message, Session fromSession) {
        for (Map.Entry<String, Session> sessionEntry : clients.entrySet()) {
            Session toSession = sessionEntry.getValue();
            log.info("服务端给客户端[{}]发送消息{},", userId, message);
            if (toSession.isOpen()) {
                toSession.getAsyncRemote().sendText(message);
            }
        }
    }

    public void sendMessageToUser(String userId, String message) {
        Session toSession = clients.get(userId);
        log.info("sendMessageToUser[{}]发送消息{},", userId, message);
        toSession.getAsyncRemote().sendText(message);
    }
}
