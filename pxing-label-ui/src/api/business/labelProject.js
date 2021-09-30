import request from '@/utils/request'

// 查询标注任务
export function listLabelProject() {
  return request({
    url: '/business/labelProject/list',
    method: 'get'
  })
}
// 新增工程
export function addLabelProject(data) {
  return request({
    url: '/business/labelProject/',
    method: 'post',
    data: data
  })
}

