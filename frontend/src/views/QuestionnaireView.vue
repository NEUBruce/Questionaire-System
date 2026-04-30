<template>
  <div>
    <AppHeader />
    <div class="container">
      <div class="list-top">
        <div class="list-top-left">
          <el-button type="primary" @click="router.push('/create-project')">创建项目</el-button>
          <el-input
            v-model="searchName"
            placeholder="请输入项目名称"
            style="width: 220px; margin-left: 16px"
            clearable
            @keyup.enter="fetchData"
          >
            <template #append>
              <el-button @click="fetchData">搜索</el-button>
            </template>
          </el-input>
        </div>
        <el-button type="primary" @click="router.push('/see-questionnaire')">历史问卷</el-button>
      </div>

      <div v-loading="loading">
        <div v-for="project in projectList" :key="project.id" class="list">
          <div class="list-header">
            <span>{{ project.projectName }}</span>
            <div>
              <el-button link type="primary" @click="onCreateQuestionnaire(project)">创建问卷</el-button>
              <el-button link type="primary" @click="router.push({ path: '/see-project', query: { id: project.id } })">查看</el-button>
              <el-button link type="primary" @click="onEditProject(project)">编辑</el-button>
              <el-button link type="danger" @click="onDelProject(project.id)">删除</el-button>
              <el-button link type="primary" @click="onStatistics(project.id)">统计</el-button>
            </div>
          </div>
          <div v-if="questionnaireMap[project.id]?.length">
            <div
              v-for="q in questionnaireMap[project.id]"
              :key="q.id"
              class="questionnaire-name"
            >
              问卷名称：{{ q.questionnaireName }}
            </div>
          </div>
          <div v-else class="list-footer">暂无调查问卷或问卷已过期</div>
        </div>
        <el-empty v-if="!loading && projectList.length === 0" description="暂无项目" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import AppHeader from '@/components/AppHeader.vue'
import { queryProjectList, deleteProjectById } from '@/api/project'
import { queryQuestionnaireList } from '@/api/questionnaire'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const userStore = useUserStore()

const loading = ref(false)
const searchName = ref('')
const projectList = ref([])
const questionnaireMap = ref({})

async function fetchData() {
  loading.value = true
  try {
    const params = { createdBy: userStore.userInfo.username, projectName: searchName.value }

    const [projRes, qRes] = await Promise.all([
      queryProjectList(params),
      queryQuestionnaireList(params)
    ])

    projectList.value = projRes.code === '666' ? projRes.data : []

    const map = {}
    if (qRes.code === '666') {
      for (const q of qRes.data) {
        if (!map[q.projectId]) map[q.projectId] = []
        map[q.projectId].push(q)
      }
    }
    questionnaireMap.value = map
  } catch {
    ElMessage.error('加载失败，请刷新重试')
  } finally {
    loading.value = false
  }
}

function onCreateQuestionnaire(project) {
  router.push({ path: '/create-questionnaire', query: { projectId: project.id, projectName: project.projectName } })
}

function onEditProject(project) {
  router.push({ path: '/edit-project', query: { id: project.id, projectName: project.projectName, projectContent: project.projectContent } })
}

async function onStatistics(projectId) {
  const res = await queryQuestionnaireList({ projectId })
  if (res.code === '666') {
    router.push({ path: '/see-questionnaire', query: { projectId } })
  }
}

async function onDelProject(id) {
  await ElMessageBox.confirm('确认删除该项目吗？', '提示', { type: 'warning' })
  const res = await deleteProjectById({ id })
  if (res.code === '666') {
    ElMessage.success('删除成功')
    fetchData()
  } else {
    ElMessage.error(res.message)
  }
}

onMounted(fetchData)
</script>

<style scoped>
.container { padding: 0 80px; }

.list-top {
  border-bottom: 1px solid #e3e3e6;
  padding-bottom: 35px;
  margin-bottom: 35px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.list-top-left { display: flex; align-items: center; }

.list {
  border: 1px solid #E7E7E7;
  margin-bottom: 30px;
}

.list-header {
  background-color: #F5F5F5;
  padding: 10px 16px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.list-footer {
  color: red;
  height: 40px;
  line-height: 40px;
  text-align: center;
}

.questionnaire-name {
  background-color: #fff;
  padding: 10px;
  border-top: 1px solid #ccc;
  text-align: center;
}
</style>
