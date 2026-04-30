<template>
  <div>
    <AppHeader subtitle="答卷明细" />
    <div class="container" v-loading="loading">
      <div class="q-header">
        <div class="q-title">{{ questionnaire.questionnaireName }}</div>
        <div class="q-desc">用途：{{ questionnaire.questionnaireDescription }}</div>
        <div class="answerer">答卷人：{{ record.answeredBy }}</div>
      </div>
      <QuestionItem
        v-for="(q, idx) in questions"
        :key="q.id"
        :question="q"
        :index="idx + 1"
        :modelValue="answers[idx + 1] || {}"
        :readonly="true"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import AppHeader from '@/components/AppHeader.vue'
import QuestionItem from '@/components/QuestionItem.vue'
import { queryRecordList } from '@/api/record'
import { queryQuestionnaireList } from '@/api/questionnaire'

const route = useRoute()
const loading = ref(false)
const record = ref({})
const questionnaire = ref({})
const questions = ref([])
const answers = ref({})

onMounted(async () => {
  loading.value = true
  try {
    const rRes = await queryRecordList({ id: route.query.recordId })
    record.value = rRes.data?.[0] || {}

    const qRes = await queryQuestionnaireList({ id: record.value.questionnaireId })
    questionnaire.value = qRes.data?.[0] || {}
    questions.value = questionnaire.value.questionEntityList || []

    parseAnswers(record.value.answerEntityList || [])
  } finally {
    loading.value = false
  }
})

function parseAnswers(list) {
  const map = {}
  for (const item of list) {
    const qi = item.questionIndex
    if (!map[qi]) map[qi] = { selected: '', text: '', matrix: [] }
    if (item.type === '1' || item.type === '5') {
      map[qi].selected = item.chooseTerm
    } else if (item.type === '2') {
      if (!Array.isArray(map[qi].selected)) map[qi].selected = []
      map[qi].selected.push(item.chooseTerm)
    } else if (item.type === '3') {
      map[qi].text = item.chooseTerm
    } else if (item.type === '4') {
      map[qi].matrix[item.row] = item.chooseTerm
    }
  }
  answers.value = map
}
</script>

<style scoped>
.container { max-width: 800px; margin: 0 auto; padding: 0 20px; }
.q-header { text-align: center; margin-bottom: 24px; }
.q-title { font-size: 22px; font-weight: 700; }
.q-desc { color: #888; margin-top: 6px; }
.answerer { margin-top: 8px; color: #555; }
</style>
