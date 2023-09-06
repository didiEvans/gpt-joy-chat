package cloud.cstream.chat.core.domain.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class InviteePageQuery extends PageQuery{

    private Integer inviterUId;

    private String nickName;


    private Integer stage;


}
