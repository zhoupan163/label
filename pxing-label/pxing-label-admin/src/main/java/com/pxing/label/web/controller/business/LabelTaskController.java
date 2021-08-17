package com.pxing.label.web.controller.business;


import com.pxing.label.business.domain.vo.LabelTaskViaVo;
import com.pxing.label.business.domain.vo.LabelTaskVo;
import com.pxing.label.business.service.LabelTaskService;
import com.pxing.label.common.core.controller.BaseController;
import com.pxing.label.common.core.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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


    @PreAuthorize("@ss.hasPermi('business:labelTask:list')")
    @GetMapping("/list")
    public TableDataInfo list(LabelTaskVo labelTaskVo)
    {
        startPage();
        List<LabelTaskVo> list = labelTaskService.selectLabelTaskList(labelTaskVo);
        labelTaskService.selectLabelTaskViaInfo("1000");
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

}
