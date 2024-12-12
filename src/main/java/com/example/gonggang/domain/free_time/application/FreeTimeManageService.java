package com.example.gonggang.domain.free_time.application;

import com.example.gonggang.domain.common.Weekday;
import com.example.gonggang.domain.free_time.domain.FreeTime;
import com.example.gonggang.domain.free_time.dto.response.FreeTimeAllResponse;
import com.example.gonggang.domain.users.domain.Users;
import com.example.gonggang.domain.users.service.UserGetService;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FreeTimeManageService {

    private final UserGetService userGetService;
    private final FreeTimeGetService freeTimeGetService;

    public List<FreeTimeAllResponse> readAll(Long userId) {
        Users user = userGetService.findByMemberId(userId);

        Map<Weekday, List<FreeTime>> freeTimeMap = Arrays.stream(Weekday.values())
                .collect(Collectors.toMap(
                        weekday -> weekday,
                        weekday -> freeTimeGetService.findAllByUserAndWeekDay(user, weekday)
                ));

        return FreeTimeAllResponse.from(freeTimeMap);
    }
}
