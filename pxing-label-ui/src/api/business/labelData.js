import request from '@/utils/request'

// 下载标注任务
export function downLoadLabelData(taskId) {
  return request({
    url: '/business/labelData/getLabelTaskZip/'+ "?taskId=" +taskId,
    method: 'get',
    responseType: 'blob'
  })
}




