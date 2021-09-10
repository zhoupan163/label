import request from '@/utils/request'

// 查询分配给自己却没有完成审批的视频stream
export function getUnFinishedCheckedImage(taskId, qa_level) {
  return request({
    url: '/business/labelCheck/getUnFinishedCheckedVideoStream'+'?taskId='+ taskId +"&qa_level=" + qa_level,
    method: 'get',
  })
}

// 查询已经标注完成却没有开始审核的stream
export function getUnCheckedStream(taskId, qa_level) {
  return request({
    url: '/business/labelCheck/getUnCheckedStream'+'?taskId='+ taskeId+ "&qa_level=" + qa_level,
    method: 'get',
  })
}
