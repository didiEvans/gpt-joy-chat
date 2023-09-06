package cloud.cstream.chat.core.domain.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CardIssuingPageQuery extends PageQuery{
    /**
     * 用户账户
     */
    private String convertUserAccount;
    /**
     * 用户名
     */
    private String convertUser;
    /**
     * 增值套餐id
     */
    private Integer vasPkgId;
    /**
     * 是否被兑换
     */
    private Integer converted;
}
