import request from './request'

export const addRecord = (data) => request.post('/addRecord', data)
export const queryRecordList = (data) => request.post('/queryRecordList', data)
