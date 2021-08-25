package com.pxing.label.web.controller.business;


import com.mongodb.client.result.UpdateResult;
import com.pxing.label.business.domain.vo.LabelTaskImageVo;
import com.pxing.label.business.domain.vo.LabelStreamVo;
import com.pxing.label.business.domain.vo.LabelTaskVo;
import com.pxing.label.business.service.LabelStreamService;
import com.pxing.label.business.service.LabelTaskService;
import com.pxing.label.business.service.LabelViaService;
import com.pxing.label.common.core.controller.BaseController;
import com.pxing.label.common.core.domain.model.LoginUser;
import com.pxing.label.common.core.page.TableDataInfo;
import com.pxing.label.framework.web.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @Autowired
    private LabelViaService labelViaService;

    @Autowired
    private LabelStreamService labelStreamService;


    @PreAuthorize("@ss.hasPermi('business:labelTask:list')")
    @GetMapping("/list")
    public TableDataInfo list(LabelTaskVo labelTaskVo)
    {
        startPage();
        List<LabelTaskVo> list = labelTaskService.selectLabelTaskList(labelTaskVo);
        return getDataTable(list);
    }

    //获取已分配未标注完成的stream
    @GetMapping("/getLabelTaskUnfinishedStream")
    @ResponseBody
    public TableDataInfo getLabelTaskUnfinishedStream(@RequestParam("taskName")String taskName, @RequestParam("token")String token)
    {
        startPage();
        LoginUser loginUser= tokenService.getLoginUser(token);
        String  userName=loginUser.getUser().getUserName();
        List<LabelTaskImageVo> list=labelTaskService.getLabelTaskUnfinishedStream(taskName,userName);
        //List<LabelStreamVo> list=labelStreamService.getLabelTaskUnfinishedStream(taskName,userName);
        return getDataTable(list);
    }

    //获取未分配的stream
    @GetMapping("/getLabelTaskStream")
    @ResponseBody
    public TableDataInfo getLabelTaskStream(@RequestParam("taskName")String taskName )
    {
        startPage();
        List<LabelStreamVo> list=labelTaskService.getLabelTaskStream(taskName);
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
