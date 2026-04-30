<template>
  <div>
    <AppHeader :subtitle="route.query.questionnaireName" />
    <div class="layout">
      <!-- 左侧题型工具栏 -->
      <div class="sidebar">
        <div class="sidebar-title">添加题目</div>
        <el-button class="add-btn" @click="addQuestion('1')">+ 单选题</el-button>
        <el-button class="add-btn" @click="addQuestion('2')">+ 多选题</el-button>
        <el-button class="add-btn" @click="addQuestion('3')">+ 填空题</el-button>
        <el-button class="add-btn" @click="addQuestion('4')">+ 矩阵题</el-button>
        <el-button class="add-btn" @click="addQuestion('5')">+ 量表题</el-button>
        <el-divider />
        <el-button class="add-btn" @click="onPreview">预览</el-button>
        <el-button class="add-btn" type="primary" @click="onSave" :loading="saving">保存完成</el-button>
      </div>

      <!-- 中间问卷编辑区 -->
      <div class="editor">
        <div class="questionnaire-header">
          <div class="q-name">{{ route.query.questionnaireName }}</div>
          <div class="q-desc">{{ route.query.questionnaireDescription }}</div>
        </div>

        <el-empty v-if="questions.length === 0" description="从左侧添加题目" />

        <div v-for="(q, idx) in questions" :key="q._id" class="question-card">
          <div class="q-card-header">
            <span class="q-index">题目 {{ idx + 1 }} · {{ typeLabel(q.type) }}</span>
            <div class="q-actions">
              <el-button link @click="moveUp(idx)" :disabled="idx === 0">上移</el-button>
              <el-button link @click="moveDown(idx)" :disabled="idx === questions.length - 1">下移</el-button>
              <el-button link type="danger" @click="questions.splice(idx, 1)">删除</el-button>
            </div>
          </div>

          <!-- 题目名称 -->
          <el-input v-model="q.problemName" placeholder="请输入题目内容" style="margin-bottom:10px" />

          <!-- 必答开关 -->
          <el-switch v-model="q.mustAnswer" active-text="必答" inactive-text="非必答" style="margin-bottom:12px" />

          <!-- 单选 / 多选 / 量表 选项 -->
          <div v-if="['1','2','5'].includes(q.type)">
            <div v-for="(opt, oi) in q.option" :key="oi" class="option-row">
              <el-input v-model="opt.chooseTerm" :placeholder="q.type === '5' ? '选项文字' : `选项 ${oi + 1}`" style="flex:1" />
              <el-input v-if="q.type === '5'" v-model="opt.fraction" placeholder="分数" style="width:80px;margin-left:8px" />
              <el-button link type="danger" @click="q.option.splice(oi, 1)" style="margin-left:8px">删除</el-button>
            </div>
            <el-button link type="primary" @click="q.option.push({ chooseTerm: '', fraction: '' })">+ 添加选项</el-button>
          </div>

          <!-- 矩阵左标题 -->
          <div v-if="q.type === '4'">
            <el-input v-model="q.leftTitle" placeholder="行标题，用逗号分隔，如：CCTV1,CCTV2" style="margin-bottom:10px" />
            <div v-for="(opt, oi) in q.option" :key="oi" class="option-row">
              <el-input v-model="opt.chooseTerm" :placeholder="`列选项 ${oi + 1}`" style="flex:1" />
              <el-button link type="danger" @click="q.option.splice(oi, 1)" style="margin-left:8px">删除</el-button>
            </div>
            <el-button link type="primary" @click="q.option.push({ chooseTerm: '' })">+ 添加列选项</el-button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import AppHeader from '@/components/AppHeader.vue'
import { addQuestion as apiAddQuestion } from '@/api/question'

const router = useRouter()
const route = useRoute()
const saving = ref(false)
const questions = ref([])
let _id = 0

const TYPE_LABELS = { '1': '单选题', '2': '多选题', '3': '填空题', '4': '矩阵题', '5': '量表题' }
const typeLabel = (t) => TYPE_LABELS[t] || '未知'

function addQuestion(type) {
  questions.value.push({
    _id: ++_id,
    type,
    problemName: '',
    mustAnswer: true,
    leftTitle: '',
    option: type === '3' ? [] : [{ chooseTerm: '', fraction: '' }]
  })
}

function moveUp(idx) {
  if (idx === 0) return
  const arr = questions.value
  ;[arr[idx - 1], arr[idx]] = [arr[idx], arr[idx - 1]]
}

function moveDown(idx) {
  const arr = questions.value
  if (idx === arr.length - 1) return
  ;[arr[idx], arr[idx + 1]] = [arr[idx + 1], arr[idx]]
}

function onPreview() {
  router.push({
    path: '/answer-sheet',
    query: {
      preview: '1',
      questionnaireName: route.query.questionnaireName,
      questionnaireDescription: route.query.questionnaireDescription,
      questionnaireId: route.query.questionnaireId,
      questions: JSON.stringify(questions.value)
    }
  })
}

async function onSave() {
  for (const q of questions.value) {
    if (!q.problemName.trim()) { ElMessage.error('存在未填写内容的题目'); return }
  }
  saving.value = true
  try {
    const questionnaireId = route.query.questionnaireId
    for (let i = 0; i < questions.value.length; i++) {
      const q = questions.value[i]
      await apiAddQuestion({ ...q, questionnaireId, order: i })
    }
    ElMessage.success('保存成功')
    router.push('/questionnaire')
  } catch {
    ElMessage.error('保存失败，请重试')
  } finally {
    saving.value = false
  }
}
</script>

<style scoped>
.layout { display: flex; min-height: calc(100vh - 120px); }

.sidebar {
  width: 160px;
  background: #f7f8fa;
  border-right: 1px solid #e7e7e7;
  padding: 20px 12px;
  display: flex;
  flex-direction: column;
  gap: 8px;
  flex-shrink: 0;
}
.sidebar-title { font-weight: 600; margin-bottom: 4px; color: #333; }
.add-btn { width: 100%; display: flex; justify-content: center; }

.editor { flex: 1; padding: 24px 40px; }

.questionnaire-header { text-align: center; margin-bottom: 24px; }
.q-name { font-size: 22px; font-weight: 700; }
.q-desc { color: #888; margin-top: 6px; }

.question-card {
  border: 1px solid #e7e7e7;
  border-radius: 8px;
  padding: 16px;
  margin-bottom: 16px;
  background: #fff;
}
.q-card-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 12px; }
.q-index { font-weight: 600; color: #555; }
.q-actions { display: flex; gap: 4px; }

.option-row { display: flex; align-items: center; margin-bottom: 8px; }
</style>
