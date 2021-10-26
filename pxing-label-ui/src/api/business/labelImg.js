import request from '@/utils/request'

export function listLabelTaskImg(query) {
  return request({
    url: '/business/taskImg/list',
    method: 'post',
    data: query
  })
}



