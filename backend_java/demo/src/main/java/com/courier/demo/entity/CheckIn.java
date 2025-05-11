package com.courier.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * 打卡记录实体类
 * 用于存储用户的打卡记录
 */
@Entity
@Table(name = "check_ins")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckIn {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(name = "check_in_time", nullable = false)
    private LocalDateTime checkInTime;
    
    private String note;
    
    @PrePersist
    protected void onCreate() {
        checkInTime = LocalDateTime.now();
    }
}