package com.pxing.label.business.service.impl;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.pxing.label.business.dao.LabelStreamDao;
import com.pxing.label.business.domain.vo.*;
import com.pxing.label.business.service.LabelStreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class LabelStreamServiceImp implements LabelStreamService {
    @Autowired
    private LabelStreamDao labelStreamDao;

    @Override
    public List<LabelStreamVo> getLabelTaskUnfinishedStream(String taskName, String userName) {
        QueryWrapper<LabelStreamVo> querywrapper = new QueryWrapper<>();
        querywrapper.eq("task_name", taskName).eq("label_by", userName);
        querywrapper.in("status", 0,4).orderByDesc("status");
        return labelStreamDao.selectList(querywrapper);
    }

    @Override
    public int insertLabelStreamVo(LabelStreamVo labelStreamVo) {
        //labelStreamDao.insert(labelStreamVo);
        //labelStreamDao.insertLabelStreamVo(labelStreamVo);
        return labelStreamDao.insert(labelStreamVo);
    }

    @Override
    public int updateLabelStream(LabelStreamVo labelStreamVo) {
        UpdateWrapper<LabelStreamVo> labelStreamVoUpdateWrapper= new UpdateWrapper<>();
        labelStreamVoUpdateWrapper.eq("stream_id", labelStreamVo.getStreamId());
        return labelStreamDao.update(labelStreamVo, labelStreamVoUpdateWrapper);
        //labelStreamDao.updateLabelStreamVo(labelStreamVo);
    }

    @Override
    public List<LabelStreamVo> getStreamList(LabelStreamVo labelStreamVo) {
        QueryWrapper<LabelStreamVo> querywrapper = new QueryWrapper<>();
        querywrapper.eq("task_name", labelStreamVo.getTaskName()).eq("status", labelStreamVo.getStatus());
        if(labelStreamVo.getQa1()!=null){
            querywrapper.eq("qa1",labelStreamVo.getQa1());
        }else if(labelStreamVo.getQa2()!=null){
            querywrapper.eq("qa2", labelStreamVo.getQa2());
        }
         return labelStreamDao.selectList(querywrapper);
    }

}
