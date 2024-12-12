package com.example.gonggang.domain.free_time.application;

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

	public List<FreeTimeResponse> saveFreeTimes(Map<String, Object> fastApiResponse) {
		String memberIdStr = (String) fastApiResponse.get("user_name");
		long memberId = Long.parseLong(memberIdStr);

		Users user = userGetService.findByMemberId(memberId);

		Map<String, List<String>> schedule = extractSchedule(fastApiResponse);
		List<FreeTime> freeTimes = new ArrayList<>();

		createFreeTimes(schedule, user, freeTimes);

		freeTimeRepository.saveAll(freeTimes);

		return formatResponse(freeTimes);
	}

	// 'output'을 추출하는 메서드
	@SuppressWarnings("unchecked")
	private Map<String, List<String>> extractSchedule(Map<String, Object> fastApiResponse) {
		Object outputObj = fastApiResponse.get("output");

		if (!(outputObj instanceof List)) {
			throw new OutputIsNotListException();
		}

		if (((List<?>) outputObj).isEmpty()) {
			throw new OutputListIsEmptyException();
		}

		Object firstElement = ((List<?>) outputObj).getFirst();

		if (!(firstElement instanceof Map)) {
			throw new OutputFirstElementIsNotMapException();
		}

		return (Map<String, List<String>>) firstElement;
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
		String startTimeStr = convertToTimeFormat(times[0]);
		String endTimeStr = convertToTimeFormat(times[1]);

		// LocalTime으로 파싱
		LocalTime startTime = LocalTime.parse(startTimeStr);
		LocalTime endTime = LocalTime.parse(endTimeStr);

		// 한글 요일을 Weekday로 변환
		Weekday weekday = Weekday.fromKorean(weekdayStr);

		return FreeTime.create(startTime, endTime, weekday, user);
	}

	// 시간을 "9" -> "09:00" 형식으로 변환하는 메서드
	private String convertToTimeFormat(String time) {
		if (time.length() == 1) {
			time = "0" + time + ":00"; // "9" -> "09:00" 변환
		} else if (time.length() == 2 && !time.contains(":")) {
			time = time + ":00"; // "17" -> "17:00" 변환
		}
		return time + ":00"; // 기본적으로 ":00"을 붙여서 반환
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

	public void create(Long userId, FreeTimeRequest request) {
		Users user = userGetService.findByMemberId(userId);
		List<FreeTimeRequestItem> requestItems = request.freeTimeRequestItems();
		List<FreeTime> freeTimes = requestItems.stream().map(item->
				FreeTime.create(LocalTime.parse(item.startTime()),LocalTime.parse(item.endTime()),Weekday.fromKorean(item.weekday()),user)
		).toList();

		freeTimeRepository.saveAll(freeTimes);
	}

	public void update(Long userId, FreeTimeRequest request) {
		Users user = userGetService.findByMemberId(userId);

		List<FreeTime> freeTimes = freeTimeGetService.findAllByUser(user);
		freeTimeRepository.deleteAll(freeTimes);

		List<FreeTimeRequestItem> requestItems = request.freeTimeRequestItems();
		List<FreeTime> newFreeTimes = requestItems.stream().map(item->
				FreeTime.create(LocalTime.parse(item.startTime()),LocalTime.parse(item.endTime()),Weekday.fromKorean(item.weekday()),user)
		).toList();

		freeTimeRepository.saveAll(newFreeTimes);
	}
}
