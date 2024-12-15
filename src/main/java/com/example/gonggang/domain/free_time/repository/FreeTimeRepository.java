package com.example.gonggang.domain.free_time.repository;

import com.example.gonggang.domain.common.Weekday;
import com.example.gonggang.domain.users.domain.Users;
import io.lettuce.core.dynamic.annotation.Param;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gonggang.domain.free_time.domain.FreeTime;
import org.springframework.data.jpa.repository.Query;

public interface FreeTimeRepository extends JpaRepository<FreeTime, Long> {

    @Query("SELECT f FROM FreeTime f WHERE f.user = :user AND f.weekday = :weekday")
    List<FreeTime> findAllByUserAndWeekday(@Param("user") Users user, @Param("weekday") Weekday weekday);

    List<FreeTime> findAllByUser(Users user);

    void deleteByUserId(Long userId);
}
