package com.example.gonggang.domain.free_time.application;

import com.example.gonggang.domain.free_time.repository.FreeTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FreeTimeDeleteService {
    private final FreeTimeRepository freeTimeRepository;
}
