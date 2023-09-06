package cloud.cstream.chat.core.domain.query;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author evans
 * @description
 * @date 2023/5/7
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FrontUserPageQuery extends PageQuery {


    private String account;
}
