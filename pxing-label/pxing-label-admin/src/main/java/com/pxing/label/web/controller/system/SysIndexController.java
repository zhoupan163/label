package com.pxing.label.web.controller.system;

import com.pxing.label.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pxing.label.common.config.PxingLabelConfig;

/**
 * 首页
 *
 * @author pxing 官方网址：www.pxing.vip
 */
@RestController
public class SysIndexController
{
    /** 系统基础配置 */
    @Autowired
    private PxingLabelConfig PxingLabelConfig;

    /**
     * 访问首页，提示语
     */
    @RequestMapping("/")
    public String index()
    {
        return StringUtils.format("欢迎使用{}后台管理框架，当前版本：v{}，请通过前端地址访问。", PxingLabelConfig.getName(), PxingLabelConfig.getVersion());
    }
}
