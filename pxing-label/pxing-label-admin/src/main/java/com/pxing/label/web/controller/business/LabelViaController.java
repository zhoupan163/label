package com.pxing.label.web.controller.business;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.result.UpdateResult;
import com.pxing.label.business.domain.vo.LabelTaskStreamVo;
import com.pxing.label.business.domain.vo.LabelTaskViaVo;
import com.pxing.label.business.domain.vo.LabelViaProjectVo;
import com.pxing.label.business.service.LabelViaService;
import com.pxing.label.common.core.controller.BaseController;
import com.pxing.label.common.core.domain.model.LoginUser;
import com.pxing.label.common.core.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
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
    //@RequestBody JSONObject params
    public TableDataInfo getTaskViaInfo(@RequestBody String params)
    {
        startPage();

        LabelViaProjectVo labelViaProjectVo= JSON.parseObject(params,LabelViaProjectVo.class);
        List<LabelViaProjectVo> list = labelViaService.updateSreamViaProject(labelViaProjectVo);
        return getDataTable(new ArrayList<>());
    }



}
