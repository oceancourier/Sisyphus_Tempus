package com.courier.demo.controller;

import com.courier.demo.entity.CheckIn;
import com.courier.demo.service.CheckInService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 打卡控制器
 * 处理打卡相关的HTTP请求
 */
@RestController
@RequestMapping("/api/check-ins")
@CrossOrigin(origins = "*") // 允许跨域请求
public class CheckInController {
    
    private final CheckInService checkInService;
    
    public CheckInController(CheckInService checkInService) {
        this.checkInService = checkInService;
    }
    
    /**
     * 用户打卡
     * @param request 打卡请求
     * @return 打卡结果
     */
    @PostMapping
    public ResponseEntity<?> checkIn(@RequestBody Map<String, Object> request) {
        try {
            Long userId = Long.valueOf(request.get("userId").toString());
            String note = (String) request.get("note");
            
            CheckIn checkIn = checkInService.checkIn(userId, note);
            return ResponseEntity.ok(checkIn);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * 获取用户的所有打卡记录
     * @param userId 用户ID
     * @return 打卡记录列表
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserCheckIns(@PathVariable Long userId) {
        try {
            List<CheckIn> checkIns = checkInService.getUserCheckIns(userId);
            return ResponseEntity.ok(checkIns);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * 获取用户某个时间段内的打卡记录
     * @param userId 用户ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 打卡记录列表
     */
    @GetMapping("/user/{userId}/between")
    public ResponseEntity<?> getUserCheckInsBetween(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime
    ) {
        try {
            List<CheckIn> checkIns = checkInService.getUserCheckInsBetween(userId, startTime, endTime);
            return ResponseEntity.ok(checkIns);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}