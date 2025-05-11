<script setup>
import { ref } from 'vue'
import { userService } from '../services/api'

// 表单数据
const username = ref('')
const password = ref('')

// 错误消息
const errorMessage = ref('')

// 登录成功事件
const emit = defineEmits(['login-success'])

// 登录方法
const handleLogin = async () => {
  try {
    errorMessage.value = ''
    
    // 表单验证
    if (!username.value || !password.value) {
      errorMessage.value = '用户名和密码不能为空'
      return
    }
    
    // 调用登录API
    const response = await userService.login(username.value, password.value)
    
    // 登录成功
    const user = response.data
    
    // 存储用户信息到本地存储
    localStorage.setItem('user', JSON.stringify(user))
    
    // 触发登录成功事件
    emit('login-success', user)
    
    // 清空表单
    username.value = ''
    password.value = ''
  } catch (error) {
    // 处理错误
    if (error.response && error.response.data && error.response.data.error) {
      errorMessage.value = error.response.data.error
    } else {
      errorMessage.value = '登录失败，请稍后再试'
    }
  }
}
</script>

<template>
  <div class="login-form">
    <h2>用户登录</h2>
    
    <!-- 错误信息提示 -->
    <div v-if="errorMessage" class="error-message">
      {{ errorMessage }}
    </div>
    
    <!-- 登录表单 -->
    <form @submit.prevent="handleLogin">
      <div class="form-group">
        <label for="login-username">用户名</label>
        <input 
          id="login-username" 
          v-model="username" 
          type="text" 
          placeholder="请输入用户名" 
          required
        />
      </div>
      
      <div class="form-group">
        <label for="login-password">密码</label>
        <input 
          id="login-password" 
          v-model="password" 
          type="password" 
          placeholder="请输入密码" 
          required
        />
      </div>
      
      <button type="submit" class="submit-btn">登录</button>
    </form>
  </div>
</template>

<style scoped>
.login-form {
  max-width: 400px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
  background-color: #f9f9f9;
}

h2 {
  text-align: center;
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 15px;
}

label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

input {
  width: 100%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.submit-btn {
  width: 100%;
  padding: 10px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.submit-btn:hover {
  background-color: #45a049;
}

.error-message {
  color: red;
  margin-bottom: 15px;
  padding: 10px;
  background-color: #ffecec;
  border-radius: 4px;
}
</style>