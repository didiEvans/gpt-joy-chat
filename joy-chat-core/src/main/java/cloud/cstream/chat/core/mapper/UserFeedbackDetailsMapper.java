package cloud.cstream.chat.core.mapper;

        import cloud.cstream.chat.core.domain.entity.UserFeedbackDetailsDO;
        import cloud.cstream.chat.core.domain.vo.UserFeedbackDetailsPageVO;
        import com.baomidou.mybatisplus.core.mapper.BaseMapper;
        import org.apache.ibatis.annotations.Param;
        import org.springframework.stereotype.Repository;

        import java.util.List;

@Repository
public interface UserFeedbackDetailsMapper extends BaseMapper<UserFeedbackDetailsDO> {


    void readAll(@Param("gId") Integer gId, @Param("role") Integer role);

    List<UserFeedbackDetailsPageVO> queryDetailsList(@Param("gId") Integer gId, @Param("userId") Integer userId);

    Integer selectUnreadCount(@Param("userId") Integer userId);
}
