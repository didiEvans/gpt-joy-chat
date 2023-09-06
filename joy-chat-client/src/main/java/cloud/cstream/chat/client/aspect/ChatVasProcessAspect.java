package cloud.cstream.chat.client.aspect;

import cloud.cstream.chat.client.utils.ClientUserLoginUtil;
import cloud.cstream.chat.common.exception.ServiceException;
import cloud.cstream.chat.core.domain.entity.ClientUserInfoDO;
import cloud.cstream.chat.core.domain.entity.VasPkgDO;
import cloud.cstream.chat.core.service.ChatSessionCountLimitService;
import cloud.cstream.chat.core.service.ClientUserInfoService;
import cloud.cstream.chat.core.service.SensitiveWordTriggerRecordService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 聊天增值服务切面
 *
 * @author evans
 * @description
 * @date 2023/5/9
 */
@Aspect
@Component
public class ChatVasProcessAspect {

    @Autowired
    private ClientUserInfoService frontUserBaseService;
    @Autowired
    private ChatSessionCountLimitService chatSessionCountLimitService;
    @Autowired
    private SensitiveWordTriggerRecordService sensitiveWordTriggerRecordService;


    @Pointcut("execution(public * cloud.cstream.chat.client.controller.OpenAiChatClientController.sendMessage(..))")
    private void pointCut(){}


    @Before("pointCut()")
    public void before(JoinPoint joinPoint){
        Integer userId = ClientUserLoginUtil.getUserId();
        ClientUserInfoDO userInfo = frontUserBaseService.findUserInfoById(userId);
        if (Objects.isNull(userInfo)){
            return;
        }
        // 单位时间内命中敏感词上限, 符合条件则会抛出异常
        if (sensitiveWordTriggerRecordService.hitSensitiveWordLimit(userId, false)){
            throw new ServiceException("您命中敏感词次数过多,账号已被已被监管并冻结30分钟,请规范提问!如有疑问,请联系管理员");
        }
    }
}
