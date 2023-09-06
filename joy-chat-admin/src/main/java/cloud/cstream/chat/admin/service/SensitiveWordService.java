package cloud.cstream.chat.admin.service;

import cloud.cstream.chat.core.domain.query.SensitiveWordPageQuery;
import cloud.cstream.chat.core.domain.vo.SensitiveWordVO;
import cloud.cstream.chat.core.domain.entity.SensitiveWordDO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Anker
 * @date 2023/3/28 21:07
 * 敏感词业务接口
 */
public interface SensitiveWordService extends IService<SensitiveWordDO> {

    /**
     * 敏感词分页查询
     *
     * @param sensitiveWordPageQuery 查询条件
     * @return 敏感词分页列表
     */
    IPage<SensitiveWordVO> pageSensitiveWord(SensitiveWordPageQuery sensitiveWordPageQuery);

    void add(String word);

    void delete(Integer id);
}
