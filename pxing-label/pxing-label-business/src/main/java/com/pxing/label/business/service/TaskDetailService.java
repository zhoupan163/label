package com.pxing.label.business.service;


import com.pxing.label.business.domain.entity.TaskDetailEntity;

public interface TaskDetailService {

    TaskDetailEntity getTaskDetail(String taskName);

    int insertTaskDetail(String taskName);

    int updateTaskDetailTotal(Integer framesize, String taskName);

    int updateTaskDetail(String taskName, String type, Integer number);

    int discardImage(String taskName, Integer number);

    int commitLabeled(String taskName, Integer number);

    int passQa1(String taskName, Integer number);

    int passQa2(String taskName, int passCount);

    int rejectQa1(String taskName, int rejectCount);

    int rejectQa2(String taskName, int rejectCount);
}
