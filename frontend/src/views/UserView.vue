<template>
  <div>
    <AppHeader subtitle="用户管理" />
    <div class="container">
      <div class="top">
        <div class="top-left">
          <el-button type="primary" @click="router.push('/create-user')">创建用户</el-button>
          <el-input v-model="searchName" placeholder="请输入用户名" style="width:200px;margin-left:12px" clearable @keyup.enter="fetchList">
            <template #append><el-button @click="fetchList">搜索</el-button></template>
          </el-input>
        </div>
      </div>

      <el-table :data="userList" border stripe v-loading="loading">
        <el-table-column type="index" label="序号" width="60" />
        <el-table-column prop="username" label="用户账号" />
        <el-table-column prop="password" label="用户密码" />
        <el-table-column label="开始时间">
          <template #default="{ row }">{{ formatDate(row.startTime) }}</template>
        </el-table-column>
        <el-table-column label="结束时间">
          <template #default="{ row }">{{ formatDate(row.stopTime) }}</template>
        </el-table-column>
        <el-table-column label="操作" width="240">
          <template #default="{ row }">
            <el-button link type="primary" @click="onResetPassword(row)">重置密码</el-button>
            <el-button link type="primary" @click="router.push({ path: '/create-user', query: { id: row.id, username: row.username, password: row.password, startTime: row.startTime, stopTime: row.stopTime } })">编辑</el-button>
            <el-button link type="danger" @click="onCloseUser(row)">关闭</el-button>
            <el-button link type="danger" @click="onDeleteUser(row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination">
        <el-pagination
          v-model:current-page="pageNum"
          :page-size="10"
          layout="prev, pager, next, jumper, total"
          :total="total"
          @current-change="fetchList"
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import AppHeader from '@/components/AppHeader.vue'
import { queryUserList, deleteUserinfo, modifyUserInfo } from '@/api/user'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const userStore = useUserStore()
const loading = ref(false)
const searchName = ref('')
const userList = ref([])
const pageNum = ref(1)
const total = ref(0)

function formatDate(ts) {
  if (!ts) return '-'
  return new Date(ts).toLocaleString()
}

async function fetchList() {
  loading.value = true
  try {
    const res = await queryUserList({ username: searchName.value, pageNum: pageNum.value, pageSize: 10 })
    userList.value = res.code === '666' ? res.data : []
    total.value = userList.value.length
  } finally {
    loading.value = false
  }
}

async function onResetPassword(row) {
  await ElMessageBox.confirm(`确认将 ${row.username} 的密码重置为 123？`, '提示', { type: 'warning' })
  const res = await modifyUserInfo({ ...row, password: '123', lastUpdatedBy: userStore.userInfo.username })
  if (res.code === '666') { ElMessage.success('密码已重置为 123'); fetchList() }
  else ElMessage.error(res.message)
}

async function onCloseUser(row) {
  await ElMessageBox.confirm(`确认关闭用户 ${row.username}？`, '提示', { type: 'warning' })
  const res = await modifyUserInfo({ ...row, status: '0', lastUpdatedBy: userStore.userInfo.username })
  if (res.code === '666') { ElMessage.success('已关闭'); fetchList() }
  else ElMessage.error(res.message)
}

async function onDeleteUser(id) {
  await ElMessageBox.confirm('确认删除该用户？', '提示', { type: 'warning' })
  const res = await deleteUserinfo({ id })
  if (res.code === '666') { ElMessage.success('删除成功'); fetchList() }
  else ElMessage.error(res.message)
}

onMounted(fetchList)
</script>

<style scoped>
.container { padding: 0 80px; }
.top { display: flex; justify-content: space-between; margin-bottom: 20px; }
.top-left { display: flex; align-items: center; }
.pagination { margin-top: 20px; display: flex; justify-content: flex-end; }
</style>
