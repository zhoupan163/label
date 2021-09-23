import request from '@/utils/request'

// 下载标注任务
export function downLoadLabelData(taskName) {
  return request({
    url: '/business/labelData/getLabelTaskZip/'+ "?taskName=" +taskName,
    method: 'get',
    responseType: 'blob'
  })
}

// 批量下载标注视频组
export function downLoadStreamData(ids) {
  return request({
    url: '/business/labelData/getStreamData/' +ids,
    method: 'get',
    responseType: 'blob'
  })
}




