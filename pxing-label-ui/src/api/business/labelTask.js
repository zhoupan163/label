import request from '@/utils/request'

// 查询标注任务
export function listLabelTask(query) {
  return request({
    url: '/business/labelTask/list',
    method: 'get',
    params: query
  })
}

// 查询分配给自己却没有标注完成的stream
export function getkUnfinishedTaskStream(taskName,groupName, type ) {
  return request({
    url: '/business/taskStream/getkUnfinishedTaskStreamList'+'?taskName='+ taskName + "&type="+ type+ "&groupName="+ groupName,
    method: 'get'
  })
}

// 查询未分配的stream
export function getUnAssignedTaskStream(taskName, groupName, type) {
  return request({
    url: '/business/taskStream/getUnAssignedTaskStreamList'+'?taskName='+ taskName+ "&type="+ type+ "&groupName="+ groupName,
    method: 'get'
  })
}

// 查询视频组的所有stream
export function getTaskStream(taskName, groupName, type) {
  return request({
    url: '/business/taskStream/getTaskStreamList'+'?taskName='+ taskName+ "&type="+ type+ "&groupName="+ groupName,
    method: 'get'
  })
}


// 分配stream
export function assignTaskStream(data) {
  return request({
    url: '/business/taskStream/assignTaskStream',
    method: 'put',
    params: data
  })
}

// 新增task
export function addLabelTask(data) {
  return request({
    url: '/business/labelTask/',
    method: 'post',
    data: data
  })
}
//获取已经标记的未添加到此task的stream
export function getTaggedStreamList(projectName, taskName) {
  return request({
    url: '/business/labelStreamTag/getTaggedStreamList?projectName=' + projectName + '&taskName='+ taskName,
    method: 'get'
  })
}
//添加task_stream
export function addTaskStream(data) {
  return request({
    url: '/business/taskStream/',
    method: 'post',
    data: data
  })
}

// 查询是否存在已拉取未完成标注的图片
export function checkTaskImage(taskName, type) {
  return request({
    url: '/business/labelTask/checkTaskImage?taskName='+ taskName+ '&type=' +type,
    method: 'get'
  })
}


// 查询未完成标注未锁定的图片
export function getTaskDetail(taskName) {
  return request({
    url: '/business/labelTask/getTaskDetail/?taskName='+ taskName,
    method: 'get'
  })
}

//添加task_stream
export function pullTaskImage(data) {
  return request({
    url: '/business/labelTask/pullTaskImage',
    method: 'post',
    params: data
  })
}
