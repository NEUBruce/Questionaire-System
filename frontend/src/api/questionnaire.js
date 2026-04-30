import request from './request'

export const queryQuestionnaireList = (data) => request.post('/queryQuestionnaireList', data)
export const addQuestionnaire = (data) => request.post('/addQuestionnaire', data)
export const modifyQuestionnaireInfo = (data) => request.post('/modifyQuestionnaireInfo', data)
export const deleteQuestionnaire = (data) => request.post('/deleteQuestionnaire', data)
