package com.jinxin.platform.cddoorcall.rpc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jinxin.platform.base.common.annotation.RecEvent;
import com.jinxin.platform.base.common.pojo.InvokeCfg;
import com.jinxin.platform.base.common.pojo.JsonResult;
import com.jinxin.platform.base.common.support.BusinessDelegate;
import com.jinxin.platform.base.common.support.PlatFormEventListener;
import com.jinxin.platform.cddoorcall.exception.ParameterRuntimeException;
import com.jinxin.platform.cddoorcall.pojo.domains.CddoorcallCallInfo;
import com.jinxin.platform.cddoorcall.pojo.dto.CallMessageDto;
import com.jinxin.platform.cddoorcall.pojo.dto.CallMessageResult;
import com.jinxin.platform.cddoorcall.pojo.dto.FloorDto;
import com.jinxin.platform.cddoorcall.pojo.dto.UserDto;
import com.jinxin.platform.cddoorcall.service.CddoorcallCallInfoService;
import com.jinxin.platform.cddoorcall.utils.FakeData;
import com.jinxin.platform.cddoorcall.utils.MockUserSupport;
import com.jinxin.platform.cddoorcall.websocket.MessageWebSocket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Date: 2021-04-22
 * Author: yangjie
 * desc:
 **/
@Slf4j
@Service
@RecEvent(eventType = "doorcall-report")
public class DoorCallRpcService implements PlatFormEventListener {
    @Autowired
    private BusinessDelegate businessDelegate;

    @Autowired
    MessageWebSocket webSocket;

