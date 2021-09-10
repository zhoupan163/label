package com.pxing.label.web.controller.business;


import com.mongodb.client.result.UpdateResult;
import com.pxing.label.business.dataTransformation.LabelAnnotionToMott;
import com.pxing.label.business.domain.vo.LabelImageCheck;
import com.pxing.label.business.domain.vo.LabelStreamVo;
import com.pxing.label.business.domain.vo.LabelTaskImageVo;
import com.pxing.label.business.service.LabelCheckService;
import com.pxing.label.business.service.LabelStreamService;
import com.pxing.label.business.service.LabelTaskService;
import com.pxing.label.common.core.controller.BaseController;
import com.pxing.label.common.core.domain.AjaxResult;
import com.pxing.label.common.core.domain.model.LoginUser;
import com.pxing.label.common.core.page.TableDataInfo;
import com.pxing.label.common.utils.file.ZipUtils;
import com.pxing.label.common.utils.http.HttpUtils;
import com.pxing.label.framework.web.service.TokenService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 标注审核
 *
 * @author pxing 官方网址：www.pxing.com
 */
@RestController
@RequestMapping("/business/labelCheck")
public class LabelCheckController extends BaseController
{

    @Autowired
    private TokenService tokenService;

    @Autowired
    private LabelCheckService labelCheckService;

    @Autowired
    private  LabelTaskService labelTaskService;

    @Autowired
    private LabelStreamService labelStreamService;

    // 审核操作
    @PostMapping("/qa")
    @ResponseBody
    public AjaxResult qa(@RequestBody LabelImageCheck labelImageCheck)
    {
        return toAjax(labelCheckService.qa(labelImageCheck));
    }
}
