import axios from 'axios'

// API基础URL
const API_URL = 'http://localhost:8080/api'

// 创建axios实例
const apiClient = axios.create({
  baseURL: API_URL,
  headers: {
    'Content-Type': 'application/json'
  }
})

/**
 * 用户API服务
 */
export const userService = {
  // 用户注册
  register: (userData) => {
    return apiClient.post('/users/register', userData)
  },
  
  // 用户登录
  login: (username, password) => {
    return apiClient.post('/users/login', { username, password })
  },
  
  // 获取用户信息
  getUserById: (id) => {
    return apiClient.get(`/users/${id}`)
  }
}

/**
 * 打卡API服务
 */
export const checkInService = {
  // 用户打卡
  checkIn: (userId, note) => {
    return apiClient.post('/check-ins', { userId, note })
  },
  
  // 获取用户的所有打卡记录
  getUserCheckIns: (userId) => {
    return apiClient.get(`/check-ins/user/${userId}`)
  },
  
  // 获取用户某个时间段内的打卡记录
  getUserCheckInsBetween: (userId, startTime, endTime) => {
    return apiClient.get(`/check-ins/user/${userId}/between`, {
      params: { startTime, endTime }
    })
  }
}