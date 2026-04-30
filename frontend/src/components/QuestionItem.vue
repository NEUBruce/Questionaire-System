<template>
  <div class="question">
    <div class="q-top">
      <span class="q-title">{{ index }}.{{ question.problemName }}</span>
      <el-tag :type="question.mustAnswer ? 'danger' : 'info'" size="small">
        {{ question.mustAnswer ? '必答题' : '非必答题' }}
      </el-tag>
    </div>

    <!-- 单选 -->
    <el-radio-group v-if="question.type === '1'" v-model="modelValue.selected" :disabled="readonly">
      <div v-for="opt in question.option" :key="opt.id" class="option-row">
        <el-radio :value="String(opt.order)">{{ opt.chooseTerm }}</el-radio>
      </div>
    </el-radio-group>

    <!-- 多选 -->
    <el-checkbox-group v-else-if="question.type === '2'" v-model="modelValue.selected" :disabled="readonly">
      <div v-for="opt in question.option" :key="opt.id" class="option-row">
        <el-checkbox :value="String(opt.order)">{{ opt.chooseTerm }}</el-checkbox>
      </div>
    </el-checkbox-group>

    <!-- 填空 -->
    <el-input
      v-else-if="question.type === '3'"
      v-model="modelValue.text"
      type="textarea"
      :rows="3"
      placeholder="请输入答案"
      :disabled="readonly"
      style="width:50%"
    />

    <!-- 矩阵 -->
    <div v-else-if="question.type === '4'" class="matrix-wrap">
      <table class="matrix-table">
        <thead>
          <tr>
            <th></th>
            <th v-for="opt in question.option" :key="opt.id">{{ opt.chooseTerm }}</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(row, rowIdx) in leftTitles" :key="rowIdx">
            <td>{{ row }}</td>
            <td v-for="(opt, optIdx) in question.option" :key="optIdx">
              <el-radio
                v-model="modelValue.matrix[rowIdx]"
                :value="String(optIdx)"
                :disabled="readonly"
              />
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- 量表 -->
    <div v-else-if="question.type === '5'" class="gauge-wrap">
      <span class="gauge-label">{{ question.option[0]?.chooseTerm }}</span>
      <el-radio-group v-model="modelValue.selected" :disabled="readonly">
        <el-radio v-for="(opt, idx) in question.option" :key="idx" :value="String(idx)">
          {{ opt.fraction }}
        </el-radio>
      </el-radio-group>
      <span class="gauge-label">{{ question.option[question.option.length - 1]?.chooseTerm }}</span>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  question: Object,
  index:    Number,
  modelValue: Object,
  readonly: { type: Boolean, default: false }
})

const leftTitles = computed(() =>
  props.question.leftTitle ? props.question.leftTitle.split(',') : []
)
</script>

<style scoped>
.question {
  border: 1px solid #e7e7e7;
  border-radius: 6px;
  padding: 16px;
  margin-bottom: 16px;
}
.q-top { display: flex; align-items: center; gap: 10px; margin-bottom: 12px; }
.q-title { font-weight: 500; }
.option-row { margin-bottom: 6px; }
.matrix-table { border-collapse: collapse; }
.matrix-table th, .matrix-table td { border: 1px solid #ddd; padding: 8px 16px; text-align: center; }
.gauge-wrap { display: flex; align-items: center; gap: 12px; }
.gauge-label { color: #666; font-size: 13px; }
</style>