    @Autowired
    private CddoorcallCallInfoService cddoorcallCallInfoService;
    /**
     * 获取当前用户信息
     */
    public UserDto getCurrentUser() {
        InvokeCfg invokeCfg = new InvokeCfg();
        invokeCfg.setModuleKey("cddoorcall");
        invokeCfg.setClassName("DoorCallRpcService");
        invokeCfg.setFunName("getCurrentUser");
        HashMap<String, Object> resultMap = new HashMap<>(12);
        invokeCfg.setArgs(resultMap);
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

    public Object controlDevice(JSONObject jsonObject, String controlParams, String operation) {
        Map<String, String> serviceMap = getServiceInfo();
        InvokeCfg cfg = new InvokeCfg();
        cfg.setModuleKey("cddoorcall");
        cfg.setClassName("DoorCallRpcService");
        cfg.setFunName("controlDevice");
        HashMap<String, String> resultMap = new HashMap<>();
        resultMap.put("controlParams", controlParams);
        resultMap.put("operation", operation);
        resultMap.put("serial_num", jsonObject.getString("mac"));
        resultMap.put("productId", jsonObject.getString("productCode"));
        resultMap.put("account", jsonObject.getString("account"));
        resultMap.put("service_key", serviceMap.get("service_key"));
        resultMap.put("service_id", serviceMap.get("service_id"));
        cfg.setArgs(resultMap);
        JsonResult result = businessDelegate.invoke(cfg);
        log.info("controlDeviceResult--------"+result);
        return result.getData();
    }

    public Map<String, String> getServiceInfo() {
        Map<String, String> resultMap = new HashMap<>();
        InvokeCfg cfg = new InvokeCfg();
        cfg.setModuleKey("cddoorcall");
        cfg.setClassName("DoorCallRpcService");
        cfg.setFunName("getServiceInfo");
        cfg.setArgs(resultMap);
        JsonResult result = businessDelegate.invoke(cfg);
        if (result.getSuccess()) {
            JSONObject jsonObject = JSONObject.parseObject(result.getData().toString());
            resultMap.put("service_key", jsonObject.getString("service_key"));
            resultMap.put("service_id", jsonObject.getString("service_id"));
        }
        return resultMap;
    }

    public FloorDto getFloor(String mac){
        FloorDto floorDto = new FloorDto();
        Map<String, String> resultMap = new HashMap<>();
        InvokeCfg cfg = new InvokeCfg();
        cfg.setModuleKey("cddoorcall");
        cfg.setClassName("DoorCallRpcService");
        cfg.setFunName("getFloor");
        resultMap.put("mac",mac);
        cfg.setArgs(resultMap);
        JsonResult result = businessDelegate.invoke(cfg);
        if (result.getSuccess()) {
            log.info("楼栋信息--"+result.getData().toString());
            floorDto = JSONObject.parseObject(result.getData().toString(),FloorDto.class);
        }
        return floorDto;
    }
    public UserDto getUserId(FloorDto floorDto){
        UserDto userDto = new UserDto();
        Map<String, String> resultMap = new HashMap<>();
        InvokeCfg cfg = new InvokeCfg();
        cfg.setModuleKey("cddoorcall");
        cfg.setClassName("DoorCallRpcService");
        cfg.setFunName("getUserId");
        resultMap.put("buildingId",floorDto.getBuildingId());
        resultMap.put("unitId",floorDto.getUnitId());
        resultMap.put("houseNum",floorDto.getHouseNum());
        cfg.setArgs(resultMap);
        JsonResult result = businessDelegate.invoke(cfg);
        if (result.getSuccess()) {
            log.info("用户信息--"+result.getData().toString());
            userDto = JSONObject.parseObject(result.getData().toString(),UserDto.class);
        }
        return userDto;
    }

    public FloorDto getUserId(String mac,String status,String productCode,String account){
        //1.根据序列号获取设备小区楼栋+房间号
        FloorDto floorDto = getFloor(mac);
        // 2.从基础模块获取用户手机号 userId
        String userId = getUserId(floorDto).getId();
        CddoorcallCallInfo cddoorcallCallInfo = cddoorcallCallInfoService.queryByUserId(userId);
        //3.发userId通知
        cddoorcallCallInfo.setUserId(userId);
        cddoorcallCallInfo.setAccount(account);
        cddoorcallCallInfo.setProductCode(productCode);
        if (status.equals("1")){
            cddoorcallCallInfo.setCallTime(LocalDateTime.now());
        }else {
            cddoorcallCallInfo.setHangUpTime(LocalDateTime.now());
        }
        cddoorcallCallInfo.setStatus(status);
        floorDto.setUserId(userId);
        return floorDto;
    }

    @Override
    public JsonResult event(Map param) {
        JSONObject jsonObject = JSON.parseObject(param.get("data").toString());
        log.info("report--------"+jsonObject.toString());
        if (jsonObject.get("cmd_name").equals("ReportCallName")){
            String mac = jsonObject.get("serial_num").toString();
            String productCode = jsonObject.get("product_code").toString();
            String account = jsonObject.get("account").toString();
            FloorDto floorDto = getUserId(mac,"1",productCode,account);
            CallMessageResult callMessageResult = new CallMessageResult();
            callMessageResult.setStatus(301);
            callMessageResult.setMessage("门禁机挂断");
            CallMessageDto callMessageDto = new CallMessageDto();
            callMessageDto.setIp(floorDto.getIp());
            callMessageDto.setAccount(floorDto.getAccount());
            callMessageDto.setPassword(floorDto.getPassword());
            callMessageDto.setPhone("001");
            callMessageResult.setData(callMessageDto);
            webSocket.sendMessageToUser(floorDto.getUserId(),JSON.toJSONString(callMessageResult));
        }else if (jsonObject.get("cmd_name").equals("ReportHangUp")){
            String mac = jsonObject.get("serial_num").toString();
            String productCode = jsonObject.get("product_code").toString();
            String account = jsonObject.get("account").toString();
            FloorDto floorDto = getUserId(mac,"2",productCode,account);
            CallMessageResult callMessageResult = new CallMessageResult();
            callMessageResult.setStatus(301);
            callMessageResult.setMessage("门禁机挂断");
            callMessageResult.setData(null);
            webSocket.sendMessageToUser(floorDto.getUserId(),JSON.toJSONString(callMessageResult));
        }
        return null;
    }
}
