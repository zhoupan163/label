package com.pxing.label.common.utils;

/**
 * 处理并记录日志文件
 *
 * @author pxing 官方网址：www.pxing.vip
 */
public class LogUtils
{
    public static String getBlock(Object msg)
    {
        if (msg == null)
        {
            msg = "";
        }
        return "[" + msg.toString() + "]";
    }
}
