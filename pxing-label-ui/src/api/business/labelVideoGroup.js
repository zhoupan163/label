import request from '@/utils/request'

// 查询group
export function listVideoGroup(query) {
  return request({
    url: '/business/labelVideoGroup/list',
    method: 'get',
    params: query
  })
}

// 新增group
export function addLabelVideoGroup(data) {
  return request({
    url: '/business/labelVideoGroup/',
    method: 'post',
    data: data
  })
}


// 查询id
export function listVideoGroupId(groupName) {
  return request({
    url: '/business/labelVideoGroupId/list?groupName='+groupName,
    method: 'get',

  })
}

// 新增task
export function addLabelVideoGroupId(data) {
  return request({
    url: '/business/labelVideoGroupId/',
    method: 'post',
    header: {'Content type': 'multipart/form-data'},
    data: data
  })
}

// 删除
export function delLabelVideoGroupIds(ids) {
  return request({
    url: '/business/labelVideoGroupId/'+ ids,
    method: 'delete'
  })
}
