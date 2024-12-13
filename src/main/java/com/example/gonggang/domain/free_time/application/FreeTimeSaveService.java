package com.example.gonggang.domain.free_time.application;

import com.example.gonggang.domain.external.fast_api.dto.FastApiResponse;
import com.example.gonggang.domain.external.fast_api.util.TimeFormater;
import com.example.gonggang.domain.free_time.dto.request.FreeTimeRequest;
import com.example.gonggang.domain.free_time.dto.request.FreeTimeRequestItem;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.gonggang.domain.common.Weekday;
import com.example.gonggang.domain.free_time.domain.FreeTime;
import com.example.gonggang.domain.free_time.dto.response.FreeTimeResponse;
import com.example.gonggang.domain.free_time.exception.OutputFirstElementIsNotMapException;
import com.example.gonggang.domain.free_time.exception.OutputIsNotListException;
import com.example.gonggang.domain.free_time.exception.OutputListIsEmptyException;
import com.example.gonggang.domain.free_time.repository.FreeTimeRepository;
import com.example.gonggang.domain.users.domain.Users;
import com.example.gonggang.domain.users.service.UserGetService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FreeTimeSaveService {

	private final FreeTimeRepository freeTimeRepository;
	private final UserGetService userGetService;
	private final FreeTimeGetService freeTimeGetService;

	public List<FreeTimeResponse> saveFreeTimes(FastApiResponse fastApiResponse) {
		Map<String, Object> responseMap = fastApiResponse.response();
		String memberIdStr = (String)responseMap.get("user_name");
		long memberId = Long.parseLong(memberIdStr);

		Users user = userGetService.findByMemberId(memberId);

		Map<String, List<String>> schedule = extractSchedule(fastApiResponse);
		List<FreeTime> freeTimes = new ArrayList<>();

		createFreeTimes(schedule, user, freeTimes);

		freeTimeRepository.saveAll(freeTimes);

		return formatResponse(freeTimes);
	}

	public void create(Long userId, FreeTimeRequest request) {
		Users user = userGetService.findByMemberId(userId);
		List<FreeTimeRequestItem> requestItems = request.freeTimeRequestItems();
		List<FreeTime> freeTimes = requestItems.stream().map(item ->
			FreeTime.create(LocalTime.parse(item.startTime()), LocalTime.parse(item.endTime()), item.weekday(), user)
		).toList();

		freeTimeRepository.saveAll(freeTimes);
	}

	public void update(Long userId, FreeTimeRequest request) {
		Users user = userGetService.findByMemberId(userId);

		List<FreeTime> freeTimes = freeTimeGetService.findAllByUser(user);
		freeTimeRepository.deleteAll(freeTimes);

		List<FreeTimeRequestItem> requestItems = request.freeTimeRequestItems();
		List<FreeTime> newFreeTimes = requestItems.stream().map(item ->
			FreeTime.create(LocalTime.parse(item.startTime()), LocalTime.parse(item.endTime()), item.weekday(), user)
		).toList();

		freeTimeRepository.saveAll(newFreeTimes);
	}

	// 'output'을 추출하는 메서드
	@SuppressWarnings("unchecked")
	private Map<String, List<String>> extractSchedule(FastApiResponse fastApiResponse) {
		Map<String, Object> responseMap = fastApiResponse.response();
		Object outputObj = responseMap.get("output");

		if (!(outputObj instanceof List)) {
			throw new OutputIsNotListException();
		}

		if (((List<?>)outputObj).isEmpty()) {
			throw new OutputListIsEmptyException();
		}

		Object firstElement = ((List<?>)outputObj).getFirst();

		if (!(firstElement instanceof Map)) {
			throw new OutputFirstElementIsNotMapException();
		}

		return (Map<String, List<String>>)firstElement;
	}

	private void createFreeTimes(Map<String, List<String>> schedule, Users user, List<FreeTime> freeTimes) {
		schedule.forEach((weekdayStr, timeSlots) -> {
			for (String timeSlot : timeSlots) {
				FreeTime freeTime = createFreeTimeFromTimeSlot(weekdayStr, timeSlot, user);
				freeTimes.add(freeTime);
			}
		});
	}

	private FreeTime createFreeTimeFromTimeSlot(String weekdayStr, String timeSlot, Users user) {
		String[] times = timeSlot.split("-");

		// 시간을 "9-17" -> "09:00-17:00" 형식으로 변환
		String startTimeStr = TimeFormater.convertToTimeFormat(times[0]);
		String endTimeStr = TimeFormater.convertToTimeFormat(times[1]);

		// LocalTime으로 파싱
		LocalTime startTime = LocalTime.parse(startTimeStr);
		LocalTime endTime = LocalTime.parse(endTimeStr);

		// 한글 요일을 Weekday로 변환
		Weekday weekday = Weekday.fromKorean(weekdayStr);

		return FreeTime.create(startTime, endTime, weekday, user);
	}

	private List<FreeTimeResponse> formatResponse(List<FreeTime> freeTimes) {
		return freeTimes.stream()
			.map(freeTime -> FreeTimeResponse.of(
				freeTime.getWeekday().toString(),
				freeTime.getStartTime().toString(),
				freeTime.getEndTime().toString(),
				freeTime.getUser().getId()
			))
			.toList();
	}
}
