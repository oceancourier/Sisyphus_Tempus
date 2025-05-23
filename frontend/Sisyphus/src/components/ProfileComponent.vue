<script setup>
import { ref, onMounted } from 'vue'
import { userService } from '../services/api'

const user = ref(null)
const editMode = ref(false)
const errorMessage = ref('')
const successMessage = ref('')

// 编辑表单数据
const editForm = ref({
  name: '',
  email: '',
  password: '',
  newPassword: ''
})

onMounted(() => {
  const storedUser = localStorage.getItem('user')
  if (storedUser) {
    user.value = JSON.parse(storedUser)
    // 初始化表单数据
    editForm.value.name = user.value.name || ''
    editForm.value.email = user.value.email || ''
  }
})

const toggleEditMode = () => {
  editMode.value = !editMode.value
  errorMessage.value = ''
  successMessage.value = ''
}

const updateProfile = async () => {
  try {
    errorMessage.value = ''
    successMessage.value = ''
    
    // 表单验证
    if (!editForm.value.name) {
      errorMessage.value = '姓名不能为空'
      return
    }
    
    // 准备更新数据
    const updateData = {
      id: user.value.id,
      name: editForm.value.name,
      email: editForm.value.email
    }
    
    // 如果填写了当前密码和新密码，则更新密码
    if (editForm.value.password && editForm.value.newPassword) {
      updateData.currentPassword = editForm.value.password
      updateData.newPassword = editForm.value.newPassword
    }
    
    // 调用更新API
    const response = await userService.updateProfile(updateData)
    
    // 更新成功
    user.value = response.data
    localStorage.setItem('user', JSON.stringify(user.value))
    
    successMessage.value = '个人信息更新成功'
    editMode.value = false
    
    // 清空密码字段
    editForm.value.password = ''
    editForm.value.newPassword = ''
  } catch (error) {
    if (error.response && error.response.data && error.response.data.error) {
      errorMessage.value = error.response.data.error
    } else {
      errorMessage.value = '更新失败，请稍后再试'
    }
  }
}

const goBack = () => {
  emit('back')
}
const emit = defineEmits(['back'])
</script>

<template>
  <div class="profile-container">
    <div class="profile-header">
      <button @click="goBack" class="back-btn">返回</button>
      <h2>个人信息</h2>
    </div>
    
    <div v-if="errorMessage" class="error-message">
      {{ errorMessage }}
    </div>
    
    <div v-if="successMessage" class="success-message">
      {{ successMessage }}
    </div>
    
    <div v-if="!editMode" class="profile-info">
      <div class="info-item">
        <strong>用户名:</strong> {{ user?.username }}
      </div>
      <div class="info-item">
        <strong>姓名:</strong> {{ user?.name }}
      </div>
      <div class="info-item">
        <strong>邮箱:</strong> {{ user?.email || '未设置' }}
      </div>
      <div class="info-item">
        <strong>注册时间:</strong> {{ new Date(user?.createdAt).toLocaleString() }}
      </div>
      
      <button @click="toggleEditMode" class="edit-btn">编辑信息</button>
    </div>
    
    <div v-else class="profile-edit-form">
      <div class="form-group">
        <label for="name">姓名</label>
        <input id="name" v-model="editForm.name" type="text" required />
      </div>
      
      <div class="form-group">
        <label for="email">邮箱</label>
        <input id="email" v-model="editForm.email" type="email" />
      </div>
      
      <div class="form-group">
        <label for="current-password">当前密码 (仅修改密码时需填写)</label>
        <input id="current-password" v-model="editForm.password" type="password" />
      </div>
      
      <div class="form-group">
        <label for="new-password">新密码</label>
        <input id="new-password" v-model="editForm.newPassword" type="password" />
      </div>
      
      <div class="button-group">
        <button @click="toggleEditMode" class="cancel-btn">取消</button>
        <button @click="updateProfile" class="save-btn">保存修改</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.profile-container {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
}

.profile-header {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
  position: relative;
}

.profile-header h2 {
  flex-grow: 1;
  text-align: center;
  margin: 0;
}

.back-btn {
  padding: 5px 10px;
  background-color: #f1f1f1;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  position: absolute;
  left: 0;
}

.profile-info, .profile-edit-form {
  background-color: #f9f9f9;
  padding: 20px;
  border-radius: 5px;
}

.info-item {
  margin-bottom: 15px;
  padding-bottom: 10px;
  border-bottom: 1px solid #eee;
}

.info-item:last-child {
  border-bottom: none;
}

.edit-btn {
  width: 100%;
  padding: 10px;
  background-color: #2196F3;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-top: 15px;
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

.button-group {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}

.cancel-btn, .save-btn {
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.cancel-btn {
  background-color: #f1f1f1;
}

.save-btn {
  background-color: #4CAF50;
  color: white;
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

@media (max-width: 600px) {
  .button-group {
    flex-direction: column;
  }
  
  .cancel-btn, .save-btn {
    width: 100%;
    margin-bottom: 10px;
  }
}
</style>