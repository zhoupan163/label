package com.pxing.label.web.controller.business;


import com.pxing.label.business.domain.entity.TagEntity;
import com.pxing.label.business.domain.entity.TaskEntity;
import com.pxing.label.business.domain.vo.LabelTaskVo;
import com.pxing.label.business.service.LabelStreamService;
import com.pxing.label.business.service.LabelTaskService;
import com.pxing.label.business.service.LabelViaService;
import com.pxing.label.business.service.TaskStreamService;
import com.pxing.label.common.annotation.Log;
import com.pxing.label.common.core.controller.BaseController;
import com.pxing.label.common.core.domain.AjaxResult;
import com.pxing.label.common.core.domain.model.LoginUser;
import com.pxing.label.common.core.page.TableDataInfo;
import com.pxing.label.common.enums.BusinessType;
import com.pxing.label.framework.web.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
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
@RequestMapping("/business/labelTask")
public class LabelTaskController extends BaseController
{
    @Autowired
    private LabelTaskService labelTaskService;

    @Autowired
    private TokenService tokenService;

    @PreAuthorize("@ss.hasPermi('business:labelTask:list')")
    @GetMapping("/list")
    public TableDataInfo list(LabelTaskVo labelTaskVo)
    {
        startPage();
        List<TaskEntity> list = labelTaskService.selectLabelTaskList(labelTaskVo);
        return getDataTable(list);
    }

    /**
     * 新增任务
     */
    //@PreAuthorize("@ss.hasPermi('business:labelTag:add')")
    @Transactional
    @Log(title = "新增任务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody TaskEntity taskEntity, HttpServletRequest request)
    {
        LoginUser loginUser= tokenService.getLoginUser(request);
        taskEntity.setCreateBy(loginUser.getUsername());
        return toAjax(labelTaskService.insertTaskEntity(taskEntity));
    }



}
