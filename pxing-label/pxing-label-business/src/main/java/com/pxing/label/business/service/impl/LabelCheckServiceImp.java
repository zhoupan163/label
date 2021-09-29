package com.pxing.label.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.result.UpdateResult;
import com.pxing.label.business.dao.TaskStreamDao;
import com.pxing.label.business.domain.entity.TaskImageEntity;
import com.pxing.label.business.domain.entity.TaskStreamEntity;
import com.pxing.label.business.domain.vo.*;
import com.pxing.label.business.service.LabelCheckService;

import com.pxing.label.business.service.LabelStreamService;
import com.pxing.label.business.service.TaskDetailService;
import com.pxing.label.business.service.TaskStreamService;
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
    public int qa(LabelImageCheck labelImageCheck) {
        List<Pair<Query, Update>> updateList = new ArrayList<>();
        BulkOperations operations = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, TaskImageEntity.class);
        List<String> imgList = labelImageCheck.getImgList();
        List<Integer> imgStatusList = labelImageCheck.getImgStatusList();
        List<String> imgQaCommentList = labelImageCheck.getImgQaCommentList();
        String taskName = labelImageCheck.getTaskName();
        String streamId = labelImageCheck.getStreamId();
        String qaType = labelImageCheck.getQaType();

        int passCount= 0;
        int rejectCount= 0;

        for (int i = 0; i < imgList.size(); i++) {
            Query query = Query.query(Criteria.where("task_name").is(taskName).and("jpg_url").is(imgList.get(i)));

            int status=  imgStatusList.get(i);
            String comment= imgQaCommentList.get(i);
            Update update = new Update();
            update.set("qa_comment", comment );
            update.set("status", status);
            if (status==5){
                rejectCount+=1;
            }else{
                passCount+=1;
            }
            //审核不通过
            /*

            if (imgStatusList.get(i).equals("4")) {
                update.set("status", 4);
            } else {
                if (qaType.equals("qa1")) {

                    update.set("status", 2);
                } else {
                    update.set("status", 3);
                }
            };
            *
             */

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
            int a=10/0;
            return taskStreamDao.update(null, updateWrapper);
        }
    }

}
