import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useUserStore = defineStore('user', () => {
  const userInfo = ref(JSON.parse(localStorage.getItem('userInfo')) || null)
  const token = ref(localStorage.getItem('token') || null)

  function setUser(data, jwt) {
    userInfo.value = data
    token.value = jwt
    localStorage.setItem('userInfo', JSON.stringify(data))
    localStorage.setItem('token', jwt)
  }

  function clearUser() {
    userInfo.value = null
    token.value = null
    localStorage.removeItem('userInfo')
    localStorage.removeItem('token')
  }

  return { userInfo, token, setUser, clearUser }
})
