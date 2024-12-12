package com.example.gonggang.admin.application.dto.response;

import com.example.gonggang.domain.member.domain.Member;

public record ReadAllMemberResponse(
        String name,
        String email
) {
    public static ReadAllMemberResponse toResponse(Member member) {
        return new ReadAllMemberResponse(
                member.getNickname(),
                member.getEmail()
        );
    }
}
