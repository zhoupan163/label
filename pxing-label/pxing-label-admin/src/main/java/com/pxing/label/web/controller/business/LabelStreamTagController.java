package com.pxing.label.web.controller.business;

import com.pxing.label.business.domain.entity.LabelTagEntity;
import com.pxing.label.business.domain.vo.LabelTaskImageVo;
import com.pxing.label.business.service.LabelTagService;
import com.pxing.label.business.service.LabelTaskService;
import com.pxing.label.common.annotation.Log;
import com.pxing.label.common.core.controller.BaseController;
import com.pxing.label.common.core.domain.AjaxResult;
import com.pxing.label.common.core.domain.model.LoginUser;
import com.pxing.label.common.core.page.TableDataInfo;
import com.pxing.label.common.enums.BusinessType;
import com.pxing.label.framework.web.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/business/labelStreamTag/")
public class LabelStreamTagController extends BaseController
{
    @Autowired
    private LabelTagService labelTagService;

    @Autowired
    private LabelTaskService labelTaskService;

    /**
     * 获取stream列表
     */
    //@PreAuthorize("@ss.hasPermi('business:labelTag:list')")
    @GetMapping("/selectUnTaggedImageList")
    public TableDataInfo list(@RequestParam("streamId")String streamId)
    {
        startPage();
        List<LabelTaskImageVo> list = labelTaskService.selectUnTaggedImageList(streamId);
        return getDataTable(list);
    }
}
