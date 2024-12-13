package com.example.gonggang.domain.appointment.domain;

import com.example.gonggang.domain.common.BaseTimeEntity;
import com.example.gonggang.domain.common.Category;
import com.example.gonggang.domain.common.Weekday;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AppointmentBoard extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Weekday weekday;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column
    private String chattingUrl;

    @Column
    private long reportCount;

    @Builder
    private AppointmentBoard(
            final LocalTime startTime,
            final LocalTime endTime,
            final Weekday weekday,
            final Category category,
            final String chattingUrl,
            final int reportCount
    ) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.weekday = weekday;
        this.category = category;
        this.chattingUrl = chattingUrl;
        this.reportCount = reportCount;
    }

    public static AppointmentBoard create(
            final LocalTime startTime,
            final LocalTime endTime,
            final Weekday weekday,
            final Category category,
            final String chattingUrl,
            final int reportCount
    ) {
        return AppointmentBoard.builder()
                .startTime(startTime)
                .endTime(endTime)
                .weekday(weekday)
                .category(category)
                .chattingUrl(chattingUrl)
                .reportCount(reportCount)
                .build();
    }
}
