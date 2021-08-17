package com.pxing.label.common.exception.user;

import com.pxing.label.common.exception.BaseException;

/**
 * 用户信息异常类
 *
 * @author pxing 官方网址：www.pxing.vip
 */
public class UserException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args)
    {
        super("user", code, args, null);
    }
}
