package com.example.gonggang.domain.appointment.application;

import com.example.gonggang.domain.appointment.domain.AppointmentParticipant;
import com.example.gonggang.domain.appointment.domain.AppointmentRoom;
import com.example.gonggang.domain.appointment.dto.request.AppointmentCreateRequest;
import com.example.gonggang.domain.appointment.dto.request.AppointmentEnterRequest;
import com.example.gonggang.domain.appointment.dto.response.AppointmentCreateResponse;
import com.example.gonggang.domain.appointment.exception.AlreadyEnteredException;
import com.example.gonggang.domain.appointment.exception.AppointmentRoomMaxAppointmentException;
import com.example.gonggang.domain.users.domain.Users;
import com.example.gonggang.domain.users.service.UserGetService;
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

        appointmentParticipantSaveService.save(appointmentParticipant);
        appointmentRoomSaveService.save(appointmentRoom);

        return AppointmentCreateResponse.toResponse(code);
    }

    @Transactional
    public void enter(Long userId, AppointmentEnterRequest appointmentEnterRequest) {
        Users user = userGetService.findByMemberId(userId);
        AppointmentRoom appointmentRoom = appointmentRoomGetService.findByEnteranceCode(
                appointmentEnterRequest.entranceCode());

        checkAvailable(appointmentRoom,user);

        AppointmentParticipant appointmentParticipant = AppointmentParticipant.create(user, appointmentRoom, false,
                false);

        appointmentParticipantSaveService.save(appointmentParticipant);
    }

    private void checkAvailable(AppointmentRoom appointmentRoom, Users user) {
        if (!appointmentRoom.isAvailable()) {
            throw new AppointmentRoomMaxAppointmentException();
        }
        if (participantGetService.checkAlreadyEntered(user)) {
            throw new AlreadyEnteredException();
        }
    }
}
