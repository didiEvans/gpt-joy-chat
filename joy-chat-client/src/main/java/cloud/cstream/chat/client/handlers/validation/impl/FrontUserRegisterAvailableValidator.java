package cloud.cstream.chat.client.handlers.validation.impl;

import cloud.cstream.chat.client.domain.request.RegisterFrontUserRequest;
import cloud.cstream.chat.client.service.strategy.user.AbstractRegisterTypeStrategy;
import cloud.cstream.chat.common.enums.ClientUserRegisterTypeEnum;
import cloud.cstream.chat.common.util.SimpleCaptchaUtil;
import cloud.cstream.chat.client.handlers.validation.annotation.FrontUserRegisterAvailable;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.PhoneUtil;
import cn.hutool.core.util.StrUtil;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 前端用户注册有效性校验器
 *
 * @author CoDeleven
 */
@Slf4j
public class FrontUserRegisterAvailableValidator implements ConstraintValidator<FrontUserRegisterAvailable, RegisterFrontUserRequest> {

    @Override
    public void initialize(FrontUserRegisterAvailable constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(RegisterFrontUserRequest registerRequest, ConstraintValidatorContext context) {
        // 可能是手机，也可能是邮箱，取决于 RegisterFrontUserRequest#registerType
        String identity = registerRequest.getIdentity();
        ClientUserRegisterTypeEnum registerType = registerRequest.getRegisterType();
        boolean isValid = isUnregisteredIdentity(registerType, identity, context);
        if (!isValid) {
            log.info("注册 {} 注册账号：{} 已使用，校验不通过", registerType.getDesc(), registerRequest.getIdentity());
            return false;
        }
        // 如果注册类型=手机，验证手机号是否有效；如果注册类型=邮箱，验证邮箱是否有效
        isValid = isValidFormatIdentity(registerType, identity, context);
        if (!isValid) {
            log.info("注册 {} 注册账号：{} 格式不正确，校验不通过", registerType.getDesc(), registerRequest.getIdentity());
            return false;
        }
        if (StrUtil.isBlank(registerRequest.getPicCodeSessionId())) {
            log.info("注册 {} 注册账号：{} 图形验证码会话不存在，校验不通过", registerType.getDesc(), registerRequest.getIdentity());
            return false;
        }
        isValid = SimpleCaptchaUtil.verifyCaptcha(registerRequest.getPicCodeSessionId(), registerRequest.getPicVerificationCode());
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("图形验证码输入错误").addConstraintViolation();
            log.info("注册 {} 注册账号：{} 验证码错误，校验不通过", registerType.getDesc(), registerRequest.getIdentity());
            return false;
        }
        return true;
    }

    /**
     * 验证注册载体是否使用过
     *
     * @return true未使用过，校验通过；false已注册过，校验不通过
     */
    private boolean isUnregisteredIdentity(ClientUserRegisterTypeEnum registerType, String identity,
                                           ConstraintValidatorContext context) {
        // 检测账号是否已注册
        AbstractRegisterTypeStrategy registerStrategy = AbstractRegisterTypeStrategy.findStrategyByRegisterType(registerType);
        // 主要检测手机号/邮箱 是否已使用，
        boolean isUsed = registerStrategy.identityUsed(identity);
        if (isUsed) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(registerType.getDesc() + "已使用")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

    /**
     * 验证注册载体是否符合格式
     *
     * @param registerType 注册类型
     * @param identity     注册载体标识，全局唯一
     * @return true校验通过；false校验不通过
     */
    private boolean isValidFormatIdentity(ClientUserRegisterTypeEnum registerType, String identity, ConstraintValidatorContext context) {
        boolean isValid = true;
        // 验证邮箱/手机格式是否正确
        if (registerType == ClientUserRegisterTypeEnum.EMAIL && !Validator.isEmail(identity)) {
            isValid = false;
        } else if (registerType == ClientUserRegisterTypeEnum.PHONE && !PhoneUtil.isPhone(identity)) {
            isValid = false;
        }
        // 自定义验证结果信息
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(registerType.getDesc() + "格式不正确")
                    .addConstraintViolation();
        }
        return isValid;
    }
}
