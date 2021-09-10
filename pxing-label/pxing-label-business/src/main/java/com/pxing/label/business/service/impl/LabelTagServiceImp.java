package com.pxing.label.business.service.impl;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pxing.label.business.dao.LabelTagDao;
import com.pxing.label.business.domain.entity.TagEntity;
import com.pxing.label.business.domain.vo.LabelTagVo;
import com.pxing.label.business.service.LabelTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LabelTagServiceImp implements LabelTagService {
    @Autowired
    private LabelTagDao labelTagDao;


    @Override
    public List<TagEntity> selectLabelTagList(TagEntity labelTag) {
        QueryWrapper<TagEntity> querywrapper = new QueryWrapper<>();
        return labelTagDao.selectList(querywrapper);
    }

    @Override
    public int insertLabelTag(TagEntity labelTag) {
        return labelTagDao.insert(labelTag);
    }

    @Override
    public int updateLabelTag(TagEntity labelTag) {
        return 0;
    }

    @Override
    public int deleteLabelTagByIds(Long[] projectIds) {
        return 0;
    }

    @Override
    public List<LabelTagVo> selectTagListByProjectId(Long projectId) {
        return labelTagDao.selectTagListByProjectId(projectId);
    }
}
