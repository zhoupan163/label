package com.pxing.label.web.controller.business;


import com.alibaba.fastjson.JSON;
import com.pxing.label.business.domain.vo.LabelViaProjectVo;
import com.pxing.label.business.service.LabelTaskService;
import com.pxing.label.business.service.LabelViaService;
import com.pxing.label.common.core.controller.BaseController;
import com.pxing.label.common.core.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息
 *
 * @author pxing 官方网址：www.pxing.vip
 */
@RestController
@RequestMapping("/business/labelVia")
public class LabelViaController extends BaseController
{
    @Autowired
    private LabelViaService labelViaService;

    @Autowired
    private LabelTaskService labelTaskService;

    @GetMapping("/getStreamViaInfo")
    @ResponseBody
    public TableDataInfo getStreamViaInfo(@RequestParam("streamId")String streamId)
    {
        startPage();
        List<LabelViaProjectVo> list = labelViaService.getSreamViaProject(streamId);
        return getDataTable(list);
    }

    @PostMapping("/updateStreamViaInfo")
    @ResponseBody
    public TableDataInfo updateTaskViaInfo(@RequestBody String params)
    {
        startPage();
        LabelViaProjectVo labelViaProjectVo= JSON.parseObject(params,LabelViaProjectVo.class);
        List<Update> list1= labelTaskService.updateLabelTaskImages(labelViaProjectVo);
        List<LabelViaProjectVo> list = labelViaService.updateSreamViaProject(labelViaProjectVo);
        //labelTaskService.changeStreamStatus(labelViaProjectVo);
        return getDataTable(new ArrayList<>());
    }

    @PostMapping("/commitStreamViaInfo")
    @ResponseBody
    //@RequestBody JSONObject params
    public TableDataInfo commitStreamVia(@RequestBody String params)
    {
        startPage();
        LabelViaProjectVo labelViaProjectVo= JSON.parseObject(params,LabelViaProjectVo.class);
        List<LabelViaProjectVo> list = labelViaService.updateSreamViaProject(labelViaProjectVo);
        labelTaskService.changeStreamStatus(labelViaProjectVo);
        return getDataTable(new ArrayList<>());
    }



}
