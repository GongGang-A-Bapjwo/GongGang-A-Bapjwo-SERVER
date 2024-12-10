package com.example.gonggang.domain.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gonggang.domain.users.domain.Users;

public interface UserRepository extends JpaRepository<Users, Long> {
}
