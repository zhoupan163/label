import request from '@/utils/request'

// 查询标注任务
export function listLabelTask(query) {
  return request({
    url: '/business/labelTask/list',
    method: 'get',
    params: query
  })
}

// 查询分配给自己却没有标注完成的stream
export function getLabelTaskUnfinishedStream(taskName, token) {
  return request({
    url: '/business/labelTask/getLabelTaskUnfinishedStream'+'?taskName='+ taskName.toString() +'&token='+ token.toString(),
    method: 'get',
  })
}

// 查询未分配的stream
export function getLabelTaskStream(taskName) {
  return request({
    url: '/business/labelTask/getLabelTaskStream'+'?taskName='+ taskName.toString(),
    method: 'get',
  })
}


// 分配stream
export function assignLabelTaskStream(streamId, taskName, token) {
  return request({
    url: '/business/labelTask/assignLabelTaskStream'+ '?streamId='+ streamId.toString()+ '&taskName=' +taskName+ '&token='+ token.toString(),
    method: 'get',
  })
}



// 角色数据权限
export function dataScope(data) {
  return request({
    url: '/system/role/dataScope',
    method: 'put',
    data: data
  })
}

// 角色状态修改
export function changeRoleStatus(roleId, status) {
  const data = {
    roleId,
    status
  }
  return request({
    url: '/system/role/changeStatus',
    method: 'put',
    data: data
  })
}

// 删除角色
export function delRole(roleId) {
  return request({
    url: '/system/role/' + roleId,
    method: 'delete'
  })
}

// 导出角色
export function exportRole(query) {
  return request({
    url: '/system/role/export',
    method: 'get',
    params: query
  })
}

// 查询角色已授权用户列表
export function allocatedUserList(query) {
  return request({
    url: '/system/role/authUser/allocatedList',
    method: 'get',
    params: query
  })
}

// 查询角色未授权用户列表
export function unallocatedUserList(query) {
  return request({
    url: '/system/role/authUser/unallocatedList',
    method: 'get',
    params: query
  })
}

// 取消用户授权角色
export function authUserCancel(data) {
  return request({
    url: '/system/role/authUser/cancel',
    method: 'put',
    data: data
  })
}

// 批量取消用户授权角色
export function authUserCancelAll(data) {
  return request({
    url: '/system/role/authUser/cancelAll',
    method: 'put',
    params: data
  })
}

// 授权用户选择
export function authUserSelectAll(data) {
  return request({
    url: '/system/role/authUser/selectAll',
    method: 'put',
    params: data
  })
}
