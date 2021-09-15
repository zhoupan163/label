package com.pxing.label.web.controller.business;


import com.pxing.label.business.domain.entity.TaskEntity;
import com.pxing.label.business.domain.entity.VideoGroupEntity;
import com.pxing.label.business.domain.vo.LabelTaskVo;
import com.pxing.label.business.service.LabelTaskService;
import com.pxing.label.business.service.VideoGroupService;
import com.pxing.label.common.annotation.Log;
import com.pxing.label.common.core.controller.BaseController;
import com.pxing.label.common.core.domain.AjaxResult;
import com.pxing.label.common.core.domain.model.LoginUser;
import com.pxing.label.common.core.page.TableDataInfo;
import com.pxing.label.common.enums.BusinessType;
import com.pxing.label.framework.web.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 角色信息
 *
 * @author pxing 官方网址：www.pxing.vip
 */
@RestController
@RequestMapping("/business/labelVideoGroup")
public class LabelVIdeoGroupController extends BaseController
{
    @Autowired
    private VideoGroupService videoGroupService;

    @Autowired
    private TokenService tokenService;

    @GetMapping("/list")
    public TableDataInfo list()
    {
        startPage();
        List<VideoGroupEntity> videoGroupEntityList= videoGroupService.selectVideoGroupList();
        return getDataTable( videoGroupEntityList);
    }

    /**
     * 新增项目
     */
    //@PreAuthorize("@ss.hasPermi('business:labelTag:add')")
    @Log(title = "视频组添加", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody VideoGroupEntity videoGroupEntity, HttpServletRequest request)
    {
        LoginUser loginUser= tokenService.getLoginUser(request);
        videoGroupEntity.setCreateBy(loginUser.getUsername());
        return toAjax(videoGroupService.insertVideoGroup(videoGroupEntity));
    }



}
