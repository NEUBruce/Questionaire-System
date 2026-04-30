import axios from 'axios'

const request = axios.create({
  baseURL: '/api',
  timeout: 10000,
  headers: { 'Content-Type': 'application/json', Accept: 'application/json' }
})

request.interceptors.response.use(
  res => res.data,
  err => Promise.reject(err)
)

export default request
