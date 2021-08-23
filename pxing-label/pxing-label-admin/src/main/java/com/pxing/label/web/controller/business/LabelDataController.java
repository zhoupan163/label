package com.pxing.label.web.controller.business;



import com.pxing.label.business.dataTransformation.LabelAnnotionToMott;
import com.pxing.label.business.domain.vo.LabelTaskImageVo;
import com.pxing.label.business.service.LabelTaskService;
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
    private LabelTaskService labelTaskService;

    @GetMapping("/getLabelTaskZip/")
    @ApiOperation(value = "下载压缩包")
    public void getZip(@RequestParam("taskName") String taskName, HttpServletResponse response) throws Exception {
        List<LabelTaskImageVo> list=labelTaskService.getFinishedImageList(taskName);
        Map<String, InputStream> map=new HashMap<>();
        Map<String,Set<String>> streamMap= new HashMap<>();
        Set<String> result = new HashSet<>();

        for(LabelTaskImageVo labelTaskImageVo: list){
             List<String> list1= LabelAnnotionToMott.getMott(labelTaskImageVo);
             String ids= list1.get(1);
             List<String> idList= Arrays.asList(ids.split(" "));
             String streamId=labelTaskImageVo.getStream_id();
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
             InputStream png= HttpUtils.getInputStream(labelTaskImageVo.getPng_url());

             map.put("motTxt/"+ labelTaskImageVo.getStream_id()+ "/"+ labelTaskImageVo.getImage_id()+".txt",  txt);
             map.put("png/"+ labelTaskImageVo.getStream_id() +"/" + labelTaskImageVo.getImage_id()+ ".png", png);
        };
        for(String streamId : streamMap.keySet()){
            map.put("motTxt/"+ streamId+ "/"+ "idCount.txt",  new ByteArrayInputStream(String.valueOf(streamMap.get(streamId).size())
                    .getBytes(StandardCharsets.UTF_8)));
        }
        ZipUtils.doZip("demo.zip",map,response);
    }

}
