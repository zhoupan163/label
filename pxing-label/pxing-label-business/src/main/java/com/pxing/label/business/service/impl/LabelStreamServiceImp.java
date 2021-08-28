package com.pxing.label.business.service.impl;



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
       // List<LabelStreamVo>  list= labelStreamDao.getLabelTaskUnfinishedStream(taskName, userName);
        return null;
    }

    @Override
    public void insertLabelStreamVo(LabelStreamVo labelStreamVo) {
        labelStreamDao.insertLabelStreamVo(labelStreamVo);
    }

    @Override
    public void updateLabelStreamService(LabelStreamVo labelStreamVo) {
        labelStreamDao.updateLabelStreamVo(labelStreamVo);
    }

    @Override
    public List<LabelStreamVo> getUnCheckedStream(LabelStreamVo labelStreamVo) {

        return labelStreamDao.getLabelTaskUnCheckedStream(labelStreamVo);
    }
}
