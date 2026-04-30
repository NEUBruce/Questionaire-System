import request from './request'

export const login = (data) => request.post('/admin/userLogin', data)
export const queryUserList = (data) => request.post('/admin/queryUserList', data)
export const addUser = (data) => request.post('/admin/addUser', data)
export const modifyUserInfo = (data) => request.post('/admin/modifyUserInfo', data)
export const deleteUserinfo = (data) => request.post('/admin/deleteUserinfo', data)
