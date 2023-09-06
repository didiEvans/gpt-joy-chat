package cloud.cstream.chat.core.domain.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author evans
 * @description
 * @date 2023/5/7
 */
@Data
public class ClientUserPageVO {

    /**
     * 用户id
     */
    private Integer id;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 会话次数
     */

    private String mobilePhone;

    private String emailAddress;

    private Integer chatCount;
    /**
     * 当前套餐
     */
    private String currentVasPkg;
    /**
     * 0 正常, 1 禁用
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;



}
