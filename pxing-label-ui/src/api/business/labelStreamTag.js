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

//查询已经标注的 tag
export function selectTagListByStreamId(streamId) {
  return request({
    url: '/business/labelStreamTag/selectTagListByStreamId?streamId='+ streamId,
    method: 'get'
  })
}

//绑定tag
export function updateTagStream(data) {
  return request({
    url: '/business/labelStreamTag/updateTagStream',
    method: 'put',
    params: data
  })
}

//绑定tag
export function addTagStream(data) {
  return request({
    url: '/business/labelStreamTag/tagStream',
    method: 'put',
    params: data
  })
}


