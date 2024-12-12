package com.example.gonggang.domain.free_time.dto.response;

import com.example.gonggang.domain.common.Weekday;
import com.example.gonggang.domain.free_time.domain.FreeTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record FreeTimeAllResponse(
        Weekday weekday,
        List<FreeTimeItem> freeTimeItems
) {
    public static List<FreeTimeAllResponse> from(Map<Weekday, List<FreeTime>> freeTimeMap) {
        return List.of(Weekday.values()).stream()
                .map(weekday -> new FreeTimeAllResponse(
                        weekday,
                        freeTimeMap.getOrDefault(weekday, List.of()).stream()
                                .map(FreeTimeItem::of)
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }
}
