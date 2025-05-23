package com.courier.demo.service;

import com.courier.demo.entity.User;

/**
 * 用户服务接口
 * 定义用户相关的业务逻辑
 */
public interface UserService {
    
    /**
     * 注册新用户
     * @param user 用户信息
     * @return 注册成功的用户
     * @throws RuntimeException 如果用户名已存在
     */
    User register(User user);
    
    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 登录成功的用户
     * @throws RuntimeException 如果用户名或密码错误
     */
    User login(String username, String password);
    
    /**
     * 根据ID查找用户
     * @param id 用户ID
     * @return 用户对象
     * @throws RuntimeException 如果用户不存在
     */
    User findById(Long id);
    
    /**
     * 更新用户信息
     * @param user 用户信息
     * @return 更新后的用户
     */
    User update(User user);
}