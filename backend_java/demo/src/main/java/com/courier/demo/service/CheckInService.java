package com.courier.demo.service;

import com.courier.demo.entity.CheckIn;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 打卡服务接口
 * 定义打卡相关的业务逻辑
 */
public interface CheckInService {
    
    /**
     * 用户打卡
     * @param userId 用户ID
     * @param note 打卡备注
     * @return 打卡记录
     */
    CheckIn checkIn(Long userId, String note);
    
    /**
     * 获取用户的打卡记录
     * @param userId 用户ID
     * @return 打卡记录列表
     */
    List<CheckIn> getUserCheckIns(Long userId);
    
    /**
     * 获取用户某个时间段内的打卡记录
     * @param userId 用户ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 打卡记录列表
     */
    List<CheckIn> getUserCheckInsBetween(Long userId, LocalDateTime startTime, LocalDateTime endTime);
}