package com.pxing.label.business.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.pxing.label.business.domain.entity.TaskImageEntity;
import com.pxing.label.business.domain.entity.TaskStreamEntity;
import com.pxing.label.business.domain.vo.LabelViaProjectVo;
import com.pxing.label.business.service.LabelViaService;
import com.pxing.label.business.service.TaskDetailService;
import com.pxing.label.business.service.TaskStreamService;
import com.pxing.label.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class LabelViaServiceImp implements LabelViaService {

    private static final Logger logger = LoggerFactory.getLogger(LabelViaServiceImp.class);

    @Resource
    private MongoTemplate mongoTemplate;

    @Autowired
    private TaskStreamService  taskStreamService;

    @Autowired
    private TaskDetailService taskDetailService;


    @Override
    public List<LabelViaProjectVo> getViaProject(String taskName, String streamId, String userName, String type) {
        Query query=Query.query(Criteria.where("task_name").is(taskName).and("type").is("template"));
        LabelViaProjectVo labelViaProjectVo= mongoTemplate.findOne(query ,LabelViaProjectVo.class);

        Query query1= new Query();
            //图片任务
        if(streamId!=null && streamId.equals("")){
            Criteria criteria=  Criteria.where("task_name").is(taskName).and(type).is(userName);
            if(type!=null && type.equals("label")){
                criteria.and("status").in(0,4);
            }else if(type!=null && type.equals("qa1")){
                criteria.and("status").is(1);
            }else if(type!=null && type.equals("qa2")){
                criteria.and("status").is(2);
            }else{
                //预览界面
                criteria.and("status").nin(5);
            }
            query1= Query.query(criteria);
        }else{
            //视频流任务
            query1=Query.query(Criteria.where("task_name").is(taskName).and("stream_id").is(streamId).and("status").nin(5));
        }

        List<TaskImageEntity> taskImageEntityList= mongoTemplate.find(query1 , TaskImageEntity.class);

        JSONObject via_img_metadata= new JSONObject();
        //获取图片
        List<String> imageList= new ArrayList<>();
        List<JSONArray> qaArray= new ArrayList<>();
        List<Integer> imgStatusList= new ArrayList<>();
        for(TaskImageEntity taskImageEntity : taskImageEntityList){
            String imageUrl= taskImageEntity.getJpgUrl();
            imageList.add(imageUrl);
            if(taskImageEntity.getQaComment()!=null){
                qaArray.add(taskImageEntity.getQaComment());
            }else{
                qaArray.add(new JSONArray());
            }

            imgStatusList.add(Integer.valueOf(taskImageEntity.getStatus()));
            JSONObject jsonObject= new JSONObject();
            jsonObject.put("filename", imageUrl);
            jsonObject.put("size", -1);
            jsonObject.put("regions", taskImageEntity.getAnnotationInfo());
            jsonObject.put("file_attributes", new JSONObject());
            via_img_metadata.put(imageUrl, jsonObject);
        };

        labelViaProjectVo.setVia_image_id_list(imageList);
        labelViaProjectVo.setQaArray(qaArray);
        labelViaProjectVo.setImg_status_list(imgStatusList);
        labelViaProjectVo.setVia_img_metadata(via_img_metadata);

        List<LabelViaProjectVo> list=new ArrayList<>();
        list.add(labelViaProjectVo);

        return list;
    }


    @Override
    public int updateViaInfo(LabelViaProjectVo labelViaProjectVo) {
        int count= 0;
        JSONObject via_img_metadata= labelViaProjectVo.getVia_img_metadata();
        for (String jpgUrl : labelViaProjectVo.getVia_image_id_list()) {
            logger.info("jpg_url:{}",jpgUrl);
            JSONObject j1= via_img_metadata.getJSONObject(jpgUrl);
            JSONArray regions= via_img_metadata.getJSONObject(jpgUrl).getJSONArray("regions");
            Query query = Query.query(Criteria.where("jpg_url").is(jpgUrl).and("task_name").is(labelViaProjectVo.getTaskName()));
            Update update= new Update();
            //update.set("status", 0);
            update.set("annotationInfo", regions);
            count+= mongoTemplate.updateMulti(query, update, TaskImageEntity.class).getModifiedCount();
        };
        //int a= 10/0;
        logger.info("成功更新条数:{}",count);
        return count;
    }

    @Override
    public int commitViaInfo(LabelViaProjectVo labelViaProjectVo) {

        int count= 0;
        JSONObject via_img_metadata= labelViaProjectVo.getVia_img_metadata();
        for (String jpgUrl : labelViaProjectVo.getVia_image_id_list()) {
            JSONArray regions= via_img_metadata.getJSONObject(jpgUrl).getJSONArray("regions");
            //驳回或者第一次提交操作 已经审批过的数据不再做修改
            Query query = Query.query(Criteria.where("jpg_url").is(jpgUrl).and("status").in(0, 4).
                    and("task_name").is(labelViaProjectVo.getTaskName()));
            Update update= new Update();
            update.set("status", 1);
            update.set("annotationInfo", regions);
            count+= mongoTemplate.updateFirst(query, update, TaskImageEntity.class).getModifiedCount();
        };
        logger.info("成功提交条数:{}",count);

        //视频流需要提交  非视频流不需要提交
        if(labelViaProjectVo.getStreamId()!=null && !labelViaProjectVo.getStreamId().equals("")){
            taskStreamService.commitTaskStream(labelViaProjectVo.getTaskName(), labelViaProjectVo.getStreamId());
        }

        //修改task_detail表
        taskDetailService.commitLabeled(labelViaProjectVo.getTaskName(), count);
        return count;
    }


    @Override
    public void test() {
        TaskImageEntity taskImageEntity= new TaskImageEntity();
        taskImageEntity.setImageId("aaa");
        taskImageEntity.setJpgUrl("123456.jpg");
        mongoTemplate.insert(taskImageEntity);

        Query query = Query.query(Criteria.where("jpg_url").is("123456.jpg"));
        Update update= new Update();
        update.set("status", 1);
        mongoTemplate.updateFirst(query, update, TaskImageEntity.class);
        int a=3/0;
        update.set("tag", 1);
        mongoTemplate.updateFirst(query, update, TaskImageEntity.class).getModifiedCount();
        mongoTemplate.insert(taskImageEntity);
    }


}
