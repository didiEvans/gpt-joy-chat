package cloud.cstream.chat.common.enums;

import cloud.cstream.chat.common.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author evans
 * @description
 * @date 2023/5/15
 */
@Getter
@AllArgsConstructor
public enum VasPkgEnum {
    /**
     * 日卡
     */
    DAY_PKG(1, "day_pkg", "日卡"),
    /**
     * 周卡
     */
    WEEK_PKG(2, "week_pkg", "周卡"),
    /**
     * 月卡
     */
        MONTH_PKG(3, "month_pkg", "月卡"),
    /**
     * 季卡
     */
    QUARTER_PKG(4, "quarter_pkg", "季卡")
    ;


    private final Integer code;

    private final String udid;

    private final String describe;

    public static VasPkgEnum loadByUdid(String udid){
        return Arrays.stream(values()).filter(e -> e.getUdid().equals(udid)).findFirst().orElseThrow(() -> new ServiceException("未知套餐"));
    }
}
