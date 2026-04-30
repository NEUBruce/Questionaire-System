<template>
  <div class="page" v-loading="loading">
    <div class="sheet-wrap">
      <div class="q-header">
        <div class="q-title">{{ questionnaire.questionnaireName }}</div>
        <div class="q-desc">用途：{{ questionnaire.questionnaireDescription }}</div>
      </div>

      <el-form>
        <el-form-item label="答卷人姓名" required>
          <el-input v-model="answererName" placeholder="请输入您的姓名" style="width:240px" />
        </el-form-item>
      </el-form>

      <QuestionItem
        v-for="(q, idx) in questions"
        :key="q.id || idx"
        :question="q"
        :index="idx + 1"
        v-model="answers[idx]"
      />

      <div class="submit-row">
        <el-button type="primary" size="large" :loading="submitting" @click="onSubmit">提交问卷</el-button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import QuestionItem from '@/components/QuestionItem.vue'
import { queryQuestionnaireList } from '@/api/questionnaire'
import { addRecord, queryRecordList } from '@/api/record'

const router = useRouter()
const route = useRoute()
const loading = ref(false)
const submitting = ref(false)
const questionnaire = ref({})
const questions = ref([])
const answererName = ref('')
const answers = ref([])

onMounted(async () => {
  loading.value = true
  try {
    const id = route.query.id
    const isPreview = route.query.preview === '1'

    if (isPreview && route.query.questions) {
      questions.value = JSON.parse(route.query.questions)
      questionnaire.value = {
        questionnaireName: route.query.questionnaireName,
        questionnaireDescription: route.query.questionnaireDescription,
        id: route.query.questionnaireId
      }
    } else if (id) {
      const res = await queryQuestionnaireList({ id })
      const q = res.data?.[0]
      if (!q) { ElMessage.error('问卷不存在'); return }
      if (q.status === '0') { ElMessage.warning('该问卷尚未发布'); return }
      const now = Date.now()
      if (now < q.startTime || now > q.stopTime) { ElMessage.warning('该问卷不在答题时间内'); return }
      questionnaire.value = q
      questions.value = q.questionEntityList || []
    }
    answers.value = questions.value.map(q => ({
      selected: q.type === '2' ? [] : '',
      text: '',
      matrix: []
    }))
  } finally {
    loading.value = false
  }
})

async function onSubmit() {
  if (!answererName.value.trim()) { ElMessage.error('请输入答卷人姓名'); return }

  // 答题次数限制检查
  if (questionnaire.value.answerTimeLimit > 0) {
    const rRes = await queryRecordList({ questionnaireId: questionnaire.value.id, answeredBy: answererName.value })
    if (rRes.data && rRes.data.length >= questionnaire.value.answerTimeLimit) {
      ElMessage.error('您的回答次数已超过上限'); return
    }
  }

  // 必答题校验
  for (let i = 0; i < questions.value.length; i++) {
    const q = questions.value[i]
    const ans = answers.value[i]
    if (!q.mustAnswer) continue
    const empty = q.type === '1' || q.type === '5' ? !ans.selected
      : q.type === '2' ? ans.selected.length === 0
      : q.type === '3' ? !ans.text?.trim()
      : q.type === '4' ? ans.matrix.filter(Boolean).length === 0 : false
    if (empty) { ElMessage.error(`第 ${i + 1} 题为必答题，请作答`); return }
  }

  submitting.value = true
  try {
    const answerEntityList = []
    for (let i = 0; i < questions.value.length; i++) {
      const q = questions.value[i]
      const ans = answers.value[i]
      const qi = i + 1
      if (q.type === '1' || q.type === '5') {
        if (ans.selected) answerEntityList.push({ type: q.type, questionIndex: qi, chooseTerm: ans.selected })
      } else if (q.type === '2') {
        for (const v of ans.selected) answerEntityList.push({ type: q.type, questionIndex: qi, chooseTerm: v })
      } else if (q.type === '3') {
        if (ans.text) answerEntityList.push({ type: q.type, questionIndex: qi, chooseTerm: ans.text })
      } else if (q.type === '4') {
        ans.matrix.forEach((v, row) => {
          if (v) answerEntityList.push({ type: q.type, questionIndex: qi, chooseTerm: v, row })
        })
      }
    }
    const res = await addRecord({ answeredBy: answererName.value, questionnaireId: questionnaire.value.id, answerEntityList })
    if (res.code === '666') { ElMessage.success('提交成功！'); router.back() }
    else ElMessage.error(res.message)
  } finally {
    submitting.value = false
  }
}
</script>

<style scoped>
.page { min-height: 100vh; background: #f0f2f5; padding: 40px 20px; }
.sheet-wrap { max-width: 800px; margin: 0 auto; background: #fff; border-radius: 8px; padding: 32px; }
.q-header { text-align: center; margin-bottom: 24px; }
.q-title { font-size: 22px; font-weight: 700; }
.q-desc { color: #888; margin-top: 6px; }
.submit-row { text-align: center; margin-top: 24px; }
</style>
