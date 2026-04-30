<template>
  <div>
    <AppHeader :subtitle="isEdit ? '修改用户' : '创建用户'" />
    <div class="container">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="用户账号" prop="username">
          <el-input v-model="form.username" placeholder="请输入账号" />
        </el-form-item>
        <el-form-item label="用户密码" prop="password">
          <el-input v-model="form.password" placeholder="请输入密码" show-password />
        </el-form-item>
        <el-form-item label="开始时间" prop="startTime">
          <el-date-picker v-model="form.startTime" type="datetime" placeholder="请选择开始时间" style="width:100%" />
        </el-form-item>
        <el-form-item label="结束时间" prop="stopTime">
          <el-date-picker v-model="form.stopTime" type="datetime" placeholder="请选择结束时间" style="width:100%" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :loading="loading" @click="onSubmit">{{ isEdit ? '保存修改' : '创建用户' }}</el-button>
          <el-button @click="router.back()">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import AppHeader from '@/components/AppHeader.vue'
import { addUser, modifyUserInfo } from '@/api/user'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()

const isEdit = computed(() => !!route.query.id)
const formRef = ref()
const loading = ref(false)
const form = ref({ username: '', password: '', startTime: null, stopTime: null })

const rules = {
  username: [{ required: true, message: '账号不能为空', trigger: 'blur' }],
  password: [{ required: true, message: '密码不能为空', trigger: 'blur' }],
  startTime: [{ required: true, message: '请选择开始时间', trigger: 'change' }],
  stopTime:  [{ required: true, message: '请选择结束时间', trigger: 'change' }]
}

onMounted(() => {
  if (isEdit.value) {
    form.value = {
      username:  route.query.username  || '',
      password:  route.query.password  || '',
      startTime: route.query.startTime ? new Date(Number(route.query.startTime)) : null,
      stopTime:  route.query.stopTime  ? new Date(Number(route.query.stopTime))  : null
    }
  }
})

async function onSubmit() {
  await formRef.value.validate()
  if (new Date(form.value.startTime) > new Date(form.value.stopTime)) {
    ElMessage.error('开始时间不能晚于结束时间')
    return
  }
  loading.value = true
  try {
    const user = userStore.userInfo
    const params = {
      ...form.value,
      startTime: new Date(form.value.startTime).getTime(),
      stopTime:  new Date(form.value.stopTime).getTime(),
      lastUpdatedBy: user.username
    }
    if (isEdit.value) params.id = route.query.id
    else params.createdBy = user.username

    const res = await (isEdit.value ? modifyUserInfo(params) : addUser(params))
    if (res.code === '666') {
      ElMessage.success(isEdit.value ? '修改成功' : '创建成功')
      router.push('/user')
    } else {
      ElMessage.error(res.message)
    }
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.container { max-width: 600px; margin: 0 auto; padding: 0 20px; }
</style>
