package com.courier.demo.service.impl;

import com.courier.demo.entity.CheckIn;
import com.courier.demo.entity.User;
import com.courier.demo.repository.CheckInRepository;
import com.courier.demo.service.CheckInService;
import com.courier.demo.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 打卡服务实现类
 */
@Service
public class CheckInServiceImpl implements CheckInService {
    
    private final CheckInRepository checkInRepository;
    private final UserService userService;
    
    // 移除了@Autowired注解，Spring会自动注入
    public CheckInServiceImpl(CheckInRepository checkInRepository, UserService userService) {
        this.checkInRepository = checkInRepository;
        this.userService = userService;
    }
    
    @Override
    public CheckIn checkIn(Long userId, String note) {
        // 查找用户
        User user = userService.findById(userId);
        
        // 创建打卡记录
        CheckIn checkIn = new CheckIn();
        checkIn.setUser(user);
        checkIn.setNote(note);
        
        // 保存打卡记录
        return checkInRepository.save(checkIn);
    }
    
    @Override
    public List<CheckIn> getUserCheckIns(Long userId) {
        User user = userService.findById(userId);
        return checkInRepository.findByUser(user);
    }
    
    @Override
    public List<CheckIn> getUserCheckInsBetween(Long userId, LocalDateTime startTime, LocalDateTime endTime) {
        User user = userService.findById(userId);
        return checkInRepository.findByUserAndCheckInTimeBetween(user, startTime, endTime);
    }
}