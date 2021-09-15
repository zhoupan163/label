import request from '@/utils/request'

// 查询场景
export function selectUnTaggedImageList(streamId) {
  return request({
    url: '/business/labelStreamTag/selectUnTaggedImageList' +'?streamId=' +streamId,
    method: 'get'
  })
}

// 查询stream
export function selectStreamList() {
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

//查询 tag
export function selectTagListByProjectName(projectName) {
  return request({
    url: '/business/labelStreamTag/selectTagListByProjectName?projectName='+ projectName,
    method: 'get'
  })
}

//绑定tag
export function tagStream(data) {
  return request({
    url: '/business/labelStreamTag/tagStream',
    method: 'put',
    params: data
  })
}


