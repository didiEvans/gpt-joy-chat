package cloud.cstream.chat.admin.service.impl;

import cloud.cstream.chat.common.enums.EnableDisableStatusEnum;
import cloud.cstream.chat.core.domain.query.SensitiveWordPageQuery;
import cloud.cstream.chat.core.domain.vo.SensitiveWordVO;
import cloud.cstream.chat.admin.converter.SensitiveWordConverter;
import cloud.cstream.chat.admin.service.SensitiveWordService;
import cloud.cstream.chat.common.util.PageUtil;
import cloud.cstream.chat.core.domain.entity.SensitiveWordDO;
import cloud.cstream.chat.core.mapper.SensitiveWordMapper;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author Anker
 * @date 2023/3/28 21:08
 * 敏感词业务实现类
 */
@Service
public class SensitiveWordServiceImpl extends ServiceImpl<SensitiveWordMapper, SensitiveWordDO> implements SensitiveWordService {

    @Override
    public IPage<SensitiveWordVO> pageSensitiveWord(SensitiveWordPageQuery sensitiveWordPageQuery) {
        IPage<SensitiveWordDO> sensitiveWordPage = page(new Page<>(sensitiveWordPageQuery.getPageNum(), sensitiveWordPageQuery.getPageSize()),
                new LambdaQueryWrapper<SensitiveWordDO>()
                        .eq(Objects.nonNull(sensitiveWordPageQuery.getStatus()), SensitiveWordDO::getStatus, sensitiveWordPageQuery.getStatus())
                        .like(StrUtil.isNotBlank(sensitiveWordPageQuery.getWord()), SensitiveWordDO::getWord, sensitiveWordPageQuery.getWord())
                        .orderByDesc(SensitiveWordDO::getCreateTime));

        return PageUtil.toPage(sensitiveWordPage, SensitiveWordConverter.INSTANCE.entityToVO(sensitiveWordPage.getRecords()));
    }

    @Override
    public void add(String word) {
        save(SensitiveWordDO.builder()
                .word(word)
                .status(EnableDisableStatusEnum.ENABLE).build());
    }

    @Override
    public void delete(Integer id) {
        removeById(id);
    }
}
