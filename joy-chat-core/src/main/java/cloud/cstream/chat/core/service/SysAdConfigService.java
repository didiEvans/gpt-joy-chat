package cloud.cstream.chat.core.service;

import cloud.cstream.chat.core.domain.entity.SysAdConfigDO;
import cloud.cstream.chat.core.domain.pojo.CosFileResourcePair;
import cloud.cstream.chat.core.domain.query.SysAdConfigQuery;
import cloud.cstream.chat.core.domain.request.SysAdConfigRequest;
import cloud.cstream.chat.core.domain.vo.SysAdClientViewVO;
import cloud.cstream.chat.core.domain.vo.SysAdConfigVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 系统广告配置
 *
 * @author Anker
 */
public interface SysAdConfigService extends IService<SysAdConfigDO> {
    /**
     * 分页查询
     *
     * @param pageQuery
     * @return
     */
    Page<SysAdConfigVO> queryPage(SysAdConfigQuery pageQuery);

    /**
     * 新增
     *
     * @param request
     */
    void add(SysAdConfigRequest request);

    /**
     * 更新
     *
     * @param request
     */
    void updateConfig(SysAdConfigRequest request);

    /**
     * 启用或禁用开关
     *
     * @param request
     */
    void enableSwitch(SysAdConfigRequest request);

    /**
     * 获取启用中的开关
     *
     */
    List<SysAdClientViewVO> getAdList();

    List<CosFileResourcePair> getAdCoverPicResources();

}
