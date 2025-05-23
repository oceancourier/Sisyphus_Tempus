<script setup>
import { ref, onMounted, computed } from 'vue'
import { checkInService } from '../services/api'

// 用户信息
const user = ref(null)

// 打卡消息
const checkInMessage = ref('')
const checkInNote = ref('')
const checkInStatus = ref('')

// 打卡记录
const checkInRecords = ref([])

// 今日打卡状态
const todayCheckIns = ref([])
const hasCheckedInToday = computed(() => todayCheckIns.value.length > 0)

// 定义事件
const emit = defineEmits(['go-to-profile'])

// 组件加载时获取用户信息
onMounted(() => {
  const storedUser = localStorage.getItem('user')
  if (storedUser) {
    user.value = JSON.parse(storedUser)
    fetchCheckInRecords()
    fetchTodayCheckIns()
  }
})

// 打卡方法
const handleCheckIn = async () => {
  try {
    if (!user.value) {
      checkInStatus.value = 'error'
      checkInMessage.value = '请先登录'
      return
    }
    
    // 调用打卡API
    await checkInService.checkIn(user.value.id, checkInNote.value)
    
    // 打卡成功
    checkInStatus.value = 'success'
    checkInMessage.value = '打卡成功！'
    checkInNote.value = ''
    
    // 刷新打卡记录
    fetchCheckInRecords()
    fetchTodayCheckIns()
  } catch (error) {
    // 处理错误
    checkInStatus.value = 'error'
    if (error.response && error.response.data && error.response.data.error) {
      checkInMessage.value = error.response.data.error
    } else {
      checkInMessage.value = '打卡失败，请稍后再试'
    }
  }
}

// 获取打卡记录
const fetchCheckInRecords = async () => {
  try {
    if (!user.value) return
    
    const response = await checkInService.getUserCheckIns(user.value.id)
    checkInRecords.value = response.data
  } catch (error) {
    console.error('获取打卡记录失败', error)
  }
}

// 获取今日打卡记录
const fetchTodayCheckIns = async () => {
  try {
    if (!user.value) return
    
    const response = await checkInService.getUserTodayCheckIns(user.value.id)
    todayCheckIns.value = response.data
  } catch (error) {
    console.error('获取今日打卡记录失败', error)
  }
}

// 退出登录
const logout = () => {
  localStorage.removeItem('user')
  user.value = null
  checkInRecords.value = []
  todayCheckIns.value = []
}

// 格式化日期时间
const formatDateTime = (dateTimeStr) => {
  const date = new Date(dateTimeStr)
  return new Intl.DateTimeFormat('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit',
    second: '2-digit'
  }).format(date)
}

// 跳转到个人资料页面
const goToProfile = () => {
  emit('go-to-profile')
}
</script>

<template>
  <div class="check-in-container">
    <div v-if="user" class="user-info">
      <div class="user-name-container">
        <h2>欢迎, {{ user.name }}</h2>
        <button @click="goToProfile" class="profile-btn">个人信息</button>
      </div>
      <button @click="logout" class="logout-btn">退出登录</button>
    </div>
    
    <div v-if="user" class="check-in-form">
      <h3>今日打卡</h3>
      
      <!-- 打卡状态提示 -->
      <div v-if="hasCheckedInToday" class="today-status success">
        今日已打卡 ({{ todayCheckIns.length }}次)
      </div>
      
      <!-- 打卡消息提示 -->
      <div v-if="checkInMessage" :class="['message', checkInStatus]">
        {{ checkInMessage }}
      </div>
      
      <div class="form-group">
        <label for="note">打卡备注 (可选)</label>
        <textarea 
          id="note" 
          v-model="checkInNote" 
          placeholder="请输入打卡备注" 
          rows="3"
        ></textarea>
      </div>
      
      <button @click="handleCheckIn" class="check-in-btn">打卡</button>
    </div>
    
    <div v-if="user && checkInRecords.length > 0" class="check-in-records">
      <h3>打卡记录</h3>
      <ul>
        <li v-for="record in checkInRecords" :key="record.id" class="record-item">
          <div class="record-time">{{ formatDateTime(record.checkInTime) }}</div>
          <div v-if="record.note" class="record-note">{{ record.note }}</div>
        </li>
      </ul>
    </div>
    
    <div v-else-if="user" class="no-records">
      <p>暂无打卡记录</p>
    </div>
  </div>
</template>

<style scoped>
.check-in-container {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
}

.user-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding-bottom: 10px;
  border-bottom: 1px solid #eee;
}

.user-name-container {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.user-name-container h2 {
  margin: 0;
}

.profile-btn {
  padding: 5px 10px;
  background-color: #2196F3;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
}

.logout-btn {
  padding: 5px 10px;
  background-color: #f44336;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.check-in-form {
  background-color: #f9f9f9;
  padding: 20px;
  border-radius: 5px;
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

textarea {
  width: 100%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.check-in-btn {
  width: 100%;
  padding: 12px;
  background-color: #2196F3;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
}

.check-in-btn:hover {
  background-color: #0b7dda;
}

.message {
  margin-bottom: 15px;
  padding: 10px;
  border-radius: 4px;
}

.success {
  color: green;
  background-color: #e7f7e7;
}

.error {
  color: red;
  background-color: #ffecec;
}

.today-status {
  margin-bottom: 15px;
  padding: 10px;
  border-radius: 4px;
  text-align: center;
  font-weight: bold;
}

.check-in-records {
  background-color: #f9f9f9;
  padding: 20px;
  border-radius: 5px;
}

h3 {
  margin-top: 0;
  margin-bottom: 15px;
}

ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.record-item {
  padding: 10px;
  margin-bottom: 10px;
  border: 1px solid #eee;
  border-radius: 4px;
  background-color: white;
}

.record-time {
  font-weight: bold;
  margin-bottom: 5px;
}

.record-note {
  color: #666;
}

.no-records {
  text-align: center;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 5px;
  color: #666;
}

@media (max-width: 600px) {
  .user-info {
    flex-direction: column;
    align-items: flex-start;
    gap: 10px;
  }
  
  .user-name-container {
    width: 100%;
  }
  
  .logout-btn {
    width: 100%;
  }
}
</style>