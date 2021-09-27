package com.pxing.label.web.controller.business;


import com.pxing.label.business.domain.entity.ProjectStreamEntity;
import com.pxing.label.business.service.TaskDetailService;
import com.pxing.label.common.annotation.Log;
import com.pxing.label.common.core.controller.BaseController;
import com.pxing.label.common.core.domain.AjaxResult;
import com.pxing.label.common.core.domain.model.LoginUser;
import com.pxing.label.common.enums.BusinessType;
import com.pxing.label.framework.web.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 角色信息
 *
 * @author pxing 官方网址：www.pxing.vip
 */

@RestController
@RequestMapping("/business/image")
public class ImageTaskController extends BaseController{

    @Autowired
    private TokenService tokenService;

    @Autowired
    private TaskDetailService taskDetailService;


    @Log(title = "筛选stream流到图片任务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult addStreamToImageTask(@RequestBody ProjectStreamEntity projectStreamEntity, HttpServletRequest request)
    {
        LoginUser loginUser= tokenService.getLoginUser(request);

        return toAjax(taskDetailService.updateTaskDetailTotal(projectStreamEntity.getFrameSize(), ""));
    }



}
