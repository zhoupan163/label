package com.pxing.label.business.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mongodb.bulk.BulkWriteResult;

import com.pxing.label.business.dao.TaskStreamDao;
import com.pxing.label.business.domain.entity.TaskImageEntity;
import com.pxing.label.business.domain.entity.TaskStreamEntity;
import com.pxing.label.business.domain.vo.*;
import com.pxing.label.business.service.LabelCheckService;


import com.pxing.label.business.service.TaskDetailService;
import com.alibaba.fastjson.JSONArray;
import com.pxing.label.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;


@Service
public class LabelCheckServiceImp implements LabelCheckService {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private TaskDetailService taskDetailService;

    @Autowired
    private TaskStreamDao taskStreamDao;


    @Override
    public List<LabelTaskImageVo> getUnFinishedCheckedImage(String taskName, String userName,int qa_level) {
        Query query = Query.query(Criteria.where("task_name").is(taskName).and("qa" + qa_level).is(userName).and("image_status").is(qa_level));
        List<LabelTaskImageVo> list = mongoTemplate.find(query, LabelTaskImageVo.class);
        return list;

    }

    @Override
    public int qa(LabelImageCheck labelImageCheck, String userName) {
        List<Pair<Query, Update>> updateList = new ArrayList<>();
        BulkOperations operations = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, TaskImageEntity.class);
        List<String> imgList = labelImageCheck.getImgList();
        List<Integer> imgStatusList = labelImageCheck.getImgStatusList();
        List<String> imgQaCommentList = labelImageCheck.getImgQaCommentList();
        List<JSONArray> jsonArrayList= labelImageCheck.getQaArray();
        String taskName = labelImageCheck.getTaskName();
        String streamId = labelImageCheck.getStreamId();
        String qaType = labelImageCheck.getQaType();

        int passCount= 0;
        int rejectCount= 0;

        for (int i = 0; i < imgList.size(); i++) {
            Query query = Query.query(Criteria.where("task_name").is(taskName).and("jpg_url").is(imgList.get(i)));

            int status=  imgStatusList.get(i);
            String comment= imgQaCommentList.get(i);
            JSONArray array= jsonArrayList.get(i);
            Update update = new Update();
            update.set("status", status);

            JSONObject qaJson= new JSONObject();
            qaJson.put("user", userName);
            qaJson.put("type", labelImageCheck.getQaType());
            qaJson.put("comment", comment);
            qaJson.put("time", DateUtils.getTime());


            if (status==4){
                update.inc("reject_count",1);
                qaJson.put("action", "reject");
                rejectCount+=1;
            }else{
                passCount+=1;
                qaJson.put("action", "pass");
            }
            array.add(qaJson);
            update.set("qa_comment", array);

            Pair<Query, Update> updatePair = Pair.of(query, update);
            updateList.add(updatePair);
        };

        operations.updateMulti(updateList);
        BulkWriteResult result = operations.execute();

        if (labelImageCheck.getStreamId() != null && labelImageCheck.getStreamId().equals("")) {
            if(rejectCount>0){

            };
            if(qaType!=null && qaType.equals("qa1")&&passCount>0){
                taskDetailService.commitQa1(taskName, passCount);
            }else if(qaType!=null && qaType.equals("qa2")&&passCount>0){
                taskDetailService.commitQa2(taskName, passCount);
                }
            return 1;
        } else {
            //视频审核需要修改task_stream
            UpdateWrapper<TaskStreamEntity> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("task_name", taskName).eq("stream_id", streamId);
            if (imgStatusList.contains("4")) {
                updateWrapper.set("status", 4);
            } else {
                if (qaType.equals("qa1")) {
                    updateWrapper.set("status", 2);
                } else {
                    updateWrapper.set("status", 3);
                }
            }
            return taskStreamDao.update(null, updateWrapper);
        }
    }

}
