package com.pxing.label.web.controller.business;



import com.pxing.label.business.dao.LabelStreamDao;
import com.pxing.label.business.dataTransformation.LabelAnnotionToMott;
import com.pxing.label.business.domain.entity.TaskImageEntity;
import com.pxing.label.business.domain.entity.TaskStreamEntity;
import com.pxing.label.business.domain.vo.LabelStreamVo;
import com.pxing.label.business.domain.vo.LabelTaskImageVo;
import com.pxing.label.business.service.LabelStreamService;
import com.pxing.label.business.service.LabelTaskService;
import com.pxing.label.business.service.TaskImageService;
import com.pxing.label.business.service.TaskStreamService;
import com.pxing.label.common.core.controller.BaseController;
import com.pxing.label.common.utils.file.ZipUtils;
import com.pxing.label.common.utils.http.HttpUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 角色信息
 *
 * @author pxing 官方网址：www.pxing.vip
 */
@RestController
@RequestMapping("/business/labelData")
public class LabelDataController extends BaseController
{



    @Autowired
    private TaskStreamService taskStreamService;

    @Autowired
    private TaskImageService taskImageService;

    @GetMapping("/getLabelTaskZip/")
    @ApiOperation(value = "下载压缩包")
    public void getZip(@RequestParam("taskId") Long taskId, HttpServletResponse response) throws Exception {

        List<TaskStreamEntity> taskStreamEntityList= taskStreamService.getFinishedTaskStream(taskId);
        List<Long> streamIdList= taskStreamEntityList.stream().map(TaskStreamEntity::getStreamId).collect(Collectors.toList());
         //图片
        List<TaskImageEntity> list= taskImageService.getFinishedImageList(taskId, streamIdList);
        Map<String, InputStream> map=new HashMap<>();
        Map<String,Set<String>> streamMap= new HashMap<>();
        Set<String> result = new HashSet<>();

        for(TaskImageEntity taskImageEntity: list){
             List<String> list1= LabelAnnotionToMott.getMott(taskImageEntity);
             String ids= list1.get(1);
             List<String> idList= Arrays.asList(ids.split(" "));
             String streamId= String.valueOf(taskImageEntity.getStream_id());
             Set<String> idSet= idList.stream().collect(Collectors.toSet());

             if (!streamMap.containsKey(streamId)){
                 streamMap.put(streamId, idSet);
             }else{
                 Set<String> oldSet= streamMap.get(streamId);
                 //idSet.addAll(oldSet);
                 result.clear();
                 result.addAll(oldSet);
                 result.addAll(idSet);
                 streamMap.put(streamId, result);
             };
             InputStream txt= new ByteArrayInputStream(list1.get(0).getBytes(StandardCharsets.UTF_8));
             InputStream png= HttpUtils.getInputStream(taskImageEntity.getPng_url());

             map.put("motTxt/"+ taskImageEntity.getStream_id()+ "/"+ taskImageEntity.getImageId()+".txt",  txt);
             map.put("png/"+ taskImageEntity.getStream_id() +"/" + taskImageEntity.getImageId()+ ".png", png);
        };
        for(String streamId : streamMap.keySet()){
            map.put("motTxt/"+ streamId+ "/"+ "idCount.txt",  new ByteArrayInputStream(String.valueOf(streamMap.get(streamId).size())
                    .getBytes(StandardCharsets.UTF_8)));
        }
        ZipUtils.doZip("demo.zip",map,response);
    }

}
