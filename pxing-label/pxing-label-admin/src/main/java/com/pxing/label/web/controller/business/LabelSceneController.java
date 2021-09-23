package com.pxing.label.web.controller.business;

import com.pxing.label.business.domain.entity.SceneEntity;
import com.pxing.label.business.service.LabelSceneService;
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
@RequestMapping("/business/labelScene/")
public class LabelSceneController extends BaseController
{
    @Autowired
    private LabelSceneService labelSceneService;

    @Autowired
    private TokenService tokenService;

    /**
     * 获取项目列表
     */
    //@PreAuthorize("@ss.hasPermi('business:labelScene:list')")
    @GetMapping("/list")
    public TableDataInfo list(SceneEntity labelScene)
    {
        startPage();
        List<SceneEntity> list = labelSceneService.selectLabelSceneList(labelScene);
        return getDataTable(list);
    }

    @Transactional
    /**
     * 新增场景
     */
    //@PreAuthorize("@ss.hasPermi('business:labelScene:add')")
    @Log(title = "场景新增", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SceneEntity labelScene, HttpServletRequest request)
    {
        LoginUser loginUser= tokenService.getLoginUser(request);
        labelScene.setCreateBy(loginUser.getUsername());
        return toAjax(labelSceneService.insertLabelScene(labelScene));
    }

    /**
     * 修改项目
     */
    @PreAuthorize("@ss.hasPermi('business:labelScene:edit')")
    @Log(title = "项目管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SceneEntity labelScene)
    {

        return toAjax(labelSceneService.updateLabelScene(labelScene));
    }

    /**
     * 删除项目
     */
    @PreAuthorize("@ss.hasPermi('business:labelScene:remove')")
    @Log(title = "项目管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] projectIds)
    {
        return toAjax(labelSceneService.deleteLabelSceneByIds(projectIds));
    }


}
