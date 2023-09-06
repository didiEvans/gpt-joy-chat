package cloud.cstream.chat.core.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author evans
 * @description
 * @date 2023/5/28
 */
@Data
@TableName("sys_sms_config")
public class SysSmsConfigDO {
    /*
    CREATE TABLE `sys_sms_config` (
  `id` int(11) NOT NULL COMMENT '主键id',
  `operator_name` varchar(255) NOT NULL COMMENT '运营商名称',
  `endpoint` varchar(255) NOT NULL COMMENT '端点',
  `secret_id` varchar(255) NOT NULL COMMENT 'appId',
  `secret_key` varchar(255) NOT NULL COMMENT '密钥',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
     */

    @TableId(type = IdType.AUTO)
    private Integer id;


    private Integer handlerCode;


    private String operatorName;


    private String endpoint;

    private String sdkAppId;

    private String sign;

    private String secretId;

    private String secretKey;

    private String captchaTid;

    private Date createTime;

    private Date updateTime;
}
