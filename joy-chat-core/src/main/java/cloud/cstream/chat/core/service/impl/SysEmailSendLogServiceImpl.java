package cloud.cstream.chat.core.service.impl;

import cloud.cstream.chat.common.util.WebUtil;
import cloud.cstream.chat.common.enums.EmailBizTypeEnum;
import cloud.cstream.chat.core.domain.entity.SysEmailSendLogDO;
import cloud.cstream.chat.core.mapper.SysEmailSendLogMapper;
import cloud.cstream.chat.core.service.SysEmailSendLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;

/**
 * 邮箱发送日志业务实现类
 *
 * @author CoDeleven
 */
@Service
public class SysEmailSendLogServiceImpl extends ServiceImpl<SysEmailSendLogMapper, SysEmailSendLogDO>
        implements SysEmailSendLogService {

    /**
     * 邮件发送成功的默认消息
     */
    private static final String DEFAULT_SEND_SUCCESS_MESSAGE = "success";
    private static final int STATUS_SEND_SUCCESS = 1;
    private static final int STATUS_SEND_FAILED = 0;

    @Override
    public void createSuccessLogBySysLog(String messageId, String from, String to, EmailBizTypeEnum bizType, String content) {
        SysEmailSendLogDO log = this.createLogBySysLog(messageId, from, to, bizType, content);
        log.setStatus(STATUS_SEND_SUCCESS);
        log.setMessage(DEFAULT_SEND_SUCCESS_MESSAGE);
        this.save(log);
    }

    @Override
    public void createFailedLogBySysLog(String messageId, String from, String to, EmailBizTypeEnum bizType, String content, String failedMsg) {
        SysEmailSendLogDO log = this.createLogBySysLog(messageId, from, to, bizType, content);
        log.setStatus(STATUS_SEND_FAILED);
        log.setMessage(failedMsg);
        this.save(log);
    }

    private SysEmailSendLogDO createLogBySysLog(String messageId, String from, String to, EmailBizTypeEnum bizType, String content) {
        SysEmailSendLogDO sendLog = new SysEmailSendLogDO();
        sendLog.setFromEmailAddress(from);
        sendLog.setToEmailAddress(to);
        sendLog.setRequestIp(WebUtil.getIp());
        sendLog.setContent(content);
        sendLog.setBizType(bizType);
        sendLog.setMessageId(messageId);
        return sendLog;
    }
}




