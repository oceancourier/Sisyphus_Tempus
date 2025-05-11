package com.courier.demo.service.impl;

import com.courier.demo.entity.User;
import com.courier.demo.repository.UserRepository;
import com.courier.demo.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    public User register(User user) {
        // 检查用户名是否已存在
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("用户名已存在");
        }
        
        // 实际开发中应该对密码进行加密处理
        // 这里为了简化，直接保存明文密码
        return userRepository.save(user);
    }
    
    @Override
    public User login(String username, String password) {
        // 查找用户
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("用户名或密码错误"));
        
        // 验证密码
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("用户名或密码错误");
        }
        
        return user;
    }
    
    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
    }
}