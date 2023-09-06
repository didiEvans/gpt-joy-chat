package cloud.cstream.chat.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 百度文本盛和命中枚举
 *
 * @author evans
 * @description
 * @date 2023/5/20
 */
@Getter
@AllArgsConstructor
public enum BaiDuTextReviewHitEnum {

    /**
     * 合规
     */
    HIT_1(1, "合规"),

    HIT_2(2, "不合规"),

    HIT_3(3, "疑似"),

    HIT_4(4, "审核失败")
    ;



    private final Integer code;


    private final String describe;


    public static BaiDuTextReviewHitEnum load(Integer code){
        return Arrays.stream(values()).filter(b -> b.getCode().equals(code)).findFirst().orElse(HIT_4);
    }

    public static boolean isHit(Integer code){
        BaiDuTextReviewHitEnum hit = load(code);
        return hit.equals(HIT_2) || hit.equals(HIT_3);
    }
}
