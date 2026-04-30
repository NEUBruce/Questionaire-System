import request from './request'

export const addQuestion = (data) => request.post('/addQuestion', data)
export const queryQuestionList = (data) => request.post('/queryQuestionList', data)
export const queryTemplateQuestionList = (data) => request.post('/queryTemplateQuestionList', data)
export const searchTemplateQuestionList = (data) => request.post('/searchTemplateQuestionList', data)
