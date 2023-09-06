package cloud.cstream.chat.core.domain.vo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author evans
 * @description
 * @date 2023/5/8
 */
@Data
@Builder
public class VasPkgVO {

    private Integer vasPkgId;

    private String vasPkgName;

    private Date expireTime;

}
