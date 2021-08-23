import request from '@/utils/request'

// 下载标注任务
export function downLoadLabelData(taskName) {
  return request({
    url: '/business/labelData/getLabelTaskZip/'+ "?taskName=" +taskName,
    method: 'get',
    responseType: 'blob'
  })
}




