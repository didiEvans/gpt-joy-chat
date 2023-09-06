package cloud.cstream.chat.core.service.impl;

import cloud.cstream.chat.common.util.ThrowExceptionUtil;
import cloud.cstream.chat.common.util.WebUtil;
import cloud.cstream.chat.common.enums.EmailBizTypeEnum;
import cloud.cstream.chat.core.config.EmailConfig;
import cloud.cstream.chat.core.domain.entity.EmailVerifyCodeDO;
import cloud.cstream.chat.core.mapper.EmailVerifyCodeMapper;
import cloud.cstream.chat.core.service.EmailVerifyCodeService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 邮箱验证码核销记录业务实现类
 *
 * @author CoDeleven
 */
@Service
public class EmailVerifyCodeServiceImpl extends ServiceImpl<EmailVerifyCodeMapper, EmailVerifyCodeDO>
        implements EmailVerifyCodeService {

    @Resource
    private EmailConfig emailConfig;

    @Override
    public EmailVerifyCodeDO createVerifyCode(EmailBizTypeEnum emailBizTypeEnum, String identity, Integer uid) {
        EmailVerifyCodeDO verifyCode = new EmailVerifyCodeDO();
        verifyCode.setVerifyCode(RandomUtil.randomString(32));
        verifyCode.setIsUsed(false);
        verifyCode.setUid(uid);
        verifyCode.setVerifyIp(WebUtil.getIp());
        verifyCode.setBizType(emailBizTypeEnum);
        verifyCode.setToEmailAddress(identity);
        verifyCode.setExpireAt(DateUtil.offsetMinute(new Date(), emailConfig.getVerifyCodeExpireMinutes()));
        this.save(verifyCode);
        return verifyCode;
    }

    @Override
    public EmailVerifyCodeDO findAvailableByVerifyCode(String verifyCode) {
        return getOne(new LambdaQueryWrapper<EmailVerifyCodeDO>()
                .eq(EmailVerifyCodeDO::getVerifyCode, verifyCode)
                .eq(EmailVerifyCodeDO::getIsUsed, 0)
                // 当前时间小于过期时间
                .gt(EmailVerifyCodeDO::getExpireAt, new Date()));
    }

    @Override
    public void verifySuccess(EmailVerifyCodeDO availableVerifyCode) {
        ThrowExceptionUtil.isFalse(update(new EmailVerifyCodeDO(), new LambdaUpdateWrapper<EmailVerifyCodeDO>()
                        .set(EmailVerifyCodeDO::getVerifyIp, WebUtil.getIp())
                        .set(EmailVerifyCodeDO::getIsUsed, true)
                        // 乐观锁
                        .eq(EmailVerifyCodeDO::getIsUsed, false)
                        .eq(EmailVerifyCodeDO::getId, availableVerifyCode.getId())))
                .throwMessage("邮箱验证码核销失败");
    }
}




