package com.pxing.label.web.controller.business;


import com.mongodb.client.result.UpdateResult;
import com.pxing.label.business.dataTransformation.LabelAnnotionToMott;
import com.pxing.label.business.domain.vo.LabelStreamVo;
import com.pxing.label.business.domain.vo.LabelTaskImageVo;
import com.pxing.label.business.service.LabelCheckService;
import com.pxing.label.business.service.LabelTaskService;
import com.pxing.label.common.core.controller.BaseController;
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

    //获取已分配未审核完成的stream
    @GetMapping("/getUnFinishedCheckedVideoStream")
    @ResponseBody
    public TableDataInfo getUnFinishedCheckedVideoStream(@RequestParam("taskName")String taskName, @RequestParam("token")String token, @RequestParam("qa_level")String qa_level)
    {
        startPage();
        LoginUser loginUser= tokenService.getLoginUser(token);
        String  userName=loginUser.getUser().getUserName();
        List<LabelTaskImageVo> list=labelCheckService.getUnFinishedCheckedImage(taskName, userName, qa_level);
        return getDataTable(list);
    }

    // 查询已经标注完成却没有开始审核的stream
    @GetMapping("/getUnCheckedStream")
    @ResponseBody
    public TableDataInfo getUnCheckedStream(@RequestParam("taskName")String taskName, @RequestParam("qa_level")String qa_level)
    {
        startPage();
        Query query=Query.query(Criteria.where("task_name").is(taskName).and("image_status").is(qa_level));
        List<LabelStreamVo> list=labelTaskService.getLabelTaskStream(taskName, query);
        return getDataTable(list);
    }

    // 审核操作
    @GetMapping("/qa")
    @ResponseBody
    public TableDataInfo qa(@RequestParam("qaType")String qaType, @RequestParam("streamId")String streamId, @RequestParam("imgUrl")String imgUrl,
                            @RequestParam("qaComment")String qaComment, @RequestParam("operation")String operation)
    {
        startPage();
        List<UpdateResult> updateResults= labelCheckService.qa(qaType, streamId, imgUrl, qaComment, operation);
        return getDataTable(updateResults);
    }
}
