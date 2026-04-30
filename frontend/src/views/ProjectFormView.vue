<template>
  <div>
    <AppHeader :subtitle="isEdit ? '编辑项目' : '创建项目'" />
    <div class="container">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="项目名称" prop="projectName">
          <el-input v-model="form.projectName" placeholder="请输入项目名称" />
        </el-form-item>
        <el-form-item label="项目描述" prop="projectContent">
          <el-input
            v-model="form.projectContent"
            type="textarea"
            :rows="4"
            placeholder="请输入项目描述"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :loading="loading" @click="onSubmit">
            {{ isEdit ? '保存修改' : '创建项目' }}
          </el-button>
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
import { addProjectInfo, modifyProjectInfo } from '@/api/project'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()

const isEdit = computed(() => !!route.query.id)
const formRef = ref()
const loading = ref(false)

const form = ref({ projectName: '', projectContent: '' })
const rules = {
  projectName:   [{ required: true, message: '项目名称不能为空', trigger: 'blur' }],
  projectContent: [{ required: true, message: '项目描述不能为空', trigger: 'blur' }]
}

onMounted(() => {
  if (isEdit.value) {
    form.value.projectName    = route.query.projectName    || ''
    form.value.projectContent = route.query.projectContent || ''
  }
})

async function onSubmit() {
  await formRef.value.validate()
  loading.value = true
  try {
    const user = userStore.userInfo
    const params = isEdit.value
      ? { id: route.query.id, ...form.value }
      : { userId: user.id, createdBy: user.username, lastUpdatedBy: user.username, ...form.value }

    const res = await (isEdit.value ? modifyProjectInfo(params) : addProjectInfo(params))
    if (res.code === '666') {
      ElMessage.success(isEdit.value ? '修改成功' : '创建成功')
      router.push('/questionnaire')
    } else {
      ElMessage.error(res.message)
    }
  } catch {
    ElMessage.error('操作失败，请稍后重试')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.container {
  max-width: 700px;
  margin: 0 auto;
  padding: 0 20px;
}
</style>
