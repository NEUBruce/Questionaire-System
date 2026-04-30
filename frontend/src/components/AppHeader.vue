<template>
  <div class="header">
    <div class="div-top">
      <div class="header-left">
        <div v-if="showBack" class="back-btn" @click="router.back()">
          <img class="header-img" src="/images/fallback.png" />
          <span>返回</span>
        </div>
      </div>
      <div class="header-right">
        <div class="nav-item" @click="goMyProject">
          <img class="header-img" src="/images/my.png" />
          <span>我的项目</span>
        </div>
        <div class="nav-item user-item">
          <img class="header-img" src="/images/avatar.png" />
          <span class="username">{{ userStore.userInfo?.username }}</span>
          <span class="user-manage" @click="goUserManage">用户管理</span>
        </div>
        <div class="nav-item" @click="onSignOut">
          <img class="header-img" src="/images/sign_out.png" />
          <span>退出</span>
        </div>
      </div>
    </div>
    <div v-if="subtitle" class="div-bottom">{{ subtitle }}</div>
  </div>
</template>

<script setup>
import { useRouter, useRoute } from 'vue-router'
import { useUserStore } from '@/stores/user'

const props = defineProps({
  subtitle: { type: String, default: '' }
})

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()

const showBack = route.path !== '/questionnaire'

function goMyProject() {
  if (route.path !== '/questionnaire') router.push('/questionnaire')
}

function goUserManage() {
  if (route.path !== '/user') router.push('/user')
}

function onSignOut() {
  userStore.clearUser()
  router.push('/login')
}
</script>

<style scoped>
.header { margin-bottom: 50px; }

.div-top {
  height: 50px;
  background-color: #0078C8;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 80px;
}

.header-left { color: #fff; }

.back-btn {
  display: flex;
  align-items: center;
  cursor: pointer;
  gap: 8px;
}

.header-right {
  display: flex;
  align-items: center;
  color: #fff;
}

.nav-item {
  display: flex;
  align-items: center;
  padding: 0 15px;
  cursor: pointer;
  gap: 4px;
}

.nav-item:first-child { border-right: 1px solid #fff; }

.user-item { gap: 0; }

.username {
  border-right: 1px solid #fff;
  padding: 0 10px 0 5px;
  margin-right: 8px;
}

.user-manage { cursor: pointer; }

.header-img { width: 30px; height: 30px; }

.div-bottom {
  color: #3EAAF5;
  background-color: #F7F8F8;
  padding: 15px 80px;
  font-size: 18px;
}
</style>
