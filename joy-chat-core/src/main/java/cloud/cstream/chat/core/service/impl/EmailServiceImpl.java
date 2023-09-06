package cloud.cstream.chat.core.service.impl;

import cloud.cstream.chat.common.constants.CoreConstants;
import cloud.cstream.chat.common.enums.EmailBizTypeEnum;
import cloud.cstream.chat.common.util.EncryptUtil;
import cloud.cstream.chat.core.config.EmailConfig;
import cloud.cstream.chat.core.service.EmailService;
import cloud.cstream.chat.core.service.SysEmailSendLogService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 邮箱注册类型策略实现类
 *
 * @author CoDeleven
 */
@Slf4j
@Service
public class EmailServiceImpl implements EmailService {
    private final EmailConfig emailConfig;
    private final MailAccount mailAccount;
    private final SysEmailSendLogService emailLogService;

    public EmailServiceImpl(EmailConfig emailConfig, SysEmailSendLogService emailLogService) {
        this.emailConfig = emailConfig;
        this.emailLogService = emailLogService;

        mailAccount = new MailAccount();
        mailAccount.setHost(emailConfig.getHost());
        mailAccount.setPort(Integer.parseInt(emailConfig.getPort()));
        mailAccount.setFrom(emailConfig.getFrom());
        mailAccount.setUser(emailConfig.getUser());
        mailAccount.setAuth(emailConfig.getAuth());
        mailAccount.setDebug(true);
        mailAccount.setSslEnable(true);
        mailAccount.setPass(emailConfig.getPass());
        log.info("The email account is initialized：{} ", emailConfig);
    }

    @Override
    public void sendForVerifyCode(String targetEmail, String inviteCode, String verifyCode) {
        String url = StrUtil.format(emailConfig.getVerificationRedirectUrl(), verifyCode);
        if (CharSequenceUtil.isNotBlank(inviteCode)){
            url = StrUtil.builder(url).append("&virtualAlias=").append(EncryptUtil.aesEncryptHex(inviteCode)).toString();
        }
        String content = StrUtil.format(CoreConstants.EMAIL_AUTH_CONTENT, url) ;
        // 记录日志
        try {
            String sendMsgId = this.sendMessage(targetEmail, "点击进行【悦聊】账号注册验证 >>> ",content);
            emailLogService.createSuccessLogBySysLog(sendMsgId, mailAccount.getFrom(), targetEmail, EmailBizTypeEnum.REGISTER_VERIFY, content);
        } catch (Exception e) {
            log.error("邮件发送过程异常:", e);
            emailLogService.createFailedLogBySysLog("", mailAccount.getFrom(), targetEmail, EmailBizTypeEnum.REGISTER_VERIFY, content, e.getMessage());
        }
    }


    @Override
    public void send(List<String> target, String content) {
        if (CollUtil.isEmpty(target)) {
            return;
        }
        target.forEach(e -> this.sendMessage(e, "OpenAi ApiKey 失效!!!",content));
    }

    /**
     * 执行邮件发送
     *
     * @param targetEmail 目标邮箱
     * @param subject   主题
     * @param content   内容
     * @return
     */
    public String sendMessage(String targetEmail, String subject, String content) {
        return MailUtil.send(mailAccount, targetEmail, subject, content, true);
    }
}
