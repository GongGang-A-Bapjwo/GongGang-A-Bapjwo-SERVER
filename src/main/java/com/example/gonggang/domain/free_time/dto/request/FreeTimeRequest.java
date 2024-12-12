package com.example.gonggang.domain.free_time.dto.request;

import java.util.List;

public record FreeTimeRequest (
        List<FreeTimeRequestItem> freeTimeRequestItems
) {
}
