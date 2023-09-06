package cloud.cstream.chat.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DirectionTypeEnum {

    IN(1, "入账"),
    OUT(-1, "出账");

    private final Integer type;

    private final String name;



}
