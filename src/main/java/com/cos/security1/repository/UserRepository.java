package com.cos.security1.repository;

import com.cos.security1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

// 기본적인 CRUD 함수를 JpaRepository 가 들고 있음
// @Repository 없어도 IoC 가능
public interface UserRepository extends JpaRepository<User, Integer> {
    // select * from user where username = 1?
    public User findByUsername(String username); // Jpa Query Methods
}
