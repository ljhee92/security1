package com.cos.security1.repository;

import com.cos.security1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

// 기본적인 CRUD 함수를 JpaRepository가 들고 있음
// @Repository 없어도 IoC 가능
public interface UserRepository extends JpaRepository<User, Integer> {
}
