package com.courier.demo.controller;

import com.courier.demo.entity.User;
import com.courier.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户控制器
 * 处理用户相关的HTTP请求
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") // 允许跨域请求
public class UserController {
    
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    /**
     * 用户注册
     * @param user 用户信息
     * @return 注册结果
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User registeredUser = userService.register(user);
            return ResponseEntity.ok(registeredUser);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * 用户登录
     * @param credentials 登录凭证
     * @return 登录结果
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        try {
            String username = credentials.get("username");
            String password = credentials.get("password");
            
            User user = userService.login(username, password);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * 获取用户信息
     * @param id 用户ID
     * @return 用户信息
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            User user = userService.findById(id);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * 更新用户信息
     * @param id 用户ID
     * @param userData 用户数据
     * @return 更新后的用户信息
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody Map<String, Object> userData) {
        try {
            // 获取原用户信息
            User user = userService.findById(id);
            
            // 更新用户信息
            if (userData.containsKey("name")) {
                user.setName((String) userData.get("name"));
            }
            
            if (userData.containsKey("email")) {
                user.setEmail((String) userData.get("email"));
            }
            
            // 如果要更新密码
            if (userData.containsKey("currentPassword") && userData.containsKey("newPassword")) {
                String currentPassword = (String) userData.get("currentPassword");
                String newPassword = (String) userData.get("newPassword");
                
                // 验证当前密码
                if (!user.getPassword().equals(currentPassword)) {
                    throw new RuntimeException("当前密码不正确");
                }
                
                // 更新密码
                user.setPassword(newPassword);
            }
            
            // 保存更新
            User updatedUser = userService.update(user);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}