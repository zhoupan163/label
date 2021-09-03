import request from '@/utils/request'

// 查询场景
export function listLabelTag(query) {
  return request({
    url: '/business/labelTag/list',
    method: 'get',
    params: query
  })
}
// 新增场景
export function addLabelTag(data) {
  return request({
    url: '/business/labelTag/',
    method: 'post',
    data: data
  })
}


