<template>
  <div>
    <AppHeader subtitle="创建调查问卷" />
    <div class="container">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="110px">
        <el-form-item label="调查名称" prop="questionnaireName">
          <el-input v-model="form.questionnaireName" placeholder="请输入调查名称" />
        </el-form-item>
        <el-form-item label="调查说明" prop="questionnaireDescription">
          <el-input v-model="form.questionnaireDescription" type="textarea" :rows="4" placeholder="请输入调查说明" />
        </el-form-item>
        <el-form-item label="开始时间" prop="startTime">
          <el-date-picker v-model="form.startTime" type="date" placeholder="请选择开始时间" style="width:100%" />
        </el-form-item>
        <el-form-item label="结束时间" prop="stopTime">
          <el-date-picker v-model="form.stopTime" type="date" placeholder="请选择结束时间" style="width:100%" />
        </el-form-item>
        <el-form-item label="答卷次数" prop="answerTimeLimit">
          <el-input-number v-model="form.answerTimeLimit" :min="1" />
        </el-form-item>
        <el-form-item label="调查范围" prop="target">
          <el-select v-model="form.target" placeholder="请选择" style="width:180px;margin-right:12px" @change="onTargetChange">
            <el-option label="面向公众" value="面向公众" />
            <el-option label="面向指定群组" value="面向指定群组" />
          </el-select>
          <el-select v-if="groupOptions.length" v-model="form.group" placeholder="请选择群组" style="width:180px">
            <el-option v-for="o in groupOptions" :key="o.value" :label="o.label" :value="o.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="问卷风格" prop="style">
          <el-select v-model="form.style" placeholder="请选择" style="width:180px">
            <el-option label="简约" value="简约" />
            <el-option label="科技" value="科技" />
            <el-option label="几何" value="几何" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :loading="loading" @click="onSubmit">立即创建</el-button>
          <el-button @click="router.back()">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import AppHeader from '@/components/AppHeader.vue'
import { addQuestionnaire } from '@/api/questionnaire'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()
const formRef = ref()
const loading = ref(false)

const form = ref({
  questionnaireName: '', questionnaireDescription: '',
  startTime: null, stopTime: null, answerTimeLimit: 1,
  target: '面向公众', group: '', style: '简约'
})

const rules = {
  questionnaireName: [{ required: true, message: '调查名称不能为空', trigger: 'blur' }],
  questionnaireDescription: [{ required: true, message: '调查说明不能为空', trigger: 'blur' }],
  startTime: [{ required: true, message: '请选择开始时间', trigger: 'change' }],
  stopTime:  [{ required: true, message: '请选择结束时间', trigger: 'change' }]
}

const groupOptions = computed(() => {
  if (form.value.target !== '面向指定群组') return []
  return [
    { value: '计院学生', label: '计院学生' },
    { value: '软院学生', label: '软院学生' },
    { value: '计院老师', label: '计院老师' },
    { value: '软院老师', label: '软院老师' }
  ]
})

function onTargetChange() { form.value.group = '' }

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
      projectId: route.query.projectId,
      createdBy: user.username,
      lastUpdatedBy: user.username
    }
    const res = await addQuestionnaire(params)
    if (res.code === '666') {
      router.push({ path: '/design-questionnaire', query: { questionnaireId: res.data.id, questionnaireName: res.data.questionnaireName, questionnaireDescription: res.data.questionnaireDescription } })
    } else {
      ElMessage.error(res.message)
    }
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.container { max-width: 700px; margin: 0 auto; padding: 0 20px; }
</style>
