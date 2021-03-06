package com.pxing.label.web.controller.business;

import com.pxing.label.business.domain.entity.ProjectEntity;
import com.pxing.label.business.service.LabelProjectService;
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
 * 项目信息
 *
 * @author pxing 官方网址：www.pxing.vip
 */
@RestController
@RequestMapping("/business/labelProject/")
public class LabelProjectController extends BaseController
{
    @Autowired
    private LabelProjectService labelProjectService;

    @Autowired
    private TokenService tokenService;

    /**
     * 获取项目列表
     */
    //@PreAuthorize("@ss.hasPermi('business:labelProject:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProjectEntity labelProject)
    {
        startPage();
        List<ProjectEntity> list = labelProjectService.selectLabelProjectList(labelProject);
        return getDataTable(list);
    }

    /**
     * 新增项目
     */
    //@PreAuthorize("@ss.hasPermi('business:labelProject:add')")
    @Transactional
    @Log(title = "新增项目", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody ProjectEntity labelProject, HttpServletRequest request)
    {
        LoginUser loginUser= tokenService.getLoginUser(request);
        labelProject.setCreateBy(loginUser.getUsername());
        return toAjax(labelProjectService.insertLabelProject(labelProject));
    }

    /**
     * 修改项目
     */
    @PreAuthorize("@ss.hasPermi('business:labelProject:edit')")
    @Log(title = "项目管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody ProjectEntity labelProject)
    {

        return toAjax(labelProjectService.updateLabelProject(labelProject));
    }

    /**
     * 删除项目
     */
    @PreAuthorize("@ss.hasPermi('business:labelProject:remove')")
    @Log(title = "项目管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] projectIds)
    {
        return toAjax(labelProjectService.deleteLabelProjectByIds(projectIds));
    }


}
