package com.pxing.label.web.controller.business;

import com.pxing.label.business.domain.entity.ImageEntity;
import com.pxing.label.business.domain.entity.LabelTagEntity;
import com.pxing.label.business.domain.entity.StreamEntity;
import com.pxing.label.business.domain.vo.LabelTagVo;
import com.pxing.label.business.domain.vo.LabelTaskImageVo;
import com.pxing.label.business.domain.vo.StreamTagVo;
import com.pxing.label.business.service.*;
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
import java.util.ArrayList;
import java.util.Arrays;
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
    private StreamService streamService;

    @Autowired
    private LabelTaskService labelTaskService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private LabelTagService labelTagService;

    @Autowired
    private StreamTagService streamTagService;

    @Autowired
    private ProjectStreamService projectStreamService;

    /**
     * 获取image列表
     */
    //@PreAuthorize("@ss.hasPermi('business:labelTag:list')")
    @GetMapping("/selectUnTaggedImageList")
    public TableDataInfo list(@RequestParam("streamId")String streamId)
    {
        startPage();
        List<LabelTaskImageVo> list = labelTaskService.selectUnTaggedImageList(streamId);
        return getDataTable(list);
    }

    /**
     * 获取stream列表
     */
    //@PreAuthorize("@ss.hasPermi('business:labelTag:list')")
    @GetMapping("/selectStreamList")
    public TableDataInfo selectStreamList()
    {
        startPage();
        List<StreamEntity> list = streamService.selectStreamList();
        return getDataTable(list);
    }

    /**
     * 获取 stream对应 image标记列表
     */
    //@PreAuthorize("@ss.hasPermi('business:labelTag:list')")
    @GetMapping("/selectImageListByStreamId")
    public TableDataInfo selectImageListByStreamId(@RequestParam("streamId")Long streamId)
    {
        startPage();
        List<ImageEntity> list =  imageService.selectImageListByStreamId(streamId);
        return getDataTable(list);
    }

    /**
     * 获取 project对应 的tag标记列表
     */
    //@PreAuthorize("@ss.hasPermi('business:labelTag:list')")
    @GetMapping("/selectTagListByProjectId")
    public TableDataInfo selectTagListByProjectId(@RequestParam("projectId")Long projectId)
    {
        startPage();
        List<LabelTagVo> list = labelTagService.selectTagListByProjectId(projectId);
        return getDataTable(list);
    }

    /**
     * tag stream
     */
    //@PreAuthorize("@ss.hasPermi('business:labelTag:list')")
    @PutMapping("/tagStream")
    public AjaxResult tagStream(Long streamId, Long projectId, Long[] tagIds)
    {
        streamTagService.tagStreamList(streamId, Arrays.asList(tagIds));
        projectStreamService.insertProjectStream(streamId, projectId);
        return toAjax(streamService.updateTagStatusById(streamId));
    }

    /**
     * 获取已经标记的stream记录
     */
    //@PreAuthorize("@ss.hasPermi('business:labelTag:list')")
    @GetMapping("/getTaggedStreamList")
    @ResponseBody
    public TableDataInfo getTaggedStreamList(@RequestParam("projectId")Long projectId, @RequestParam("taskId")Long taskId)
    {
        startPage();
        List<StreamTagVo> list = streamTagService.getTaggedStreamList(projectId, taskId);
        return getDataTable(list);
    }

}
