package cloud.cstream.chat.core.service;

import java.util.List;

/**
 * 邮箱服务
 *
 * @author CoDeleven
 */
public interface EmailService {

    /**
     * 发送邮箱验证码到目标邮箱里去
     *
     * @param targetEmail  目标邮箱
     * @param inviteCode 虚拟别名, 就是加密后的邀请码
     * @param verifyCode   验证码
     */
    void sendForVerifyCode(String targetEmail, String inviteCode, String verifyCode);


    /**
     * 发送Api密钥警告邮件
     *
     * @param content  发送内容
s     */
    void send(List<String> target, String content);

    /**
     * 执行发送
     *
     * @param targetEmail
     * @param subject
     * @param content
     * @return
     */
    String sendMessage(String targetEmail, String subject, String content);
}
