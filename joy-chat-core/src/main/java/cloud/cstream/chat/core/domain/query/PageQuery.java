package cloud.cstream.chat.core.domain.query;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author Anker
 * @date 2023/3/27 23:14
 * 分页参数
 */
@Data
public class PageQuery {

    private final Integer DEFAULT_PAGE_NUM = 1;
    private final Integer DEFAULT_PAGE_SIZE = 10;

    @NotNull(message = "第几页不能为空")
    private Integer pageSize = DEFAULT_PAGE_SIZE;

    @NotNull(message = "每页条数不能为空")
    private Integer pageNum = DEFAULT_PAGE_NUM;


    public <T> Page<T> toPage(){
        return new Page<>(pageNum, pageSize);
    }
}

