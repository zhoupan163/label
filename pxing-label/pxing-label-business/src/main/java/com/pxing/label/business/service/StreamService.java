package com.pxing.label.business.service;


import com.pxing.label.business.domain.entity.StreamEntity;

import java.util.List;

public interface StreamService {

    List<StreamEntity> selectStreamList();
}
