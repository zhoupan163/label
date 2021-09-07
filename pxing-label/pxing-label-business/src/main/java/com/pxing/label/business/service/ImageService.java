package com.pxing.label.business.service;


import com.pxing.label.business.domain.entity.ImageEntity;

import java.util.List;

public interface ImageService {


    List<ImageEntity> selectImageListByStreamId(Long streamId);
}
