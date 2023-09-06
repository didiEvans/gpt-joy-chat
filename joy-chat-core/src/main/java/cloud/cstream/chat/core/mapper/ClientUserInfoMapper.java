package cloud.cstream.chat.core.mapper;

import cloud.cstream.chat.core.domain.entity.ClientUserInfoDO;
import cloud.cstream.chat.core.domain.query.FrontUserPageQuery;
import cloud.cstream.chat.core.domain.vo.ClientUserPageVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * 前端基础用户数据访问层
 *
 * @author CoDeleven
 * @date 2023.4.8
 */
public interface ClientUserInfoMapper extends BaseMapper<ClientUserInfoDO> {
    /**
     * 分页查询
     *
     * @param page  分页对象
     * @param pageQuery 分页查询参数
     * @return {@link Page< ClientUserPageVO >}
     */
    Page<ClientUserPageVO> queryPage(Page<Object> page, @Param("query") FrontUserPageQuery pageQuery);

    /**
     * 使用邮箱地址查询用户信息
     *
     * @param email
     * @return
     */
    ClientUserInfoDO queryClientUserInfoByEmailAccount(@Param("email") String email);

    ClientUserInfoDO queryClientUserInfoByIdentification(@Param("userName") String username);

    ClientUserInfoDO queryClientUserInfoByMobilePhone(@Param("phone") String phone);

}
