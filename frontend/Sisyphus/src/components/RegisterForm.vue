<script setup>
import { ref } from 'vue'
import { userService } from '../services/api'

// 表单数据
const username = ref('')
const password = ref('')
const confirmPassword = ref('')
const name = ref('')
const email = ref('')

// 错误消息
const errorMessage = ref('')

// 成功消息
const successMessage = ref('')

// 定义事件
const emit = defineEmits(['register-success'])

// 注册方法
const handleRegister = async () => {
  try {
    errorMessage.value = ''
    successMessage.value = ''
    
    // 表单验证
    if (!username.value || !password.value || !name.value) {
      errorMessage.value = '用户名、密码和姓名不能为空'
      return
    }
    
    // 密码确认验证
    if (password.value !== confirmPassword.value) {
      errorMessage.value = '两次输入的密码不一致'
      return
    }
    
    // 调用注册API
    const response = await userService.register({
      username: username.value,
      password: password.value,
      name: name.value,
      email: email.value
    })
    
    // 注册成功
    successMessage.value = '注册成功，请登录'
    
    // 清空表单
    username.value = ''
    password.value = ''
    confirmPassword.value = ''
    name.value = ''
    email.value = ''
    
    // 3秒后跳转到登录页面
    setTimeout(() => {
      emit('register-success')
    }, 3000)
  } catch (error) {
    // 处理错误
    if (error.response && error.response.data && error.response.data.error) {
      errorMessage.value = error.response.data.error
    } else {
      errorMessage.value = '注册失败，请稍后再试'
    }
  }
}
</script>

<template>
  <div class="register-form">
    <h2>用户注册</h2>
    
    <!-- 错误信息提示 -->
    <div v-if="errorMessage" class="error-message">
      {{ errorMessage }}
    </div>
    
    <!-- 成功信息提示 -->
    <div v-if="successMessage" class="success-message">
      {{ successMessage }}
    </div>
    
    <!-- 注册表单 -->
    <form @submit.prevent="handleRegister">
      <div class="form-group">
        <label for="username">用户名</label>
        <input 
          id="username" 
          v-model="username" 
          type="text" 
          placeholder="请输入用户名" 
          required
        />
      </div>
      
      <div class="form-group">
        <label for="password">密码</label>
        <input 
          id="password" 
          v-model="password" 
          type="password" 
          placeholder="请输入密码" 
          required
        />
      </div>
      
      <div class="form-group">
        <label for="confirm-password">确认密码</label>
        <input 
          id="confirm-password" 
          v-model="confirmPassword" 
          type="password" 
          placeholder="请再次输入密码" 
          required
        />
      </div>
      
      <div class="form-group">
        <label for="name">姓名</label>
        <input 
          id="name" 
          v-model="name" 
          type="text" 
          placeholder="请输入姓名" 
          required
        />
      </div>
      
      <div class="form-group">
        <label for="email">邮箱</label>
        <input 
          id="email" 
          v-model="email" 
          type="email" 
          placeholder="请输入邮箱(选填)" 
        />
      </div>
      
      <button type="submit" class="submit-btn">注册</button>
    </form>
  </div>
</template>

<style scoped>
.register-form {
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

.success-message {
  color: green;
  margin-bottom: 15px;
  padding: 10px;
  background-color: #e7f7e7;
  border-radius: 4px;
}
</style>