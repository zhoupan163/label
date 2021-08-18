package com.pxing.label.web.controller.business;


import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.result.UpdateResult;
import com.pxing.label.business.domain.vo.LabelTaskImageVo;
import com.pxing.label.business.domain.vo.LabelTaskStreamVo;
import com.pxing.label.business.domain.vo.LabelTaskViaVo;
import com.pxing.label.business.domain.vo.LabelTaskVo;
import com.pxing.label.business.service.LabelTaskService;
import com.pxing.label.business.service.LabelViaService;
import com.pxing.label.common.core.controller.BaseController;
import com.pxing.label.common.core.domain.model.LoginUser;
import com.pxing.label.common.core.page.TableDataInfo;
import com.pxing.label.framework.web.service.TokenService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private LabelViaService labelViaService;


    @PreAuthorize("@ss.hasPermi('business:labelTask:list')")
    @GetMapping("/list")
    public TableDataInfo list(LabelTaskVo labelTaskVo)
    {
        startPage();
        List<LabelTaskVo> list = labelTaskService.selectLabelTaskList(labelTaskVo);
        return getDataTable(list);
    }


    @GetMapping("/viaInfo")
    @ResponseBody
    public TableDataInfo getTaskViaInfo(String taskId)
    {
        startPage();
        List<LabelTaskViaVo> list = labelTaskService.selectLabelTaskViaInfo(taskId);
        return getDataTable(list);
    }

    @PostMapping("/viaInfoUpdate")
    @ResponseBody
    public TableDataInfo updateTaskViaInfo(@RequestBody LabelTaskViaVo labelTaskViaVo)
    {
        startPage();
        UpdateResult updateResult= labelTaskService.updateLabelTaskViaInfo(labelTaskViaVo);
        List<UpdateResult> list= new ArrayList<>();
        list.add(updateResult);
        return getDataTable(list);
    }

    @PutMapping("/viaInfoInsert")
    @ResponseBody
    public TableDataInfo insertTaskViaInfo(LabelTaskViaVo labelTaskViaVo)
    {
        startPage();
        labelTaskService.insertLabelTaskViaInfo(labelTaskViaVo);
        List<UpdateResult> list= new ArrayList<>();
        return getDataTable(list);
    }

    @GetMapping("/assigningTask")
    @ResponseBody
    public TableDataInfo assigningTask(@RequestParam("taskId")String taskId, @RequestParam("token")String token)
    {
        startPage();
        LoginUser loginUser= tokenService.getLoginUser(token);
        String  labelBy=loginUser.getUser().getUserName();
        labelTaskService.assiginTask(taskId,labelBy);
        List<UpdateResult> list= new ArrayList<>();
        return getDataTable(list);
    }

    @GetMapping("/labelTask")
    @ResponseBody
    public TableDataInfo labelTask(@RequestParam("taskId")String taskId ,@RequestParam("token")String token)
    {
        startPage();
        LoginUser loginUser= tokenService.getLoginUser(token);
        String  userName=loginUser.getUser().getUserName();
        labelTaskService.labelTask(taskId,userName);
        List<UpdateResult> list= new ArrayList<>();
        return getDataTable(list);
    }

    //获取未分配的stream
    @GetMapping("/getLabelTaskStream")
    @ResponseBody
    public TableDataInfo getLabelTaskStream(@RequestParam("taskName")String taskName )
    {
        startPage();
        List<LabelTaskStreamVo> list=labelTaskService.getLabelTaskStream(taskName);
        return getDataTable(list);
    }

    //分配stream
    @GetMapping("/assignLabelTaskStream")
    @ResponseBody
    public TableDataInfo assignLabelTaskStream(@RequestParam("streamId")String streamId,@RequestParam("token")String token, @RequestParam("taskName")String taskName)
    {
        startPage();
        LoginUser loginUser= tokenService.getLoginUser(token);
        String  userName=loginUser.getUser().getUserName();
        labelTaskService.assignLabelTaskStream(streamId, userName);
        labelViaService.insertSreamViaProject(streamId, taskName);
        List<UpdateResult> list= new ArrayList<>();
        return getDataTable(list);
    }


}
