<template>
  <div>
    <AppHeader subtitle="创建问卷" />
    <div class="container">
      <el-card class="select-card">
        <template #header>选择创建方式</template>
        <div class="card-body">
          <el-card class="option-card" shadow="hover" @click="goCreate">
            <img src="/images/blank_template.png" class="card-img" />
            <div class="card-title">空白问卷</div>
            <div class="card-desc">从零开始创建问卷</div>
          </el-card>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script setup>
import { useRouter, useRoute } from 'vue-router'
import AppHeader from '@/components/AppHeader.vue'

const router = useRouter()
const route = useRoute()

function goCreate() {
  router.push({ path: '/create-new-questionnaire', query: { projectId: route.query.projectId, projectName: route.query.projectName } })
}
</script>

<style scoped>
.container { padding: 0 80px; }
.select-card { max-width: 600px; }
.card-body { display: flex; gap: 20px; }
.option-card { width: 180px; text-align: center; cursor: pointer; }
.card-img { width: 80px; height: 80px; object-fit: contain; margin-bottom: 8px; }
.card-title { font-weight: 600; margin-bottom: 4px; }
.card-desc { font-size: 12px; color: #999; }
</style>
