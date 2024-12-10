package com.example.gonggang.domain.appointment.application;

import com.example.gonggang.domain.appointment.domain.AppointmentParticipant;
import com.example.gonggang.domain.appointment.domain.AppointmentRoom;
import com.example.gonggang.domain.appointment.dto.request.AppointmentCreateRequest;
import com.example.gonggang.domain.appointment.dto.response.AppointmentCreateResponse;
import com.example.gonggang.domain.member.application.MemberService;
import com.example.gonggang.domain.member.domain.Member;
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
}
