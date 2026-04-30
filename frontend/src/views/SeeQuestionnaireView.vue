<template>
  <div>
    <AppHeader subtitle="问卷详情" />
    <div class="container">
      <div class="top">
        <el-input v-model="searchName" placeholder="按答卷人搜索" style="width:220px" clearable @keyup.enter="fetchData">
          <template #append><el-button @click="fetchData">搜索</el-button></template>
        </el-input>
      </div>
      <el-table :data="recordList" border stripe v-loading="loading" id="recordList">
        <el-table-column prop="questionnaireName" label="问卷名称" />
        <el-table-column prop="answeredBy" label="答卷人" />
        <el-table-column label="答卷时间">
          <template #default="{ row }">{{ formatDate(row.answerDate) }}</template>
        </el-table-column>
        <el-table-column label="操作" width="100">
          <template #default="{ row }">
            <el-button link type="primary" @click="router.push({ path: '/see-detail', query: { recordId: row.id } })">明细</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-empty v-if="!loading && recordList.length === 0" description="暂无答卷记录" />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import AppHeader from '@/components/AppHeader.vue'
import { queryQuestionnaireList } from '@/api/questionnaire'
import { queryRecordList } from '@/api/record'

const router = useRouter()
const route = useRoute()
const loading = ref(false)
const searchName = ref('')
const recordList = ref([])

function formatDate(ts) {
  if (!ts) return '-'
  return new Date(ts).toLocaleString()
}

async function fetchData() {
  loading.value = true
  recordList.value = []
  try {
    const qRes = await queryQuestionnaireList({ projectId: route.query.projectId })
    const questionnaires = qRes.code === '666' ? qRes.data : []
    for (const q of questionnaires) {
      const rRes = await queryRecordList({ questionnaireId: q.id, answeredBy: searchName.value })
      if (rRes.code === '666') {
        for (const r of rRes.data) {
          recordList.value.push({ ...r, questionnaireName: q.questionnaireName })
        }
      }
    }
  } finally {
    loading.value = false
  }
}

onMounted(fetchData)
</script>

<style scoped>
.container { padding: 0 80px; }
.top { margin-bottom: 20px; }
</style>
