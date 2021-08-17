package com.pxing.label.common.exception.file;

import com.pxing.label.common.exception.BaseException;

/**
 * 文件信息异常类
 *
 * @author pxing 官方网址：www.pxing.vip
 */
public class FileException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public FileException(String code, Object[] args)
    {
        super("file", code, args, null);
    }

}
