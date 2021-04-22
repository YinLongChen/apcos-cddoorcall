package com.jinxin.platform.cddoorcall.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
        clients.remove(userId);
        log.info("onClose,{}", userId);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(@PathParam("userId") String userId, String message, Session session) {
        this.sendMessage(userId, message, session);
        log.info("{}发送信息,内容为{}", userId, message);
    }

    @OnError
    public void onError(@PathParam("userId") String userId, Session session, Throwable error) {
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

    public void sendMessageToUser(String userId, String message, Session fromSession) {
        Session toSession = clients.get(userId);
        log.info("sendMessageToUser[{}]发送消息{},", userId, message);
        toSession.getAsyncRemote().sendText(message);
    }
}
