package cloud.cstream.chat.core.domain.pojo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CosFileResourcePair {
    /**
     * 字段名称或标识
     */
    private String field;
    /**
     * 文件key
     */
    private List<String> fileKeys;
    /**
     * 字段名称
     */
    private List<String> fileAddress;
}
