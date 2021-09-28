package com.pxing.label.web.controller.business;


import com.pxing.label.business.domain.entity.TaskDetailEntity;
import com.pxing.label.business.domain.entity.TaskEntity;
import com.pxing.label.business.domain.vo.LabelTaskVo;
import com.pxing.label.business.service.*;
import com.pxing.label.common.annotation.Log;
import com.pxing.label.common.core.controller.BaseController;
import com.pxing.label.common.core.domain.AjaxResult;
import com.pxing.label.common.core.domain.model.LoginUser;
import com.pxing.label.common.core.page.TableDataInfo;
import com.pxing.label.common.enums.BusinessType;
import com.pxing.label.framework.web.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(LabelTaskController.class);

    @Autowired
    private LabelTaskService labelTaskService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private TaskDetailService taskDetailService;

    @Autowired
    private TaskImageService taskImageService;

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
    @PostMapping()
    public AjaxResult add(@Validated @RequestBody TaskEntity taskEntity, HttpServletRequest request)
    {
        LoginUser loginUser= tokenService.getLoginUser(request);
        taskEntity.setCreateBy(loginUser.getUsername());
        return toAjax(labelTaskService.insertTaskEntity(taskEntity));
    }

    /**
     * 检查是否有已拉取未完成的图片任务
     */
    //@PreAuthorize("@ss.hasPermi('business:labelTag:add')")
    @GetMapping("checkTaskImage")
    @ResponseBody
    public AjaxResult checkTaskImage(@RequestParam("taskName") String taskName,@RequestParam("type") String type, HttpServletRequest request)
    {
        LoginUser loginUser= tokenService.getLoginUser(request);
        int a= taskImageService.checkTaskImage(taskName, type ,loginUser.getUsername());
        logger.info("当前用户:{} 存在：{} 图片任务数量{}",loginUser.getUsername(), type, a);
        return AjaxResult.success(a);
    }

    /**
     * 获取任务详细信息
     */
    //@PreAuthorize("@ss.hasPermi('business:labelTag:add')")
    @GetMapping("getTaskDetail")
    @ResponseBody
    public AjaxResult getTaskDetail(@RequestParam("taskName") String taskName, HttpServletRequest request)
    {
        TaskDetailEntity taskDetailEntity= taskDetailService.getTaskDetail(taskName);
        logger.info(taskDetailEntity.toString());
        return AjaxResult.success(taskDetailEntity);
    }

    @Transactional
    /**
     * 拉取图片标注
     */
    //@PreAuthorize("@ss.hasPermi('business:labelTag:add')")
    @PostMapping("pullTaskImage")
    public AjaxResult pullTaskImage(String taskName,String type, Integer number, HttpServletRequest request)
    {
        //labelTaskService.pullTaskImage(taskName, number);
        LoginUser loginUser= tokenService.getLoginUser(request);
        logger.info("user{ } pull task:{} image:{} count:{} for: {}",loginUser.getUsername(), taskName, number , type);
        return toAjax(labelTaskService.pullTaskImage(taskName, type, number, loginUser.getUsername()));
    }


}
