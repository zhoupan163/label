package com.pxing.label.web.controller.business;


import com.mongodb.client.result.UpdateResult;
import com.pxing.label.business.domain.entity.LabelTaskEntity;
import com.pxing.label.business.domain.entity.TaskStreamEntity;
import com.pxing.label.business.domain.vo.LabelStreamVo;
import com.pxing.label.business.domain.vo.LabelTaskVo;
import com.pxing.label.business.service.*;
import com.pxing.label.common.annotation.Log;
import com.pxing.label.common.core.controller.BaseController;
import com.pxing.label.common.core.domain.AjaxResult;
import com.pxing.label.common.core.domain.model.LoginUser;
import com.pxing.label.common.core.page.TableDataInfo;
import com.pxing.label.common.enums.BusinessType;
import com.pxing.label.framework.web.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息
 *
 * @author pxing 官方网址：www.pxing.vip
 */
@RestController
@RequestMapping("/business/taskStream")
public class TaskStreamController extends BaseController{
    @Autowired
    private  TokenService tokenService;

    @Autowired
    private TaskStreamService taskStreamService;

    @Autowired
    private TaskImageService taskImageService;
    @Log(title = "增加task_stream", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TaskStreamEntity taskStreamEntity, HttpServletRequest request)
    {
        LoginUser loginUser= tokenService.getLoginUser(request);
        taskStreamEntity.setCreateBy(loginUser.getUsername());

        taskImageService.addTaskImages(taskStreamEntity.getStreamId(), taskStreamEntity.getTaskId());
        return toAjax(taskStreamService.insertTaskStream(taskStreamEntity));
    }

    //获取已分配未标注完成或者被驳回的stream
    @GetMapping("/getkUnfinishedTaskStreamList")
    @ResponseBody
    public TableDataInfo getLabelTaskUnfinishedStream(@RequestParam("taskId")Long taskId, @RequestParam("type")String type,
                                                      HttpServletRequest request)
    {
        startPage();
        LoginUser loginUser= tokenService.getLoginUser(request);
        String  userName=loginUser.getUser().getUserName();
        List<TaskStreamEntity> list= taskStreamService.getTaskStream(taskId, type, userName);
        return getDataTable(list);
    }

    //获取未分配stream
    @GetMapping("/getUnAssignedTaskStreamList")
    @ResponseBody
    public TableDataInfo getUnAssignedTaskStream(@RequestParam("taskId")Long taskId, @RequestParam("type")String type)
    {
        startPage();
        List<TaskStreamEntity> list= taskStreamService.getTaskStream(taskId, type, "");
        return getDataTable(list);
    }


    @PutMapping("assignTaskStream")
    public AjaxResult assignTaskStream(String streamId, String taskId, String type, HttpServletRequest request)
    {
        LoginUser loginUser= tokenService.getLoginUser(request);
        String  userName=loginUser.getUser().getUserName();

        return toAjax(taskStreamService.assignTaskStream(streamId, taskId, type, userName));
    }


}