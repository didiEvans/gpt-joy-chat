package cloud.cstream.chat.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author evans
 * @description
 * @date 2023/5/7
 */
@Getter
@AllArgsConstructor
public enum InviteTaskStageEnum {

    /**
     * 注册成功
     */
    REGISTRATION_SUCCESSFUL(100, "分享并注册成功-赠送10次", 10),
    /**
     * 成功发起对话
     */
    SESSION_SUCCESSFUL(200, "成功发起对话-赠送5次", 5),
    /**
     * 首次购买任意套餐-赠送15次
     */
    FIRST_PURCHASE_VAS(300, "首次购买任意套餐-赠送15次", 15)
    ;


    private final Integer code;

    private final String describe;

    private final Integer rewardCount;


    public static InviteTaskStageEnum load(Integer code){
        return Arrays.stream(values()).filter(e -> e.getCode().equals(code)).findAny().orElse(null);
    }
}
