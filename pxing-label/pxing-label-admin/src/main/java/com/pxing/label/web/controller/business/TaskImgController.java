package com.pxing.label.web.controller.business;

import com.pxing.label.business.domain.dto.ProjectStreamDto;
import com.pxing.label.business.domain.dto.TaskImgDto;
import com.pxing.label.business.domain.entity.*;
import com.pxing.label.business.service.*;
import com.pxing.label.common.core.controller.BaseController;
import com.pxing.label.common.core.domain.AjaxResult;
import com.pxing.label.common.core.page.TableDataInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 项目信息
 *
 * @author pxing 官方网址：www.pxing.vip
 */
@RestController
@RequestMapping("/business/taskImg/")
public class TaskImgController extends BaseController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskImgController.class);

    @Autowired
    private TaskImageService taskImageService;

    /**
     * 获取stream列表
     */
    //@PreAuthorize("@ss.hasPermi('business:labelTag:list')")
    @PostMapping("list")
    public TableDataInfo selectTaskImgList(@RequestBody TaskImgDto taskImgDto)
    {
        startPage();
        Map<String, Object> map= taskImageService.getTaskImageEntityList(taskImgDto);
        List<TaskImageEntity> list = (List<TaskImageEntity>) map.get("list");
        Long total= (Long)map.get("total");
        return getDataTable(list, total);
    }
}
