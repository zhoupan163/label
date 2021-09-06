import request from '@/utils/request'

// 查询标注任务
export function listLabelProject(query) {
  return request({
    url: '/business/labelProject/list',
    method: 'get',
    params: query
  })
}
// 新增用户
export function addLabelProject(data) {
  return request({
    url: '/business/labelProject/',
    method: 'post',
    data: data
  })
}
