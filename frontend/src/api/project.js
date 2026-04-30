import request from './request'

export const queryProjectList = (data) => request.post('/queryProjectList', data)
export const addProjectInfo = (data) => request.post('/addProjectInfo', data)
export const modifyProjectInfo = (data) => request.post('/modifyProjectInfo', data)
export const deleteProjectById = (data) => request.post('/deleteProjectById', data)
