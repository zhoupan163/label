package com.pxing.label.web.controller.business;

import com.pxing.label.business.domain.entity.LabelTagEntity;
import com.pxing.label.business.service.LabelTagService;
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
 * 项目信息
 *
 * @author pxing 官方网址：www.pxing.vip
 */
@RestController
@RequestMapping("/business/labelTag/")
public class LabelTagController extends BaseController
{
    @Autowired
    private LabelTagService labelTagService;

    @Autowired
    private TokenService tokenService;

    /**
     * 获取项目列表
     */
    //@PreAuthorize("@ss.hasPermi('business:labelTag:list')")
    @GetMapping("/list")
    public TableDataInfo list(LabelTagEntity labelTag)
    {
        startPage();
        List<LabelTagEntity> list = labelTagService.selectLabelTagList(labelTag);
        return getDataTable(list);
    }

    /**
     * 新增项目
     */
    //@PreAuthorize("@ss.hasPermi('business:labelTag:add')")
    @Log(title = "项目管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody LabelTagEntity labelTag, HttpServletRequest request)
    {
        LoginUser loginUser= tokenService.getLoginUser(request);
        labelTag.setCreateBy(loginUser.getUsername());
        return toAjax(labelTagService.insertLabelTag(labelTag));
    }

    /**
     * 修改项目
     */
    @PreAuthorize("@ss.hasPermi('business:labelTag:edit')")
    @Log(title = "项目管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody LabelTagEntity labelTag)
    {

        return toAjax(labelTagService.updateLabelTag(labelTag));
    }

    /**
     * 删除项目
     */
    @PreAuthorize("@ss.hasPermi('business:labelTag:remove')")
    @Log(title = "项目管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] projectIds)
    {
        return toAjax(labelTagService.deleteLabelTagByIds(projectIds));
    }


}