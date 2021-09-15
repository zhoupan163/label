package com.pxing.label.business.dao;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pxing.label.business.domain.entity.TagEntity;
import com.pxing.label.business.domain.vo.LabelTagVo;
import org.apache.ibatis.annotations.Select;


import java.util.List;


public interface LabelTagDao extends BaseMapper<TagEntity> { ;
}
