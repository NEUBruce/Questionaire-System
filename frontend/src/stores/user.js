import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useUserStore = defineStore('user', () => {
  const userInfo = ref(JSON.parse(localStorage.getItem('userInfo')) || null)

  function setUser(data) {
    userInfo.value = data
    localStorage.setItem('userInfo', JSON.stringify(data))
  }

  function clearUser() {
    userInfo.value = null
    localStorage.removeItem('userInfo')
  }

  return { userInfo, setUser, clearUser }
})
