package com.pxing.label.common.exception.user;

/**
 * 验证码失效异常类
 *
 * @author pxing 官方网址：www.pxing.vip
 */
public class CaptchaExpireException extends UserException
{
    private static final long serialVersionUID = 1L;

    public CaptchaExpireException()
    {
        super("user.jcaptcha.expire", null);
    }
}
