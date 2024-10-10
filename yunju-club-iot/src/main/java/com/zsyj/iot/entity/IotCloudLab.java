package com.zsyj.iot.entity;


import lombok.Data;

import java.util.Date;

/**
 * .
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/10/10
 **/
@Data
public class IotCloudLab {

    /* 主键ID */
    private Long id;

    /* 项目名称 */
    private String projectName;

    /* 完整状态 1为完成 0 为未完成 */
    private Integer finishStatus;

    /* 接收到的数据 */
    private String receivedData;

    /* 阿里云主账号或对应RAM用户的AccessKey */
    private String accessKey;

    /* 阿里云主账号或对应RAM用户的AccessKeySecret */
    private String accessSecret;

    /* 当前物联网平台对应实例中的消费组ID */
    private String consumerGroupId;

    /* IOT实例ID */
    private String iotInstanceId;

    /* 表示客户端ID */
    private String clientId;

    /* 启动AMQP客户端的连接数 */
    private Integer connectionCount;

    /* AMQP接入域名 */
    private String host;

    /* 创建人 */
    private String createdBy;

    /* 创建时间 */
    private Date createdTime;

    /* 更新人 */
    private String updateBy;

    /* 更新时间 */
    private Date updateTime;

    /* 是否被删除 0为删除 1已删除 */
    private String isDeleted;

}
