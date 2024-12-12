package com.example.gonggang.domain.free_time.application;

import com.example.gonggang.domain.common.Weekday;
import com.example.gonggang.domain.free_time.domain.FreeTime;
import com.example.gonggang.domain.free_time.repository.FreeTimeRepository;
import com.example.gonggang.domain.users.domain.Users;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FreeTimeGetService {
    private final FreeTimeRepository freeTimeRepository;

    public List<FreeTime> findAllByUserAndWeekDay(Users user, Weekday weekday) {
        return freeTimeRepository.findAllByUserAndWeekday(user, weekday);
    }

    public List<FreeTime> findAllByUser(Users user) {
        return freeTimeRepository.findAllByUser(user);
    }
}
