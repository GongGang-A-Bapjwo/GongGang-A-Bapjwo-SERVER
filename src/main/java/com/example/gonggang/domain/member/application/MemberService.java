package com.example.gonggang.domain.member.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gonggang.domain.member.domain.Member;
import com.example.gonggang.domain.member.domain.SocialType;
import com.example.gonggang.domain.member.exception.MemberNotFoundExcepption;
import com.example.gonggang.domain.member.repository.MemberRepository;
import com.example.gonggang.domain.users.domain.Users;
import com.example.gonggang.domain.users.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {
	private final UserRepository userRepository;
	private final MemberRepository memberRepository;

	@Transactional(readOnly = true)
	public Member findMemberByMemberId(Long memberId) {
		return memberRepository.findById(memberId).orElseThrow(MemberNotFoundExcepption::new);
	}

	@Transactional(readOnly = true)
	public boolean checkMemberExistsBySocialIdAndSocialType(final Long socialId, final SocialType socialType) {
		return memberRepository.findBySocialTypeAndSocialId(socialId, socialType).isPresent();
	}

	@Transactional(readOnly = true)
	public Member findMemberBySocialIdAndSocialType(final Long socialId, final SocialType socialType) {
		return memberRepository.findBySocialTypeAndSocialId(socialId, socialType)
			.orElseThrow(MemberNotFoundExcepption::new);
	}

	@Transactional
	public void deleteUser(final Long id) {
		Users users = userRepository.findById(id)
			.orElseThrow(MemberNotFoundExcepption::new);

		userRepository.delete(users);
	}
}
