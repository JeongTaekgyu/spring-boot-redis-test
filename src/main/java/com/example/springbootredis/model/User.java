package com.example.springbootredis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    // redis에서 Serialize 하기 위해선 Serializable를 implements 받아야함
    private Long id;
    private String username;
    private String email;
    private LocalDateTime createdAt;
}