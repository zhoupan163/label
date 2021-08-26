import request from '@/utils/request'

// 查询分配给自己却没有完成审批的视频stream
export function getUnFinishedCheckedImage(taskName, token, qa_level) {
  return request({
    url: '/business/labelCheck/getUnFinishedCheckedVideoStream'+'?taskName='+ taskName.toString() +'&token='+ token.toString()+ "&qa_level=" + qa_level,
    method: 'get',
  })
}

// 查询已经标注完成却没有开始审核的stream
export function getUnCheckedStream(taskName, qa_level) {
  return request({
    url: '/business/labelCheck/getUnCheckedStream'+'?taskName='+ taskName.toString()+ "&qa_level=" + qa_level,
    method: 'get',
  })
}
