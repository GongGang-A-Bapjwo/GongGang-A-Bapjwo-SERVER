package com.example.gonggang.domain.appointment.api;

import static com.example.gonggang.global.config.success.SuccessCode.ENTER_SUCCESS;

import com.example.gonggang.domain.appointment.application.AppointmentManageService;
import com.example.gonggang.domain.appointment.dto.request.AppointmentCreateRequest;
import com.example.gonggang.domain.appointment.dto.request.AppointmentEnterRequest;
import com.example.gonggang.domain.appointment.dto.response.AppointmentCreateResponse;
import com.example.gonggang.domain.appointment.dto.response.AppointmentsGetResponse;
import com.example.gonggang.global.auth.annotation.CurrentMember;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/appointment")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentManageService appointmentManageService;

    @PostMapping()
    public ResponseEntity<AppointmentCreateResponse> create(@CurrentMember Long userId,
                                                            @RequestBody AppointmentCreateRequest appointmentCreateRequest) {
        AppointmentCreateResponse result = appointmentManageService.create(userId, appointmentCreateRequest);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/enter")
    public ResponseEntity<String> create(@CurrentMember Long userId,
                                       @RequestBody AppointmentEnterRequest appointmentEnterRequest) {

        appointmentManageService.enter(userId, appointmentEnterRequest);
        return ResponseEntity.ok(ENTER_SUCCESS.getMessage());
    }

    @GetMapping
    public ResponseEntity<List<AppointmentsGetResponse>> readAll(@CurrentMember Long userId) {
        List<AppointmentsGetResponse> result = appointmentManageService.readAll(userId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<String> delete(@CurrentMember Long userId, @PathVariable Long roomId) {
        appointmentManageService.delete(userId,roomId);
        return ResponseEntity.ok("성공");
    }
}
