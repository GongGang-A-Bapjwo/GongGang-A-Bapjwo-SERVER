package com.example.gonggang.domain.external.fast_api.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "fastApiClient", url = "${app.fast-api.url}")
public interface FastApiClient {

	@PostMapping("/uploadfile")
	Map<String, Object> sendImageUrlAndMemberId(
		@RequestParam("file_url") String fileUrl,
		@RequestParam("username") String username
	);

	@PostMapping("/entrance-codes")
	Map<String, Object> sendEntranceCodeAndUserId(
		@RequestParam("entrance_code") String entranceCode,
		@RequestParam("user_id") int userId
	);
}
