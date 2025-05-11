package com.courier.demo.repository;

import com.courier.demo.entity.CheckIn;
import com.courier.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 打卡记录数据访问接口
 */
@Repository
public interface CheckInRepository extends JpaRepository<CheckIn, Long> {
    
    /**
     * 查询用户的所有打卡记录
     * @param user 用户
     * @return 打卡记录列表
     */
    List<CheckIn> findByUser(User user);
    
    /**
     * 查询用户某个时间段内的打卡记录
     * @param user 用户
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 打卡记录列表
     */
    List<CheckIn> findByUserAndCheckInTimeBetween(User user, LocalDateTime startTime, LocalDateTime endTime);
}