package com.pxing.label.business.service.impl;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pxing.label.business.dao.LabelTagDao;
import com.pxing.label.business.domain.entity.LabelProjectEntity;
import com.pxing.label.business.domain.entity.LabelTagEntity;
import com.pxing.label.business.service.LabelTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LabelTagServiceImp implements LabelTagService {
    @Autowired
    private LabelTagDao labelTagDao;


    @Override
    public List<LabelTagEntity> selectLabelTagList(LabelTagEntity labelTag) {
        QueryWrapper<LabelTagEntity> querywrapper = new QueryWrapper<>();
        return labelTagDao.selectList(querywrapper);
    }

    @Override
    public int insertLabelTag(LabelTagEntity labelTag) {
        return labelTagDao.insert(labelTag);
    }

    @Override
    public int updateLabelTag(LabelTagEntity labelTag) {
        return 0;
    }

    @Override
    public int deleteLabelTagByIds(Long[] projectIds) {
        return 0;
    }
}
