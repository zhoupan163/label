package com.pxing.label.business.service.impl;

import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.result.UpdateResult;
import com.pxing.label.business.domain.vo.*;
import com.pxing.label.business.service.LabelCheckService;

import com.pxing.label.business.service.LabelStreamService;
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
    private LabelStreamService labelStreamService;


    @Override
    public List<LabelTaskImageVo> getUnFinishedCheckedImage(String taskName, String userName,int qa_level) {
        Query query = Query.query(Criteria.where("task_name").is(taskName).and("qa" + qa_level).is(userName).and("image_status").is(qa_level));
        List<LabelTaskImageVo> list = mongoTemplate.find(query, LabelTaskImageVo.class);
        return list;

    }

    @Override
    public List<UpdateResult> qa(LabelImageCheck labelImageCheck) {
        List<Pair<Query, Update>> updateList = new ArrayList<>();
        BulkOperations operations = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, LabelTaskImageVo.class);

        List<String> imgList = labelImageCheck.getImgList();
        List<String> imgStatusList = labelImageCheck.getImgStatusList();
        List<String> imgQaCommentList = labelImageCheck.getImgQaCommentList();
        String taskName = labelImageCheck.getTaskName();
        String qaType = labelImageCheck.getQaType();

       // Boolean flag= imgStatusList.contains("4");
        for (int i = 0; i < imgList.size(); i++) {
            Query query = Query.query(Criteria.where("task_name").is(taskName).and("jpg_url").is(imgList.get(i)));

            Update update = new Update();
            update.set("qa_comment", imgQaCommentList.get(i));
            //审核不通过
            if (imgStatusList.get(i).equals("4")) {
                update.set("image_status", 4);
            } else {
                if (qaType.equals("qa1")) {
                    update.set("image_status", 2);
                } else {
                    update.set("image_status", 3);
                }
            };

            Pair<Query, Update> updatePair = Pair.of(query, update);
            updateList.add(updatePair);
        };

        operations.updateMulti(updateList);
        BulkWriteResult result = operations.execute();

        LabelStreamVo labelStreamVo= new LabelStreamVo();
        labelStreamVo.setStreamId(labelImageCheck.getStreamId());
        if(imgStatusList.contains("4")){
            labelStreamVo.setStatus(4);
        }else{
            if (qaType.equals("qa1")) {
                labelStreamVo.setStatus(2);
            } else {
                labelStreamVo.setStatus(3);
            }
        };
        labelStreamService.updateLabelStreamService(labelStreamVo);
        return new ArrayList<>();
    }

}
