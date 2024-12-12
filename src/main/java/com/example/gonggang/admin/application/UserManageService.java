package com.example.gonggang.admin.application;

import com.example.gonggang.admin.application.dto.response.ReadAllMemberResponse;
import com.example.gonggang.domain.member.application.MemberService;
import com.example.gonggang.domain.member.domain.Member;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserManageService {
    private final MemberService memberService;

    public List<ReadAllMemberResponse> readAll() {
        List<Member> members = memberService.findAll();
        return members.stream()
                .map(ReadAllMemberResponse::toResponse)
                .toList();
    }
}
