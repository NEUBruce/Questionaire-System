<template>
  <div>
    <AppHeader subtitle="项目详情" />
    <div class="container">
      <el-descriptions :column="2" border class="info-card">
        <el-descriptions-item label="项目名称">{{ project.projectName }}</el-descriptions-item>
        <el-descriptions-item label="负责人">{{ project.createdBy }}</el-descriptions-item>
        <el-descriptions-item label="创建时间">{{ formatDate(project.creationDate) }}</el-descriptions-item>
        <el-descriptions-item label="项目描述">{{ project.projectContent }}</el-descriptions-item>
      </el-descriptions>

      <el-table :data="questionnaireList" border stripe v-loading="loading" style="margin-top:24px">
        <el-table-column type="index" label="序号" width="60" />
        <el-table-column prop="questionnaireName" label="问卷名称" />
        <el-table-column label="创建时间">
          <template #default="{ row }">{{ formatDate(row.creationDate) }}</template>
        </el-table-column>
        <el-table-column label="操作" width="240">
          <template #default="{ row }">
            <el-button link type="primary" @click="onPreview(row)">预览</el-button>
            <el-button link type="primary" @click="onRelease(row)">发布</el-button>
            <el-button link type="danger" @click="onDelete(row)">删除</el-button>
            <el-button link type="primary" @click="router.push({ path: '/see-questionnaire', query: { projectId: row.projectId } })">统计</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import AppHeader from '@/components/AppHeader.vue'
import { queryProjectList } from '@/api/project'
import { queryQuestionnaireList, deleteQuestionnaire, modifyQuestionnaireInfo } from '@/api/questionnaire'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()
const loading = ref(false)
const project = ref({})
const questionnaireList = ref([])

function formatDate(ts) {
  if (!ts) return '-'
  return new Date(ts).toLocaleString()
}

async function fetchData() {
  loading.value = true
  try {
    const id = route.query.id
    const [projRes, qRes] = await Promise.all([
      queryProjectList({ id, createdBy: userStore.userInfo.username }),
      queryQuestionnaireList({ projectId: id })
    ])
    project.value = projRes.data?.[0] || {}
    questionnaireList.value = qRes.code === '666' ? qRes.data : []
  } finally {
    loading.value = false
  }
}

function onPreview(row) {
  router.push({ path: '/answer-sheet', query: { id: row.id, preview: '1' } })
}

async function onRelease(row) {
  await ElMessageBox.confirm('确认发布该问卷？发布后用户可通过链接访问。', '提示', { type: 'info' })
  const res = await modifyQuestionnaireInfo({ id: row.id, status: '1' })
  if (res.code === '666') {
    ElMessage.success(`发布成功！链接：http://localhost:3000/answer-sheet?id=${row.id}`)
    fetchData()
  } else {
    ElMessage.error(res.message)
  }
}

async function onDelete(row) {
  const now = Date.now()
  if (row.status === '1' && now >= row.startTime && now <= row.stopTime) {
    ElMessage.error('该问卷正在发布中，无法删除')
    return
  }
  await ElMessageBox.confirm('确认删除该问卷？', '提示', { type: 'warning' })
  const res = await deleteQuestionnaire({ id: row.id })
  if (res.code === '666') { ElMessage.success('删除成功'); fetchData() }
  else ElMessage.error(res.message)
}

onMounted(fetchData)
</script>

<style scoped>
.container { padding: 0 80px; }
.info-card { margin-bottom: 8px; }
</style>
