package cloud.cstream.chat.core.mapper;

import cloud.cstream.chat.core.domain.entity.ClientUserWalletDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

public interface ClientUserWalletMapper extends BaseMapper<ClientUserWalletDO> {
    /**
     * 用户id 查询
     *
     * @param userId
     * @return
     */
    ClientUserWalletDO selectByUid(@Param("userId") Integer userId);
}
