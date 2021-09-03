import request from '@/utils/request'

// 查询场景
export function listLabelScene(query) {
  return request({
    url: '/business/labelScene/list',
    method: 'get',
    params: query
  })
}
// 新增场景
export function addLabelScene(data) {
  return request({
    url: '/business/labelScene/',
    method: 'post',
    data: data
  })
}

