package com.pxing.label.web.controller.business;


import com.alibaba.fastjson.JSON;
import com.pxing.label.business.domain.vo.LabelViaProjectVo;
import com.pxing.label.business.service.LabelTaskService;
import com.pxing.label.business.service.LabelViaService;
import com.pxing.label.common.core.controller.BaseController;
import com.pxing.label.common.core.domain.model.LoginUser;
import com.pxing.label.common.core.page.TableDataInfo;
import com.pxing.label.framework.web.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Update;
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
@RequestMapping("/business/labelVia")
public class LabelViaController extends BaseController
{
    @Autowired
    private LabelViaService labelViaService;

    @Autowired
    private LabelTaskService labelTaskService;

    @Autowired
    private TokenService tokenService;

    @GetMapping("/getTaskViaInfo")
    @ResponseBody
    public TableDataInfo getStreamViaInfo(@RequestParam("taskName")String taskName, HttpServletRequest request, @RequestParam("type")String type)
    {
        startPage();
        LoginUser loginUser= tokenService.getLoginUser(request);
        String  userName=loginUser.getUser().getUserName();
        List<LabelViaProjectVo> list = labelViaService.getSreamViaProject(taskName, userName, type);
        return getDataTable(list);
    }

    @PostMapping("/updateViaInfo")
    @ResponseBody
    public TableDataInfo updateTaskViaInfo(@RequestBody String params, HttpServletRequest request)
    {
        startPage();
        LoginUser loginUser= tokenService.getLoginUser(request);
        LabelViaProjectVo labelViaProjectVo= JSON.parseObject(params,LabelViaProjectVo.class);
        List<Update> list= labelTaskService.updateLabelTaskImages(labelViaProjectVo);
        return getDataTable(new ArrayList<>());
    }

    @PostMapping("/commitViaInfo")
    @ResponseBody
    //@RequestBody JSONObject params
    public TableDataInfo commitTaskImage(@RequestBody String params)
    {
        startPage();
        LabelViaProjectVo labelViaProjectVo= JSON.parseObject(params,LabelViaProjectVo.class);
        labelTaskService.commitTaskImage(labelViaProjectVo);
        return getDataTable(new ArrayList<>());
    }



}
