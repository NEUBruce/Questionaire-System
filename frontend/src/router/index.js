import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/stores/user'

const routes = [
  { path: '/', redirect: '/login' },
  { path: '/login', component: () => import('@/views/LoginView.vue'), meta: { public: true } },
  { path: '/questionnaire', component: () => import('@/views/QuestionnaireView.vue') },
  { path: '/create-project', component: () => import('@/views/ProjectFormView.vue') },
  { path: '/edit-project', component: () => import('@/views/ProjectFormView.vue') },
  { path: '/user', component: () => import('@/views/UserView.vue') },
  { path: '/create-user', component: () => import('@/views/UserFormView.vue') },
  { path: '/see-project', component: () => import('@/views/SeeProjectView.vue') },
  { path: '/create-questionnaire', component: () => import('@/views/CreateQuestionnaireView.vue') },
  { path: '/create-new-questionnaire', component: () => import('@/views/QuestionnaireFormView.vue') },
  { path: '/design-questionnaire', component: () => import('@/views/DesignQuestionnaireView.vue') },
  { path: '/see-questionnaire', component: () => import('@/views/SeeQuestionnaireView.vue') },
  { path: '/see-detail', component: () => import('@/views/SeeDetailView.vue') },
  { path: '/answer-sheet', component: () => import('@/views/AnswerSheetView.vue'), meta: { public: true } }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to) => {
  const userStore = useUserStore()
  if (to.path === '/login' && userStore.userInfo) {
    return '/questionnaire'
  }
  if (!to.meta.public && !userStore.userInfo) {
    return '/login'
  }
})

export default router
