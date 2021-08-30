package com.pxing.label.dao;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mongodb.client.result.UpdateResult;
import com.pxing.label.PxingLabelApplication;
import com.pxing.label.business.dao.LabelStreamDao;
import com.pxing.label.business.domain.vo.LabelStreamVo;
import com.pxing.label.business.domain.vo.LabelTaskImageVo;
import com.pxing.label.business.domain.vo.LabelTaskViaVo;
import com.pxing.label.business.service.LabelStreamService;
import com.pxing.label.business.service.LabelTaskService;
import com.pxing.label.business.service.LabelViaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = PxingLabelApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DaoTests {

    @Autowired
    private LabelStreamDao labelStreamDao;

    @Test
    public void test1(){
        LabelStreamVo labelStreamVo= new LabelStreamVo();
        labelStreamVo.setStreamId("demo2");
        labelStreamVo.setStatus(9);
        labelStreamVo.setTaskName("demo1");
        labelStreamVo.setRemark("test");
        //labelStreamDao.insertLabelStreamVo(labelStreamVo);

        //int a= labelStreamDao.insert(labelStreamVo);

        labelStreamVo.setStatus(5);
        labelStreamVo.setStreamId("demo3");
        //labelStreamDao.updateById(labelStreamVo);
        UpdateWrapper<LabelStreamVo> updateWrapper= new UpdateWrapper<>();
        updateWrapper.eq("stream_id", "demo2");
        labelStreamDao.update(labelStreamVo,updateWrapper);
        //System.out.println(a);
    }

}




