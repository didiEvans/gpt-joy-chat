package cloud.cstream.chat.core.domain.vo;

import lombok.Data;

import java.util.Date;

@Data
public class UserInviteeListVO {

    /**
     * 被邀请人昵称
     */
    private String nickName;
    /**
     * 被邀请人进行阶段
     */
    private Integer stage;
    /**
     * 阶段完成时间
     */
    private Date stageCompleteTime;
    /**
     * 奖励会话次数
     */
    private Integer rewardNum;
    /**
     * 注册时间
      */
    private Date registryTime;



}
