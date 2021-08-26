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
export function getLabelTaskUnfinishedStream(taskName, token) {
  return request({
    url: '/business/labelTask/getLabelTaskUnfinishedStream'+'?taskName='+ taskName.toString() +'&token='+ token.toString(),
    method: 'get',
  })
}

// 查询未分配的stream
export function getLabelTaskStream(taskName) {
  return request({
    url: '/business/labelTask/getLabelTaskStream'+'?taskName='+ taskName.toString(),
    method: 'get',
  })
}


// 分配stream
export function assignLabelTaskStream(streamId, taskName, token, type) {
  return request({
    url: '/business/labelTask/assignLabelTaskStream'+ '?streamId='+ streamId.toString()+ '&taskName=' +taskName+
          '&token='+ token.toString()+'&type='+ type.toString(),
    method: 'get',
  })
}
