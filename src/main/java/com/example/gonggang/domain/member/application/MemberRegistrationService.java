package com.example.gonggang.domain.member.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gonggang.domain.member.domain.Member;
import com.example.gonggang.domain.member.repository.MemberRepository;
import com.example.gonggang.domain.users.domain.Role;
import com.example.gonggang.domain.users.domain.Users;
import com.example.gonggang.domain.users.repository.UserRepository;
import com.example.gonggang.global.auth.client.dto.MemberInfoResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberRegistrationService {

	private final UserRepository userRepository;
	private final MemberRepository memberRepository;

	@Transactional
	public Long registerMemberWithUserInfo(final MemberInfoResponse memberInfoResponse) {
		Users users = Users.createWithRole(Role.MEMBER);

		log.info("Granting MEMBER role to new user with role: {}", users.getRole());

		users = userRepository.save(users);
		userRepository.flush();

		log.info("Registering new user with role: {}", users.getRole());

		Member member = Member.create(
			memberInfoResponse.nickname(),
			memberInfoResponse.email(),
			users,
			memberInfoResponse.socialId(),
			memberInfoResponse.socialType()
		);

		memberRepository.save(member);

		log.info("Member registered with memberId: {}, role: {}", member.getId(), users.getRole());

		return member.getId();
	}
}
