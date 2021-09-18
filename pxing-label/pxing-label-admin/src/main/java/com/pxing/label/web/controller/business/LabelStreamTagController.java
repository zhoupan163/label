package com.pxing.label.web.controller.business;

import com.pxing.label.business.domain.entity.*;
import com.pxing.label.business.domain.vo.LabelTagVo;
import com.pxing.label.business.domain.vo.StreamTagVo;
import com.pxing.label.business.service.*;
import com.pxing.label.common.core.controller.BaseController;
import com.pxing.label.common.core.domain.AjaxResult;
import com.pxing.label.common.core.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        //List<LabelTaskImageVo> list = labelTaskService.selectUnTaggedImageList(streamId);
        return getDataTable(new ArrayList<>());
    }

    /**
     * 获取stream列表
     */
    //@PreAuthorize("@ss.hasPermi('business:labelTag:list')")
    @GetMapping("/selectStreamList")
    public TableDataInfo selectStreamList()
    {
        startPage();
        List<ProjectStreamEntity> list = streamService.selectStreamList();
        return getDataTable(list);
    }

    /**
     * 获取 stream对应 image标记列表
     */
    //@PreAuthorize("@ss.hasPermi('business:labelTag:list')")
    @GetMapping("/selectImageListByStreamId")
    public TableDataInfo selectImageListByStreamId(@RequestParam("streamId")String streamId)
    {
        startPage();
        List<ImageEntity> list =  imageService.selectImageListByStreamId(streamId);
        return getDataTable(list);
    }

    /**
     * 获取 project对应 的tag标记列表
     */
    //@PreAuthorize("@ss.hasPermi('business:labelTag:list')")
    @GetMapping("/selectTagListByProjectName")
    public TableDataInfo selectTagListByProjectId(@RequestParam("projectName")String projectName)
    {
        startPage();
        List<TagEntity> list = labelTagService.selectTagListByProjectName(projectName);
        return getDataTable(list);
    }

    /**
     * tag stream
     */
    //@PreAuthorize("@ss.hasPermi('business:labelTag:list')")
    @PutMapping("/updateTagStream")
    public AjaxResult updateTagStream(String streamId, Long[] tagIds)
    {
        return toAjax(streamTagService.updateTagStream(streamId, Arrays.asList(tagIds)));
    }

    /**
     * tag stream
     */
    //@PreAuthorize("@ss.hasPermi('business:labelTag:list')")
    @PutMapping("/tagStream")
    public AjaxResult tagStream(String streamId, Long[] tagIds)
    {
        streamTagService.tagStreamList(streamId, Arrays.asList(tagIds));
        return toAjax(streamService.updateTagStatusById(streamId));
    }

    /**
     * 获取已经标记的stream 而未筛选的 记录
     */
    //@PreAuthorize("@ss.hasPermi('business:labelTag:list')")
    @GetMapping("/getTaggedStreamList")
    @ResponseBody
    public TableDataInfo getTaggedStreamList(@RequestParam("projectName")String projectName, @RequestParam("taskName")String taskName)
    {
        startPage();
        List<ProjectStreamEntity> list = streamTagService.getTaggedStreamList(projectName, taskName);
        return getDataTable(list);
    }

    /**
     * 获取已经标记的stream的list列表 而未筛选的 记录
     */
    //@PreAuthorize("@ss.hasPermi('business:labelTag:list')")
    @GetMapping("/selectTagListByStreamId")
    @ResponseBody
    public TableDataInfo getTagListBystream(@RequestParam("streamId") String streamId)
    {
        startPage();
        List<StreamTagEntity> list = streamTagService.getTagListBystream(streamId);
        return getDataTable(list);
    }

}
