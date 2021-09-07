import request from '@/utils/request'

// 查询场景
export function selectUnTaggedImageList(streamId) {
  return request({
    url: '/business/labelStreamTag/selectUnTaggedImageList' +'?streamId=' +streamId,
    method: 'get'
  })
}

// 查询stream
export function selectStreamTagList() {
  return request({
    url: '/business/labelStreamTag/selectStreamList',
    method: 'get'
  })
}

// 查询stream对应的image
export function selectImageListByStreamId(streamId) {
  return request({
    url: '/business/labelStreamTag/selectImageListByStreamId?streamId='+ streamId,
    method: 'get'
  })
}

//查询 tagw

export function selectTagListByProjectId(projectId) {
  return request({
    url: '/business/labelStreamTag/selectImageListByProjectId?projectId='+ projectId,
    method: 'get'
  })
}


