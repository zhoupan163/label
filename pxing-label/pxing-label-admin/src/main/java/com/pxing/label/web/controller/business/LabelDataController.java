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
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
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
public class LabelDataController extends BaseController {


    @Autowired
    private TaskStreamService taskStreamService;

    @Autowired
    private TaskImageService taskImageService;

    @GetMapping("/getLabelTaskZip/")
    @ApiOperation(value = "下载压缩包")
    public void getZip(@RequestParam("taskName") String taskName, HttpServletResponse response) throws Exception {

        List<TaskStreamEntity> taskStreamEntityList = taskStreamService.getTaskStreamListByTaskName(taskName);
        Map<String, InputStream> map = new HashMap<>();
        int a=0;
        for (TaskStreamEntity taskStreamEntity : taskStreamEntityList) {
            List<TaskImageEntity> taskImageEntityList = taskImageService.getTaskImageEntityList(taskStreamEntity.getTaskName(), taskStreamEntity.getStreamId());
            for (TaskImageEntity taskImageEntity : taskImageEntityList) {
                //数据为空的图片直接跳过
                if (taskImageEntity.getAnnotationInfo().size() < 1) {
                    continue;
                }
                ;
                List<String> list1 = LabelAnnotionToMott.getMott(taskImageEntity);
                InputStream txt = new ByteArrayInputStream(list1.get(0).getBytes(StandardCharsets.UTF_8));
                InputStream png = HttpUtils.getInputStream(taskImageEntity.getPngUrl());

                String folder = taskImageEntity.getTaskName() + "/" + taskStreamEntity.getGroupName() + "/" + taskImageEntity.getStreamId()
                        + "/" + taskImageEntity.getImageId();
                map.put("motTxt/" + folder + ".txt", txt);
                downLoadPng(png, "/home/zhoup/Downloads/"+ folder+".png");
                a++;
                System.out.println("第"+a+"张");
               // map.put("png/" + folder + ".png", png);
            }
        }
        ZipUtils.doZip("demo.zip", map, response);
    }

    @GetMapping("/getStreamData/{ids}")
    @ApiOperation(value = "下载压缩包")
    @ResponseBody
    public void getStreamZip(@PathVariable Long[] ids , HttpServletResponse response) throws Exception {
        List<TaskStreamEntity> taskStreamEntityList= taskStreamService.getTaskStreamListByIds(ids);
        Map<String, InputStream> map=new HashMap<>();


        for(TaskStreamEntity taskStreamEntity: taskStreamEntityList){
            List<TaskImageEntity> taskImageEntityList= taskImageService.getTaskImageEntityList(taskStreamEntity.getTaskName(), taskStreamEntity.getStreamId());
            for(TaskImageEntity taskImageEntity: taskImageEntityList){
                //数据为空的图片直接跳过
                if(taskImageEntity.getAnnotationInfo().size()<1){
                    continue;
                };
                List<String> list1= LabelAnnotionToMott.getMott(taskImageEntity);
                InputStream txt= new ByteArrayInputStream(list1.get(0).getBytes(StandardCharsets.UTF_8));
                InputStream png= HttpUtils.getInputStream(taskImageEntity.getPngUrl());

                String folder= taskImageEntity.getTaskName()+ "/" + taskStreamEntity.getGroupName()+ "/"+ taskImageEntity.getStreamId()
                        + "/"+ taskImageEntity.getImageId();

                map.put("motTxt/"+ folder + ".txt",  txt);
                map.put("png/"+ folder + ".png", png);
            }
        }
        ZipUtils.doZip("demo.zip",map,response);
    }

    public void downLoadPng(InputStream inputStream, String folder) throws IOException {
        System.out.println(folder);
        mkdir(folder);
        FileOutputStream out = new FileOutputStream(folder);
        int j = 0;
        while ((j = inputStream.read()) != -1) {
            out.write(j);
        }
        inputStream.close();
    }

    public void mkdir(String folder){
        File f = new File(folder);
        if (f.exists()) {
            // 文件已经存在，输出文件的相关信息
            System.out.println(f.getAbsolutePath());
            System.out.println(f.getName());
            System.out.println(f.length());
        } else {
            //  先创建文件所在的目录
            f.getParentFile().mkdirs();
        }
    }
}
