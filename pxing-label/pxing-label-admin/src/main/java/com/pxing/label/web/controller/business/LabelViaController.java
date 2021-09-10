package com.pxing.label.web.controller.business;


import com.alibaba.fastjson.JSON;
import com.pxing.label.business.domain.vo.LabelStreamVo;
import com.pxing.label.business.domain.vo.LabelViaProjectVo;
import com.pxing.label.business.service.LabelTaskService;
import com.pxing.label.business.service.LabelViaService;
import com.pxing.label.business.service.TaskImageService;
import com.pxing.label.common.core.controller.BaseController;
import com.pxing.label.common.core.domain.AjaxResult;
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

    @Autowired
    private TaskImageService taskImageService;

    @GetMapping("/getTaskViaInfo")
    @ResponseBody
    public TableDataInfo getStreamViaInfo(@RequestParam("taskId")Long taskId, HttpServletRequest request,
                                          @RequestParam("type")String type ,@RequestParam("streamId")Long streamId)
    {
        startPage();
        LoginUser loginUser= tokenService.getLoginUser(request);
        String  userName=loginUser.getUser().getUserName();
        List<LabelViaProjectVo> list = labelViaService.getSreamViaProject(taskId, streamId, userName, type);
        return getDataTable(list);
    }

    @PostMapping("/updateViaInfo")
    @ResponseBody
    public AjaxResult updateViaInfo(@RequestBody String params)
    {
        startPage();
        LabelViaProjectVo labelViaProjectVo= JSON.parseObject(params,LabelViaProjectVo.class);
        return toAjax(labelViaService.updateViaInfo(labelViaProjectVo));
    }

    @PostMapping("/commitViaInfo")
    @ResponseBody
    //@RequestBody JSONObject params
    public AjaxResult  commitViaInfo(@RequestBody String params)
    {
        startPage();
        LabelViaProjectVo labelViaProjectVo= JSON.parseObject(params,LabelViaProjectVo.class);
        return toAjax(labelViaService.commitViaInfo(labelViaProjectVo));
    }

    @GetMapping("discardImg")
    @ResponseBody
    public  AjaxResult discardImg(@RequestParam("taskId")Long taskId, @RequestParam("streamId")Long streamId,
                                  @RequestParam("jpgUrl")String jpg_url){
        int res= taskImageService.discardImage(taskId, streamId, jpg_url);
        return toAjax(res);

    }



}
