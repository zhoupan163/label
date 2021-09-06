import request from '@/utils/request'

// 查询场景
export function selectUnTaggedImageList(streamId) {
  return request({
    url: '/business/labelStreamTag/selectUnTaggedImageList' +'?streamId=' +streamId,
    method: 'get'
  })
}



