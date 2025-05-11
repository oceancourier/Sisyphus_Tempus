<script setup>
import { ref, onMounted } from 'vue'
import RegisterForm from './components/RegisterForm.vue'
import LoginForm from './components/LoginForm.vue'
import CheckInComponent from './components/CheckInComponent.vue'

// 当前用户
const currentUser = ref(null)

// 当前视图
const currentView = ref('login') // 'login', 'register', 'checkIn'

// 组件加载时检查是否已登录
onMounted(() => {
  const storedUser = localStorage.getItem('user')
  if (storedUser) {
    currentUser.value = JSON.parse(storedUser)
    currentView.value = 'checkIn'
  }
})

// 切换到注册视图
const switchToRegister = () => {
  currentView.value = 'register'
}

// 切换到登录视图
const switchToLogin = () => {
  currentView.value = 'login'
}

// 登录成功处理
const handleLoginSuccess = (user) => {
  currentUser.value = user
  currentView.value = 'checkIn'
}
</script>

<template>
  <header class="app-header">
    <div class="header-content">
      <div class="logo">
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="36" height="36" fill="currentColor">
          <path d="M12,20A8,8 0 0,0 20,12A8,8 0 0,0 12,4A8,8 0 0,0 4,12A8,8 0 0,0 12,20M12,2A10,10 0 0,1 22,12A10,10 0 0,1 12,22C6.47,22 2,17.5 2,12A10,10 0 0,1 12,2M12.5,7V12.25L17,14.92L16.25,16.15L11,13V7H12.5Z" />
        </svg>
      </div>
      <div class="title-container">
        <h1 class="main-title">Sisyphus Tempus</h1>
        <div class="subtitle">时间管理系统</div>
      </div>
    </div>
  </header>

  <main>
    <!-- 登录表单 -->
    <div v-if="currentView === 'login'" class="auth-container">
      <LoginForm @login-success="handleLoginSuccess" />
      <div class="switch-form">
        还没有账号？<a href="#" @click.prevent="switchToRegister">立即注册</a>
      </div>
    </div>
    
    <!-- 注册表单 -->
    <div v-else-if="currentView === 'register'" class="auth-container">
      <RegisterForm />
      <div class="switch-form">
        已有账号？<a href="#" @click.prevent="switchToLogin">立即登录</a>
      </div>
    </div>
    
    <!-- 打卡组件 -->
    <CheckInComponent v-else-if="currentView === 'checkIn'" />
  </main>
</template>

<style>
.app-header {
  background: linear-gradient(135deg, #1a2a6c, #2c3e50, #1a2a6c);
  color: white;
  padding: 20px 0;
  margin-bottom: 28px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  position: relative;
  overflow: hidden;
}

.app-header:before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 3px;
  background: linear-gradient(90deg, #4caf50, #2196F3, #4caf50);
  z-index: 5;
}

.header-content {
  max-width: 1280px;
  margin: 0 auto;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0 20px;
}

.logo {
  margin-right: 16px;
  display: flex;
  align-items: center;
  color: #75e1ff;
}

.title-container {
  text-align: center;
}

.main-title {
  margin: 0;
  font-size: 2.2rem;
  font-weight: 600;
  letter-spacing: 1px;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  font-family: 'Segoe UI', 'Helvetica Neue', Arial, sans-serif;
}

.subtitle {
  font-size: 1.1rem;
  opacity: 0.9;
  margin-top: 4px;
  font-weight: 300;
  letter-spacing: 0.5px;
}

@media (max-width: 600px) {
  .header-content {
    flex-direction: column;
  }
  
  .logo {
    margin-right: 0;
    margin-bottom: 10px;
  }
  
  .main-title {
    font-size: 1.8rem;
  }
  
  .subtitle {
    font-size: 1rem;
  }
}

.auth-container {
  max-width: 400px;
  margin: 0 auto;
}

.switch-form {
  text-align: center;
  margin-top: 15px;
}

a {
  color: #2196F3;
  text-decoration: none;
}

a:hover {
  text-decoration: underline;
}
</style>
