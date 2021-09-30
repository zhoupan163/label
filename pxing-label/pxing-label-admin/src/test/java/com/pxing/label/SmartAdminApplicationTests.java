package com.pxing.label;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mongodb.client.result.UpdateResult;
import com.pxing.label.business.dao.ImageDao;
import com.pxing.label.business.dao.StreamDao;
import com.pxing.label.business.dao.TaskStreamDao;
import com.pxing.label.business.domain.entity.*;
import com.pxing.label.business.domain.vo.LabelTaskImageVo;
import com.pxing.label.business.domain.vo.LabelTaskViaVo;
import com.pxing.label.business.domain.vo.LabelViaProjectVo;
import com.pxing.label.business.service.LabelStreamService;
import com.pxing.label.business.service.LabelTaskService;
import com.pxing.label.business.service.LabelViaService;
import com.pxing.label.common.utils.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = PxingLabelApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SmartAdminApplicationTests {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    private LabelTaskService labelTaskService;

    @Autowired
    private LabelViaService labelViaService;

    @Autowired
    private TaskStreamDao taskStreamDao;
    @Test
    public  void test1(){
        LabelTaskViaVo labelTaskViaVo= new LabelTaskViaVo();
       // labelTaskViaVo.settaskName("1001");
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("key1","values");
        labelTaskViaVo.setVia_project_info(jsonObject);
        //UpdateResult updateResult=labelTaskService.updateLabelTaskViaInfo(labelTaskViaVo);
    }

    @Test
    public  void test2(){
        LabelTaskViaVo labelTaskViaVo= new LabelTaskViaVo();
        //labelTaskViaVo.settaskName("888");
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("key2","values2");
        labelTaskViaVo.setVia_project_info(jsonObject);
        labelTaskViaVo= mongoTemplate.insert(labelTaskViaVo);
        if (labelTaskViaVo != null) {
            System.out.println("success");
        } else {
            System.out.println("false");
        }
    }

    @Test
    public  void test3(){
        //labelTaskService.insertLabelTaskViaInfo(new LabelTaskViaVo());
        JSONArray jsonArray= new JSONArray();
        JSONObject j= new JSONObject();
        j.put("type","qa1");
        j.put("user","admin");

        JSONObject j1= new JSONObject();
        j1.put("type","qa2");
        j1.put("user","admin");
        jsonArray.add(j);
        jsonArray.add(j1);
        System.out.println(jsonArray);
        System.out.println("haha");
    }


    @Test
    public  void test4(){
        String streamId="46a2a622-1e92-11ec-92ea-000c293913c8";
        Query query1=Query.query(Criteria.where("stream_id").is(streamId));
        List<TaskImageEntity> taskImageEntityList= mongoTemplate.find(query1 , TaskImageEntity.class);
        int a= 710;
        while(a< taskImageEntityList.size()){
             TaskImageEntity taskImageEntity = taskImageEntityList.get(a);
             mongoTemplate.remove(Query.query(Criteria.where("stream_id").is(streamId).and("image_id").is(taskImageEntity.getImageId())), TaskImageEntity.class);
             a+=1;
        };
        //master_identification(String), 0917test(String), yanming(String), 0(Integer), 4(Integer)
        List<LabelViaProjectVo> list= labelViaService.getViaProject("master_identification", "56e4209e-16fc-11ec-92ea-000c293913c8", "yanming", "label");
        System.out.println("ok");

        //UpdateWrapper<TaskStreamEntity> updateWrapper= new UpdateWrapper<>();
        //updateWrapper.eq("task_name", "行人跟踪").eq("stream_id", "9ec51a6e-0994-11ec-92e8-000c293913c8").set("label", "admin");
        //int a= taskStreamDao.update(null, updateWrapper);
       // System.out.println("ok");
    }

    @Test
    public  void test5(){
        Query query=Query.query(Criteria.where("task_name").is("test"));
        Update update=new Update();
        JSONArray jsonArray= new JSONArray();
        JSONObject qaJson= new JSONObject();
        qaJson.put("user","admin");
        qaJson.put("type", "qa1");
        qaJson.put("comment", "框的位置不太对");
        qaJson.put("time", DateUtils.getTime());
        qaJson.put("action", "reject");

        JSONObject qaJson1= new JSONObject();
        qaJson1.put("user","test01");
        qaJson1.put("type", "qa2");
        qaJson1.put("comment", "标的没问题");
        qaJson1.put("time", DateUtils.getTime());
        qaJson1.put("action", "pass");

        jsonArray.add(qaJson);
        jsonArray.add(qaJson1);
        update.set("qa_comment",jsonArray);
        long a= mongoTemplate.updateFirst(query, update, TaskImageEntity.class).getModifiedCount();
        System.out.println(a);
        Update update1=new Update();
        long b= mongoTemplate.updateFirst(query, update1, TaskImageEntity.class).getModifiedCount();
        System.out.println(b);
    }

    @Test
    public  void test6(){
       //labelViaService.test();
        Criteria criteria = Criteria.where("task_name").is("图片标注测试");
        Aggregation aggregation = Aggregation.newAggregation(Aggregation.match(criteria), Aggregation.project("missid"),
                Aggregation.unwind("missid"), Aggregation.group("missid").count().as("count"),
                Aggregation.project("count").and("missid").previousOperation(), Aggregation.sort(Sort.Direction.DESC, "count"));

    }

    @Test
    public void test7(){
        Query query=Query.query(Criteria.where("stream_id").is("4134cf44-ffd4-11eb-92e8-000c293913c8"));
        Update update=new Update();
        update.set("image_status", 0);
        update.set("annotationInfo", "");
        update.set("qa_comment", "");
        update.set("label","");
        update.set("image_lock",0);
        update.set("qa1","");
        update.set("qa2","");
        UpdateResult updateResult= mongoTemplate.updateMulti(query, update ,LabelTaskImageVo.class);
        System.out.println(updateResult.toString());
    }

    @Autowired
    private LabelStreamService labelStreamService;



    @Test
    public void test10(){
        //pedestrian_reid
        Query query=Query.query(Criteria.where("frame_size").is(113));
        List<ProjectStreamEntity> list= mongoTemplate.find(query, ProjectStreamEntity.class);
        System.out.println("aa");
    }

    @Autowired
    private ImageDao imageDao;
    @Autowired
    private StreamDao streamDao;
    @Test
    public  void test11(){
        Query query=Query.query(Criteria.where("image_status").nin(10)).with(Sort.by("stream_id"));
        List<LabelTaskImageVo> list=mongoTemplate.find(query, LabelTaskImageVo.class);
        Set<String> set= new HashSet<>();

        String streamId= "";
        long id=0;

        for(LabelTaskImageVo labelTaskImageVo : list){
            if(!labelTaskImageVo.getStream_id().equals(streamId)){
                streamId= labelTaskImageVo.getStream_id();

                StreamEntity streamEntity =new StreamEntity();
                streamEntity.setCreateBy("admin");
                streamEntity.setSensorLocation(labelTaskImageVo.getSensor_location());
                streamEntity.setTopicType(labelTaskImageVo.getTopic_type());
                streamEntity.setSensorType(labelTaskImageVo.getSensor_type());
                streamEntity.setWidth(labelTaskImageVo.getWidth());
                streamEntity.setHeight(labelTaskImageVo.getHeight());
                streamEntity.setTagStatus(0);

                streamDao.insert(streamEntity);
                id= streamEntity.getId();
                System.out.println(streamEntity.getId());
            };
            ImageEntity imageEntity= new ImageEntity();

           // imageEntity.setStreamId(id);
            imageEntity.setFrameIndex(labelTaskImageVo.getFrame_index());
            imageEntity.setSensorLocation(labelTaskImageVo.getSensor_location());
            imageEntity.setSensorType(labelTaskImageVo.getSensor_type());
            imageEntity.setTopicType(labelTaskImageVo.getTopic_type());
            imageEntity.setWidth(labelTaskImageVo.getWidth());
            imageEntity.setHeight(labelTaskImageVo.getHeight());
            imageEntity.setJpgUrl(labelTaskImageVo.getJpg_url());
            imageEntity.setPngUrl(labelTaskImageVo.getPng_url());
            imageEntity.setThumbnailUrl(labelTaskImageVo.getThumbnail_url());
            //imageEntity.setCreateBy("admin");
            imageDao.insert(imageEntity);
         }
    }

    @Test
    public void testDownLoad() throws IOException {
        URL url1 = new URL("http://10.66.33.113:8080/dataset/jpg/camera_body_front_center_rgb/20210928/990ac49e-211c-11ec-92ea-000c293913c8/99e19bd6-211c-11ec-92ea-000c293913c8.jpg");
        URLConnection uc = url1.openConnection();
        InputStream inputStream = uc.getInputStream();

        FileOutputStream out = new FileOutputStream("/home/zhoup/Downloads/1.jpg");
        int j = 0;
        while ((j = inputStream.read()) != -1) {
            out.write(j);
        }

        File f = new File("/home/zhoup/Downloads/aa/aa/aa", "1.txt");
        if (f.exists()) {
            // 文件已经存在，输出文件的相关信息
            System.out.println(f.getAbsolutePath());
            System.out.println(f.getName());
            System.out.println(f.length());
        } else {
            //  先创建文件所在的目录
            f.getParentFile().mkdirs();
            inputStream.close();
        }
    }


}




