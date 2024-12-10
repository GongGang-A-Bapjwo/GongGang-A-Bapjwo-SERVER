package com.example.gonggang.domain.users.service;

import com.example.gonggang.domain.member.domain.Member;
import com.example.gonggang.domain.member.exception.MemberNotFoundExcepption;
import com.example.gonggang.domain.member.repository.MemberRepository;
import com.example.gonggang.domain.users.domain.Users;
import com.example.gonggang.domain.users.exception.UserNotFoundException;
import com.example.gonggang.domain.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserGetService {
    private final MemberRepository memberRepository;
    private final UserRepository userRepository;

    public Users findByMemberId(long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundExcepption::new);
        Users user = userRepository.findById(member.getUser().getId()).orElseThrow(UserNotFoundException::new);
        return user;
    }
}
