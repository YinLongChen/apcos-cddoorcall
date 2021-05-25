package com.jinxin.platform.cddoorcall.pojo.domains;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * (CddoorcallCallInfo)实体类
 *
 * @author cyl
 * @since 2021-05-20 09:29:29
 */
@Data
public class CddoorcallCallInfo {

    private String id;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 社区
     */
    private String community;
    /**
     * 楼栋
     */
    private String floor;
    /**
     * 房间号
     */
    private String roomNum;
    /**
     * 设备mac
     */
    private String mac;
    /**
     * 呼叫时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime callTime;
    /**
     * 挂断时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime hangUpTime;
    /**
     * 状态(1-通话中,2-挂断,3-异常挂断)
     */
    private String status;
    private String account;
    private String productCode;
}