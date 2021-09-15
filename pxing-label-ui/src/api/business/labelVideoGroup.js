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
export function listVideoGroupId(query) {
  return request({
    url: '/business/labelVideoGroupId/list',
    method: 'get',
    params: query
  })
}

// 新增task
export function addLabelVideoGroupId(data) {
  return request({
    url: '/business/labelVideoGroupId/',
    method: 'post',
    data: data
  })
}
