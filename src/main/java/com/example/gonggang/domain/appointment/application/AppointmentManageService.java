package com.example.gonggang.domain.appointment.application;

import com.example.gonggang.domain.appointment.domain.AppointmentBoard;
import com.example.gonggang.domain.appointment.domain.AppointmentParticipant;
import com.example.gonggang.domain.appointment.domain.AppointmentRoom;
import com.example.gonggang.domain.appointment.dto.request.AppointmentCreateRequest;
import com.example.gonggang.domain.appointment.dto.request.AppointmentEnterRequest;
import com.example.gonggang.domain.appointment.dto.request.AppointmentSelectRequest;
import com.example.gonggang.domain.appointment.dto.response.AllAppointmentBoardResponse;
import com.example.gonggang.domain.appointment.dto.response.AppointmentAllResponse;
import com.example.gonggang.domain.appointment.dto.response.AppointmentCreateResponse;
import com.example.gonggang.domain.appointment.dto.response.AppointmentEntranceResponse;
import com.example.gonggang.domain.appointment.dto.response.AppointmentRemainingResponse;
import com.example.gonggang.domain.appointment.dto.response.AppointmentsGetResponse;
import com.example.gonggang.domain.appointment.exception.AccessDeniedException;
import com.example.gonggang.domain.appointment.exception.AlreadyEnteredException;
import com.example.gonggang.domain.appointment.exception.AppointmentRoomMaxAppointmentException;
import com.example.gonggang.domain.common.Weekday;
import com.example.gonggang.domain.external.fast_api.util.TimeFormater;
import com.example.gonggang.domain.free_time.application.FreeTimeGetService;
import com.example.gonggang.domain.free_time.domain.FreeTime;
import com.example.gonggang.domain.free_time.dto.response.FreeTimeItem;
import com.example.gonggang.domain.users.domain.Users;
import com.example.gonggang.domain.users.service.UserGetService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AppointmentManageService {
    private final AppointmentRoomSaveService appointmentRoomSaveService;
    private final UserGetService userGetService;
    private final AppointmentParticipantSaveService appointmentParticipantSaveService;
    private final AppointmentRoomGetService appointmentRoomGetService;
    private final ParticipantGetService participantGetService;
    private final AppointmentRoomDeleteService appointmentRoomDeleteService;
    private final FreeTimeGetService freeTimeGetService;
    private final AppointmentBoardGetService appointmentBoardGetService;

    @Transactional
    public AppointmentCreateResponse create(Long userId, AppointmentCreateRequest appointmentCreateRequest) {
        Users user = userGetService.findByMemberId(userId);
        String code = AppointmentRoom.generateEntranceCode();
        AppointmentRoom appointmentRoom = AppointmentRoom.initCreate(
                appointmentCreateRequest.maxParticipants(),
                code,
                appointmentCreateRequest.category(),
                appointmentCreateRequest.title()
        );

        AppointmentParticipant appointmentParticipant = AppointmentParticipant.create(user, appointmentRoom, true,
                false);

        appointmentRoomSaveService.save(appointmentRoom);
        appointmentParticipantSaveService.save(appointmentParticipant);

        return AppointmentCreateResponse.toResponse(userId, code);
    }

    @Transactional
    public AppointmentEntranceResponse enter(Long userId, AppointmentEnterRequest appointmentEnterRequest) {
        Users user = userGetService.findByMemberId(userId);
        String entranceCode = appointmentEnterRequest.entranceCode();

        AppointmentRoom appointmentRoom = appointmentRoomGetService.findByEnteranceCode(entranceCode);

        checkAvailable(appointmentRoom, user);

        AppointmentParticipant appointmentParticipant = AppointmentParticipant.create(user, appointmentRoom, false,
                false);
        appointmentParticipantSaveService.save(appointmentParticipant);
        appointmentRoom.plusCurrentParticipants();
        return AppointmentEntranceResponse.of(user.getId(), entranceCode);
    }

    private void checkAvailable(AppointmentRoom appointmentRoom, Users user) {
        if (!appointmentRoom.isAvailable()) {
            throw new AppointmentRoomMaxAppointmentException();
        }
        if (participantGetService.checkAlreadyEntered(user, appointmentRoom)) {
            throw new AlreadyEnteredException();
        }
    }

    @Transactional(readOnly = true)
    public List<AppointmentsGetResponse> readAll(Long userId) {
        Users user = userGetService.findByMemberId(userId);
        List<AppointmentParticipant> participants = participantGetService.findAllByParticipants(user);

        return participants.stream()
                .map(participantEntry -> {
                    AppointmentRoom room = participantEntry.getAppointmentRoom();
                    return new AppointmentsGetResponse(
                            room.getId(),
                            room.getTitle(),
                            room.getCategory(),
                            participantEntry.isOwner(),
                            room.getDecidedStartTime(),
                            room.getDecidedEndTime(),
                            room.getDecidedWeekday()
                    );
                })
                .toList();
    }

    @Transactional
    public void delete(Long userId, Long roomId) {
        Users user = userGetService.findByMemberId(userId);
        AppointmentParticipant appointmentParticipant = participantGetService.findByParticipantAndRoom(user,
                roomId);
        AppointmentRoom room = appointmentRoomGetService.findByRoomId(roomId);

        if (appointmentParticipant.isOwner()) {
            appointmentRoomDeleteService.delete(room);
        }

        room.minusCurrentParticipants();
        appointmentParticipant.disable();

    }

    @Transactional(readOnly = true)
    public AppointmentRemainingResponse read(Long userId, Long roomId) {
        Users user = userGetService.findByMemberId(userId);
        AppointmentParticipant appointmentParticipant = participantGetService.findByParticipantAndRoom(user,
                roomId);
        AppointmentRoom room = appointmentParticipant.getAppointmentRoom();
        return AppointmentRemainingResponse.toResponse(room);
    }

    @Transactional
    public void update(Long userId, Long roomId, AppointmentCreateRequest request) {
        Users user = userGetService.findByMemberId(userId);
        AppointmentParticipant appointmentParticipant = participantGetService.findByParticipantAndRoom(user,
                roomId);
        if (!appointmentParticipant.isOwner()) {
            throw new AccessDeniedException();
        }
        AppointmentRoom room = appointmentParticipant.getAppointmentRoom();
        room.updateFromDto(request);
    }

    @Transactional(readOnly = true)
    public AppointmentAllResponse read(Long userId) {
        LocalDateTime now = LocalDateTime.now();
        Weekday weekDay = Weekday.fromEnglish(now.getDayOfWeek().toString());
        Users user = userGetService.findByMemberId(userId);

        List<FreeTime> freeTimes = freeTimeGetService.findAllByUserAndWeekDay(user, weekDay);
        List<FreeTimeItem> freeTimeItems = freeTimes.stream().map(FreeTimeItem::of).toList();

        List<List<AppointmentBoard>> appointmentBoards = freeTimes.stream()
                .map(appointmentBoardGetService::findAllBoard)
                .toList();

        return AppointmentAllResponse.toResponse(weekDay.getValue(), freeTimeItems, appointmentBoards);
    }

    @Transactional(readOnly = true)
    public AllAppointmentBoardResponse readAll() {
        List<AppointmentBoard> appointmentBoards = appointmentBoardGetService.findAllBoard();
        return AllAppointmentBoardResponse.toResponse(appointmentBoards);
    }

    @Transactional
    public void update(Long boardId) {
        AppointmentBoard appointmentBoard = appointmentBoardGetService.findById(boardId);
        appointmentBoard.increaseReportCount();
    }

    @Transactional(readOnly = true)
    public Map<String, Object> processFastApiResponse(Map<String, Object> response, Long userId, String roomId) {
        Users user = userGetService.findByMemberId(userId);

        // front 요청에 의한 String -> Long 형변환
        Long parseLong = Long.parseLong(roomId);

        AppointmentParticipant appointmentParticipant = participantGetService.findByParticipantAndRoom(user,
            parseLong);
        if (!appointmentParticipant.isOwner()) {
            throw new AccessDeniedException();
        }

        Map<String, List<String>> meets = extractMeets(response.get("meets"));
        List<String> participants = extractParticipants(response.get("participants"));

        List<Map<String, String>> timeSlots = transformMeets(meets);

        Map<String, Object> result = new HashMap<>();
        result.put("timeSlots", timeSlots);
        result.put("participants", participants);

        return result;
    }

    @SuppressWarnings("unchecked")
    private Map<String, List<String>> extractMeets(Object meetsObject) {
        if (meetsObject instanceof Map<?, ?>) {
            return (Map<String, List<String>>) meetsObject;
        }
        throw new IllegalArgumentException("Invalid meets data type");
    }

    @SuppressWarnings("unchecked")
    private List<String> extractParticipants(Object participantsObject) {
        if (participantsObject instanceof List<?>) {
            return (List<String>) participantsObject;
        }
        throw new IllegalArgumentException("Invalid participants data type");
    }

    private List<Map<String, String>> transformMeets(Map<String, List<String>> meets) {
        List<Map<String, String>> timeSlots = new ArrayList<>();

        for (Map.Entry<String, List<String>> entry : meets.entrySet()) {
            String dayKor = entry.getKey();

            Weekday weekday = Weekday.fromKorean(dayKor);

            for (String timeRange : entry.getValue()) {
                String[] times = timeRange.split("-");
                if (times.length == 2) {
                    String startTime = TimeFormater.convertToTimeFormat(times[0]);
                    String endTime = TimeFormater.convertToTimeFormat(times[1]);

                    Map<String, String> timeSlot = new LinkedHashMap<>();
                    timeSlot.put("weekday", weekday.name());
                    timeSlot.put("startTime", startTime);
                    timeSlot.put("endTime", endTime);

                    timeSlots.add(timeSlot);
                }
            }
        }

        return timeSlots;
    }

    @Transactional
    public void update(AppointmentSelectRequest request, Long userId) {
        Users user = userGetService.findByMemberId(userId);
        AppointmentParticipant appointmentParticipant = participantGetService.findByParticipantAndRoom(user,request.roomId());
        //TODO: 권한 조회
        AppointmentRoom room = appointmentParticipant.getAppointmentRoom();
        room.decideTime(request);
    }
}
