package cloud.cstream.chat.core.domain.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author evans
 * @description
 * @date 2023/6/3
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PromptPageQuery extends PageQuery {


    private String title;


    private Integer uid;
}
