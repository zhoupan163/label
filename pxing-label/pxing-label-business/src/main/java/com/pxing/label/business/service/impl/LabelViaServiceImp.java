package com.pxing.label.business.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.result.UpdateResult;
import com.pxing.label.business.domain.vo.LabelTaskImageVo;
import com.pxing.label.business.domain.vo.LabelViaProjectVo;
import com.pxing.label.business.service.LabelViaService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class LabelViaServiceImp implements LabelViaService {

    @Resource
    private MongoTemplate mongoTemplate;



    @Override
    public List<LabelViaProjectVo> insertSreamViaProject(String streamId, String taskName) {
        //获取图片
        Query query=Query.query(Criteria.where("task_name").is(taskName).and("type").is("template"));
        List<LabelViaProjectVo> templateList= mongoTemplate.find(query ,LabelViaProjectVo.class);
        LabelViaProjectVo labelViaProjectVo= templateList.get(0);

        Query query1= Query.query(Criteria.where("task_name").is(taskName).and("stream_id").is(streamId));
        List<LabelTaskImageVo> labelTaskImageVos= mongoTemplate.find(query1 , LabelTaskImageVo.class);

        JSONObject via_img_metadata= new JSONObject();

        //获取图片
        List<String> imageList= new ArrayList<>();
        for(LabelTaskImageVo labelTaskImageVo : labelTaskImageVos){
            String imageUrl=labelTaskImageVo.getJpg_url();
            imageList.add(imageUrl);
            JSONObject jsonObject= new JSONObject();
            jsonObject.put("filename", imageUrl);
            jsonObject.put("size", -1);
            jsonObject.put("regions", new ArrayList<>());
            jsonObject.put("file_attributes", new JSONObject());
            via_img_metadata.put(imageUrl, jsonObject);
        };

        //存储到mongo
        labelViaProjectVo.setVia_image_id_list(imageList);
        //labelViaProjectVo.setStream_id(streamId);
        labelViaProjectVo.setType("stream_project");
        labelViaProjectVo.setVia_img_metadata(via_img_metadata);




        List<LabelViaProjectVo> list=new ArrayList<>();
        list.add(labelViaProjectVo);
        mongoTemplate.insert(list,LabelViaProjectVo.class);


        return list;
    }

    @Override
    public List<LabelViaProjectVo> getSreamViaProject(String taskName, String streamId, String userName, String type) {

        Query query=Query.query(Criteria.where("task_name").is(taskName).and("type").is("template"));
        LabelViaProjectVo labelViaProjectVo= mongoTemplate.findOne(query ,LabelViaProjectVo.class);

        Query query1=Query.query(Criteria.where("task_name").is(taskName).and("stream_id").is(streamId).and(type).is(userName).and("image_status").nin(5));
        List<LabelTaskImageVo> labelTaskImageVoList= mongoTemplate.find(query1 , LabelTaskImageVo.class);

        JSONObject via_img_metadata= new JSONObject();
        //获取图片
        List<String> imageList= new ArrayList<>();
        List<String> qaCommentList= new ArrayList<>();
        List<Integer> imgStatusList= new ArrayList<>();
        for(LabelTaskImageVo labelTaskImageVo : labelTaskImageVoList){
            String imageUrl=labelTaskImageVo.getJpg_url();
            imageList.add(imageUrl);
            qaCommentList.add(labelTaskImageVo.getQa_comment());
            //qa2 的Img_status_list做特殊处理
            if(type.equals("qa2")){
                imgStatusList.add(Integer.valueOf(labelTaskImageVo.getImage_status()) - 2);
            }else if(type.equals("qa1")){
                imgStatusList.add(Integer.valueOf(labelTaskImageVo.getImage_status()) - 1);
            }else {
                imgStatusList.add(Integer.valueOf(labelTaskImageVo.getImage_status()));
            }

            JSONObject jsonObject= new JSONObject();
            jsonObject.put("filename", imageUrl);
            jsonObject.put("size", -1);
            jsonObject.put("regions", labelTaskImageVo.getAnnotationInfo());
            jsonObject.put("file_attributes", new JSONObject());
            via_img_metadata.put(imageUrl, jsonObject);
        };

        labelViaProjectVo.setVia_image_id_list(imageList);
        labelViaProjectVo.setQa_comment_list(qaCommentList);
        labelViaProjectVo.setImg_status_list(imgStatusList);
        labelViaProjectVo.setVia_img_metadata(via_img_metadata);

        List<LabelViaProjectVo> list=new ArrayList<>();
        list.add(labelViaProjectVo);

        return list;
    }

    @Override
    public List<LabelViaProjectVo> updateSreamViaProject(LabelViaProjectVo labelViaProjectVo) {
        Query query=Query.query(Criteria.where("task_name").is(labelViaProjectVo.getTask_name()));
        Update update=new Update();
        update.set("via_img_metadata",labelViaProjectVo.getVia_img_metadata());
        UpdateResult updateResult= mongoTemplate.updateFirst(query, update, LabelViaProjectVo.class);
        System.out.println("hah");
        return null;
    }



}
