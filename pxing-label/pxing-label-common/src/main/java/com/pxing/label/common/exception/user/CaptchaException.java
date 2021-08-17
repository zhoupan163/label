package com.pxing.label.common.exception.user;

/**
 * 验证码错误异常类
 *
 * @author pxing 官方网址：www.pxing.vip
 */
public class CaptchaException extends UserException
{
    private static final long serialVersionUID = 1L;

    public CaptchaException()
    {
        super("user.jcaptcha.error", null);
    }
}
